package com.atoz.capp.server.listener;

/**
 * 配置文件Bean
 * @author duoli.kuai
 */
public class ProfilesBean {
    /** 饿汉单例模式
     * 在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快
     */

    /** 静态私有成员，已初始化 */
    private static ProfilesBean profilesBean = new ProfilesBean();
 
    private String profiles;
 
    private ProfilesBean() {
        //私有构造函数
    }

    /** 静态，不用同步（类加载时已初始化，不会有多线程的问题）*/
    public static ProfilesBean getInstance() {
        return profilesBean;
    }
 
    public String getProfiles() {
        return profiles;
    }
 
    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

}
