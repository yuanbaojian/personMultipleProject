package com.ybj.project.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix="project.shiro")
public class RainbowProperties {

    private String anonURL;

    //可读性太差
    private Long jwtTimeOut=86400L;

}
