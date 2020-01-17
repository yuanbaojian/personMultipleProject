package com.ybj.project.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ybj.project.Dto.JsonResult;
import com.ybj.project.Exception.RedisConnectException;
import com.ybj.project.authentication.jwt.JWTToken;
import com.ybj.project.authentication.jwt.JWTUtil;
import com.ybj.project.constant.RainbowConstant;
import com.ybj.project.constant.RainbowProperties;
import com.ybj.project.model.User;
import com.ybj.project.redis.service.RedisService;
import com.ybj.project.service.LoginLogService;
import com.ybj.project.service.UserService;
import com.ybj.project.utils.IPUtil;
import com.ybj.project.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    LoginLogService loginLogService;

    //这个也能放到IOC中， 不过取比较麻烦，还得导入
    @Autowired
    RainbowProperties rainbowProperties;

    @PostMapping("/login")
    public JsonResult login(User user, HttpServletRequest request){
        String[] anonUrl = StringUtils.splitByWholeSeparatorPreserveAllTokens(rainbowProperties.getAnonURL(), StringPool.COMMA);

        // TODO 这个应该在前台处理， 现在是为了postman测试
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.fail("用户名和密码不能为空!" + rainbowProperties.getEn());
        }
        JsonResult jsonResult=JsonResult.ok();
        String encryptedPassword= MD5Utils.encrypt(user.getUsername(), user.getPassword());
        User loginUser = userService.selectUserByUsername(user.getUsername());
        if (loginUser == null) {
            return JsonResult.fail("用户名或者密码不正确！");
        }
        if (!encryptedPassword.equals(loginUser.getPassword())) {
            return JsonResult.fail("用户名或者密码不正确!");
        }
        if (RainbowConstant.ACCOUNT_LOCK.equals(loginUser.getStatus())) {
            return JsonResult.fail("账号已被锁定,请联系管理员！");
        }

        String loginUserToken= JWTUtil.getToken(user.getUsername(), user.getPassword());
        // 生成过期时间点
        LocalDateTime expiredTime=LocalDateTime.now().plusSeconds(rainbowProperties.getJwtTimeOut());
        String expiredTimeString = expiredTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        //  获得JWTToken对象
        JWTToken jwtToken = new JWTToken(loginUserToken, expiredTimeString);
        Map<String, Object> userInfo = userService.getUserWithToken(jwtToken, loginUser);
        try {
            // 将签名放入redis缓存
            saveTokenToRedis(loginUserToken, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JsonResult.ok("登陆成功").addData(userInfo);
    }

    /**
     *  保存token到redis
     * @param token 密匙
     * @param request
     * @return void
     * @author melo、lh
     * @createTime 2019-10-22 10:59:18
     */
    private void saveTokenToRedis(String token, HttpServletRequest request) throws Exception {
        String ip = IPUtil.getIpAddr(request);
        /*
        *  key: 自定义前缀 + 加密 token + ip
        *  value: token
        * */
        redisService.set(RainbowConstant.RAINBOW_TOKEN +token+ StringPool.DOT + ip,token,rainbowProperties.getJwtTimeOut()*1000);
    }

    @PostMapping("/logout")
    public JsonResult logout(HttpServletRequest request){
        String ip=IPUtil.getIpAddr(request);
        //这个应该是后台的token
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String tokenKey = StringPool.PLUS + RainbowConstant.RAINBOW_TOKEN + token + StringPool.DOT + ip;
        try {
            redisService.del(tokenKey);
        } catch (RedisConnectException e) {
            e.printStackTrace();
            log.error("注销时，token删除失败");
        }
        return JsonResult.ok();
    }



}
