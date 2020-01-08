package com.atoz.capp.common.constant;

/**
 * 系统配置相关常量
 * @author caicai.gao
 *
 */
public class ConfigConstants {
    
    /****************************  系统常量      ************************************/
    
    /** 系统名称 */
    public static String SYSTEMNAME = "CAPP";
    
    /** 管理员名称 */
    public static String ADMIN_USER = "admin";
    
    /** 30分钟内密码最大错误次数 */
    public static final int ALLOW_FAIL_COUNT = 5;
    
    /** 密码过期时间 */
    public static final int LOCK_TIME = 30;
    
    /** 默认密码 */
    public static final String PASSWORD = "123456";
    
    /** 错误编码 */
    public static int ERROR_CODE_NONE = 0;
    public static int ERROR_CODE_SYSTEM = 10000;
    public static int ERROR_CODE_BUSINESS = 10001;
    public static int ERROR_CODE_USER = 10002;

    /** 电子仓库根目录 */
    public static String STOREHOUSE_PATH = "storehouse_path";

    /** HttpResponse 的 contentType*/
    public static String CONTENT_TYPE = "application/json";

    /** HttpResponse 的 characterEncoding*/
    public static String CHARACTER_ENCODING = "utf-8";

    /** 日期格式 */
    public static String DATE_FORMAT = ConfigConstants.DATE_FORMAT;

    /** 错误信息标识 */
    public static String ERROR_MSG = "errmsg";

    /** 添加失败 */
    public static final short FAIL = 0;
    
    /**  添加成功 */
    public static final short SUCCESS = 1;
    
    /** 添加信息已存在 */
    public static final short EXIST = 2;
    
    /** 字符串null */
    public static String NULL_STRING = "null";
    
    /** 字符串true */
    public static String TRUE_STRING = "true";
    
    /** 字符串false */
    public static String FALSE_STRING = "false";
    
    /** 双引号 */
    public static String QUOTATION_MARKS_STRING = "\"";
    
    /** 升序 */
    public static String ASC_STRING = "asc";
    
    /** linux操作系统 */
    public static String OS_LINUX = "linux";
    
    /** 验证码有效时间 5分钟*/
    public static final int VERIFYCODE_EFFECTIVE_TIME = 1000 * 60 * 5;

    /****************************  MRO权限定义      ************************************/
    
    /** 备件管理员-备件管理 */
    public static String PERMISSION_SPAREPARTS_MANAGE = "spareparts_manage";
    
    /** 业务员-设备履历管理 */
    public static String PERMISSION_EQUIPMENT_MANAGE = "equipment_manage";
    
    /** 业务员-服务包管理 */
    public static String PERMISSION_SERVICEPACK_MANAGE = "servicepack_manage";
        
    /** 业务员-客户管理 */
    public static String PERMISSION_CUSTOMER_MANAGE = "customer_manage";
    
    /** 微信端设备运维-最终用户 */
    public static String PERMISSION_WECHAT_ENDUSER = "enduser";
        
    /** 微信端设备维修-维修工程师 */
    public static String PERMISSION_WECHAT_SERVICEMAN = "serviceman";   
    
    /** 微信端业务员 */
    public static String PERMISSION_WECHAT_BUSINESSMAN = "businessman";
        
    /** 管理层-运维看板 */
    public static String PERMISSION_DASHBOARD_VIEW = "dashboard_view";
            
    /** 管理层-报表查看 */
    public static String PERMISSION_REPORT_VIEW = "report_view";
    
    /** 管理层-服务级别管理 */
    public static String PERMISSION_SERVICELEVEL_MANAGE = "servicelevel_manage";
    
    /** 系统管理员-用户管理 */
    public static String PERMISSION_USER_MANAGE = "user_manage";
            
    /** 系统管理员-角色、权限管理 */
    public static String PERMISSION_PRIVILEGE_MANAGE = "privilege_manage";
            
    /** 运维主管-运维排程 */
    public static String PERMISSION_MAINTAIN_SCHEDULE = "maintain_schedule";
                
    /**  运维主管-运维监控 */
    public static String PERMISSION_MAINTAIN_MONITOR = "maintain_monitor";
        
    /** 运维主管-服务项定义 */
    public static String PERMISSION_SERVICEITEM_MANAGE = "service_item_manage";
    
}
