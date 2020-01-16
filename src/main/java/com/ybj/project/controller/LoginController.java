package com.ybj.project.controller;

import com.ybj.project.authentication.Dto.JsonResult;
import com.ybj.project.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public JsonResult login(User user){
        JsonResult jsonResult=JsonResult.ok();
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.fail("用户名和密码不能为空!");
        } else{
            return jsonResult;
        }
    }



}
