package com.atoz.capp.common.constant;

/**
 * 业务相关常量
 * @author caicai.gao
 *
 */
public class BusniessConstants {
    
    /** 一年中月份数 */
    public static final int MOUTH_OF_YEAR = 12;

    /** MBD模板名称 */
    public static String MBD_TEMPALTE = "MBDTemplate";

    /** 分类树类型 */
    public static String TREE_TYPE = "treeType";

    
    /**
     * 审核状态枚举类
     * @author caicai.gao
     *
     */
    public static enum AuditStatusEnum{
        
        // 审核状态 （名称，值）
        STATUS_0("未审核", 0), STATUS_1("审核中", 1), 
        STATUS_2("已审核", 2), STATUS_3("已完成", 3);
        
        private AuditStatusEnum(String name, int value) {
            this.name = name;
            this.value = value;
        }
        
        private final int value;
        
        private final String name;
        
        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
        
    }
    
    /**
     * 认证状态枚举类
     * @author caicai.gao
     *
     */
    public static enum IdentifiedStatusEnum{
        
        // 认证状态 （名称，值）
        STATUS_0("未认证", "0"), STATUS_1("待认证", "1"), STATUS_2("已认证", "2");
        
        private IdentifiedStatusEnum(String name, String value) {
            this.name = name;
            this.value = value;
        }
        
        private final String value;
        
        private final String name;
        
        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
        
    }
    

}
