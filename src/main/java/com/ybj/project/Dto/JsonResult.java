package com.ybj.project.Dto;


import java.util.HashMap;
import java.util.Map;

/**
 * 状态相应类
 * 用户返回前台信息
 */
public class JsonResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "success";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";
    public static final boolean OPERATE_SUCCEED = true;
    public static final boolean OPERATE_FAILURE = false;
    public static final int OK = 200;
    public static final int ERROR = 500;

    public static JsonResult ok(){
        JsonResult jsonResult=new JsonResult();
        jsonResult.put(SUCCESS,true);
        return jsonResult;
    }

    public static JsonResult ok(String message){
        JsonResult jsonResult=new JsonResult();
        jsonResult.put(SUCCESS,true);
        jsonResult.put(MESSAGE, message);
        return jsonResult;
    }

    public static JsonResult fail(String s) {
        JsonResult jsonResult=new JsonResult();
        jsonResult.put(SUCCESS,false);
        jsonResult.put(MESSAGE,s);
        return jsonResult;
    }

    //当前的jsonresult 增加一下额外数据
    public JsonResult addData(Map<String, Object> data) {
        this.put(DATA, data);
        return this;
    }
}
