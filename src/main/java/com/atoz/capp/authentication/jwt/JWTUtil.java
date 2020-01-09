package com.atoz.capp.authentication.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @Author yabo.wang
 * @Date 2020/1/8 10:34
 * @Descripyion:实现对用户名密码的加密处理，校验token是否正确
 */
public class JWTUtil {
    /**令牌过期时间默认两小时*/
    private static final long EXPIRE_TIME = 5*60*1000;
    /***
     * @param token
     * @Description:从token中获取用户名
     * @author wangyabo
     * @date 2020/1/8 11:53
     */
    public static String getUserName(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * @param userName
     * @param password
     * @Description:根据用户名密码以及失效时间生成token
     * @author wangyabo 
     * @date 2020/1/8 10:54
     */
    public static String generateToken(String userName,String password){
        Algorithm algorithm = Algorithm.HMAC256(password);
        Date date = generateExpirationDate();
            String token = JWT.create()
                              .withClaim("userName",userName)
                              .withExpiresAt(date)
                              .sign(algorithm);
            return token;
    }
    /***
     * @param token
     * @param userName
     * @param password
     * @Description:验证token是否正确
     * @author wangyabo
     * @date 2020/1/9 10:50
     */
    public static boolean verify(String token,String userName,String password){
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("userName",userName).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("验证的结果:"+jwt.toString());
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }

    }


    /***
     * 生成过期时间戳
     * 当前时间加上token失效时间
     */
    public static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+EXPIRE_TIME);
    }


}
