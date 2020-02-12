package com.ybj.mpm.system.authentication.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ybj.mpm.system.authentication.dao.UserMapper;
import com.ybj.mpm.system.authentication.model.User;
import com.ybj.mpm.system.authentication.model.UserRole;
import com.ybj.mpm.system.authentication.service.UserRoleServiceI;
import com.ybj.mpm.system.authentication.service.UserServiceI;
import com.ybj.mpm.system.authentication.utils.Md5EncryptionUtil;
import com.ybj.mpm.utils.common.JsonResult;
import com.ybj.mpm.utils.constant.BusniessConstants;
import com.ybj.mpm.utils.constant.ConfigConstants;
import com.ybj.mpm.utils.constant.MessageConstants;
import com.ybj.mpm.utils.exception.CustomGenericException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制类
 * @author caicai.gao
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceI userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleServiceI userRoleService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


//    使用缓存后， 方法不再输出日志
//    @Cacheable(cacheNames = "getAllUsers")
    @PostMapping("/getAllUsers")
    public List<User> getAllUsers(){
        log.info("查询所有用户  执行了");
        List<User> list = userService.list();
        return list;
    }

    /**
     * 用户一览列表
     * @param serverParams 查询参数
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/users", produces="application/json")
    public JsonResult getUsers(@RequestBody Map<String, Object> serverParams) {
        Map<String, Object> searchParams = (Map<String, Object>) serverParams.get("serverParams");
        IPage<User> userPage = userService.getAll(searchParams);
        return JsonResult.ok().addData(userPage.getRecords()).add(BusniessConstants.TOTAL_COUNT,userPage.getTotal());
    }

    @PostMapping(value = "/user")
    public JsonResult addUser(@RequestBody User user) {
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(LocalDateTime.now());
        user.setCreatedBy("1");
        user.setUpdatedBy("1");
        userMapper.insert(user);
        System.out.println();
        return JsonResult.ok();
    }

    /**
     * 获取审核人员列表
     * @param req HttpServletRequest
     * @return JsonResult 状态响应类
     */
    @GetMapping(value = "/searchAuditor")
    public JsonResult searchAuditor(HttpServletRequest req) {
        String type = req.getParameter("type");
        List<User> l = userService.searchAuditor(type);
        return JsonResult.ok().addData(l);
    }

    /**
     * 获取转交人员列表
     * @return JsonResult 状态响应类
     */
    @GetMapping(value = "/searchUser")
    public JsonResult searchUser() {
        List<User> l = userService.searchUser();
        return JsonResult.ok().addData(l);
    }

    /**
     * 删除用户（逻辑删除，将用户记录中是否删除状态更改为1-即已删除）
     * @param req HttpServletRequest
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/delete")
    public JsonResult delete(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        try {
            User deleteUser = userService.getById(userId);
            if (deleteUser.getLoginName().equals(ConfigConstants.ADMIN_USER)){
                return JsonResult.ok(ConfigConstants.ERROR_CODE_BUSINESS, MessageConstants.ORG_STRUC_MSG004);
            } else {
                // 先删除与角色的关联信息
                userRoleService.removeById(userId);
                // 删除用户（逻辑删除）
                userService.removeById(userId);
            }
            return JsonResult.ok("删除用户成功！");
        } catch (Exception e) {
            log.error(e.getMessage());
            return JsonResult.fail("删除用户失败！");
        }
    }

    /**
     * 保存用户
     * @param user 用户信息
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/save")
    public JsonResult save(@RequestBody User user) {
//        User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
        try {
            user.setCreatedBy("1");
            user.setUpdatedBy("1");
            user.setDeleted(0);
            if (user.getUserId() != null) {
                // 修改用户
                user.setUpdateTime(LocalDateTime.now());
                user = userService.saveUser(1, user);
                return JsonResult.ok("用户修改成功！").addData(user);
            } else {
                // 新建用户
                user = userService.saveUser(0, user);
                return JsonResult.ok("用户新建成功！").addData(user);
            }
        } catch (CustomGenericException ex1) {
            log.error(ex1.getMessage());
            if (user.getUserId() != null) {
                return JsonResult.fail("修改用户失败！");
            } else {
                return JsonResult.fail("新建用户失败！");
            }
        }
    }

    /**
     * 禁用用户
     * @param req HttpServletRequest
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/forbidden")
    public JsonResult forbidden(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        String[] userList = userId.split(",");
        for (String id : userList) {
            if (id == null || "".equals(id)) {
                continue;
            }
            User forbiddenUserById = userService.getById(id);

            forbiddenUserById.setStatus(0);

            userService.updateById(forbiddenUserById);
        }
        return JsonResult.ok("禁用用户成功！");

    }

    /**
     * 启用用户
     * @param req HttpServletRequest
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/enable")
    public JsonResult enable(HttpServletRequest req)  {
        String userId = req.getParameter("userId");
        String[] userList = userId.split(",");
        for (String id : userList) {
            if (id == null || "".equals(id)) {
                continue;
            }
            User enableUserById = userService.getById(id);
            enableUserById.setStatus(1);
            userService.updateById(enableUserById);
        }
        return JsonResult.ok("禁用用户成功！");
    }

    /**
     * 修改密码
     * @param req HttpServletRequest
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/updatePassword")
    public JsonResult updatePassword(HttpServletRequest req) {
        String loginName = req.getParameter("loginName");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(Md5EncryptionUtil.convertMd5(oldPassword));
        // 用户验证
        User loginUser = userService.checkUser(user);
        if (loginUser != null) {
            loginUser.setPassword(Md5EncryptionUtil.convertMd5(newPassword));
            userService.updateById(loginUser);
            return JsonResult.ok("密码修改成功！");
        } else {
            return JsonResult.ok(ConfigConstants.ERROR_CODE_BUSINESS, MessageConstants.USER_MSG003);

        }
    }

    /**
     * 重置密码
     * @param req HttpServletRequest
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/resetPassword")
    public JsonResult resetPassword(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        User oldUser = userService.getById(userId);
        oldUser.setPassword(Md5EncryptionUtil.convertMd5(ConfigConstants.PASSWORD));
        oldUser.setUpdateTime(LocalDateTime.now());
        userService.updateById(oldUser);
        return JsonResult.ok("密码重置成功！");
    }

    /**
     * 获取审核人
     * @param req HttpServletRequest
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/auditors")
    public JsonResult getAuditor(HttpServletRequest req) {
        String loginUserId = req.getParameter("loginUserId");
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        // 获取状态为启用，userId不为loginUserId的用户
        lambdaQuery.eq(User::getStatus, 1).ne(User::getUserId, loginUserId);
        List<User> l = userService.list(lambdaQuery);
        return JsonResult.ok().addData(l);
    }

    /**
     * 给用户分配角色
     * @param roles 选中的角色
     * @param userId 用户ID
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/assignRole")
    public JsonResult assignRole(String roles, String userId) {
        // 选择的用户
        List<UserRole> list = JSON.parseArray(roles, UserRole.class);
        userService.assignRole(list, userId);
        return JsonResult.ok("角色分配成功！");
    }

    /**
     * 获取用户已分配角色
     * @param userId 用户ID
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/getUserSelectedRole")
    public JsonResult getUserSelectedRole(String userId) {
        // 选择的用户
        List<Integer> list = userService.getUserSelectedRole(userId);
        return JsonResult.ok().addData(list);
    }


    /**
     * 检查用户是否具有指定权限
     * @param req HttpServletRequest
     * @return JsonResult 状态响应类
     */
    @PostMapping(value = "/hasPermission")
    public JsonResult hasPermission(HttpServletRequest req) {
        String permissionCode = req.getParameter("permissionCode");
        User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
        try {
            Boolean hasPermission = userService.hasPermission(loginUser.getUserId(), permissionCode);
            return JsonResult.ok().addData(hasPermission);

        } catch (CustomGenericException ex1) {
            log.error(ex1.getMessage());
            return JsonResult.fail("查询用户权限失败！");
        }
    }

}