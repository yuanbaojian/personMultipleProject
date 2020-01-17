package com.ybj.project.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author MD5Utils
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
public class MD5Utils {

    /** 加密算法*/
    private static final String ALGORITH_NAME = "md5";

    /** 加密次数*/
    private static final int HASH_ITERATIONS = 1024;


    public static String encrypt(String username, String password){
        ByteSource salt = ByteSource.Util.bytes(username);
        String encryptedPassword = new SimpleHash(ALGORITH_NAME,password,salt,HASH_ITERATIONS).toHex();
        return encryptedPassword;
    }

}
