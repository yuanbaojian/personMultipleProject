package com.atoz.capp.server.util;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 环境参数设置类
 * @author duoli.kuai
 */
@Component
public class ProfileUtil implements ApplicationContextAware {
	private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取当前环境参数  exp: development,test,production
     * @return String 配置文件名称
     */
    public static String getActiveProfile() {
        String[] profiles = context.getEnvironment().getActiveProfiles();
        if ( ! ArrayUtils.isEmpty(profiles)){
        	System.out.println("active profile" + profiles[0]);
            return profiles[0];
        }
        return "";
    }
}
