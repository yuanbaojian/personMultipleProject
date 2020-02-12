package com.ybj.mpm.system.authentication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan
@SpringBootApplication
@ComponentScan(basePackages = {"com.ybj.cbt.*", "com.ybj.mpm.*"})
//所有的dao层 都要放到启动类上注解， 包括其他模块的
@MapperScan({"com.ybj.mpm.system.authentication.dao","com.ybj.cbt.dao"})
//开启缓存
@EnableCaching
public class MpmsystemSystemAuthenticationApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(MpmsystemSystemAuthenticationApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MpmsystemSystemAuthenticationApplication.class);
    }
}
