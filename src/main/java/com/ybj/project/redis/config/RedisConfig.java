package com.ybj.project.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Configuration;
import org.testng.annotations.Test;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {


    @Value("${spring.redis.host}")
    private String host;

    @Test
    public void test(){
//        估计是项目起来后才生效
        System.out.println("host = " + host);
    }

}
