package com.atoz.capp.common;

/**
 * 常量设置
 * @author  caicai.gao
 *
 */
public class Constants {
	
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
	
	/** 电子仓库根目录 */
	public static String STOREHOUSE_PATH = "storehouse_path";

	/** MBD模板名称 */
	public static String MBD_TEMPALTE = "MBDTemplate";
	
	/** 发布状态-web端 */
	/** 未发布 */
	public static final short RELEASE_STATUS_0 = 0;
	/** 待审核 */
	public static final short RELEASE_STATUS_1 = 1;
	/** 已发布 */
	public static final short RELEASE_STATUS_2 = 2;
	/** 已驳回 */
	public static final short RELEASE_STATUS_3 = 3;
	
	
	/** 以下为message */
	public static String BOOKTAG_MSG001 = "您输入的分类名称在数据库中已存在，请重新输入";
	public static String BOOKTAG_MSG002 = "分类存在关联，删除失败！";
	public static String LOGIN_MSG001 = "用户名或密码不正确,请重新输入";
	public static String MBD_MSG001 = "MBD模板有关联,不可删除!";
	public static String MBD_MSG002 = "您输入的模板名称和模板编码在数据库中已经存在，请重新输入";
	public static String MBD_MSG003 = "您输入的模板名称在数据库中已经存在，请重新输入";
	public static String MBD_MSG004 = "您输入的模板编码在数据库中已经存在，请重新输入";
	public static String MBD_MSG005 = "该分类下已存在MBD模板，不可新建子分类！";
	public static String ORG_STRUC_MSG001 = "用户有关联,不可删除!";
	public static String ORG_STRUC_MSG002 = "该记录有关联，不可删除!";
	public static String ORG_STRUC_MSG004 = "系统初始化管理员用户不可删除!";
	public static String ROLE_MSG001 = "您输入的角色名称在数据库中已经存在，请重新输入";
	public static String STANDARD_MANAGE_MSG001 = "该分类已关联标准件,不可删除!";
	public static String TRAINNING_MSG001 = "您输入的属性名称已经存在，请重新输入";
	public static String USER_MSG001 = "您输入的登录名在数据库中已经存在，请重新输入";
	
}