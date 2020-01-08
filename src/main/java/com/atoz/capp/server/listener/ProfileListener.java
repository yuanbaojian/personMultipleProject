package com.atoz.capp.server.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 配置文件加载监听
 * @author caicai.gao
 */
public class ProfileListener implements ServletContextListener{
	@Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext sc = sce.getServletContext();
            String profiles = sc.getInitParameter("spring.profiles.active");
            ProfilesBean profilesBean = ProfilesBean.getInstance();
            profilesBean.setProfiles(profiles);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
 
    }
}
