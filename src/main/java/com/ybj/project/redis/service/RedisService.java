package com.ybj.project.redis.service;

import com.ybj.project.Exception.RedisConnectException;

import java.util.Map;
import java.util.Set;


//各种redis的服务
public interface RedisService {

    Map<String ,Object> getKeysSize();

    Map<String ,Object> getMemoryInfo();

    Set<String> getKeys(String pattern);

    String get(String key) throws RedisConnectException;

    String set(String key, String value) throws RedisConnectException;

    String set(String key, String value, Long millisecond) throws RedisConnectException;

    Long del(String... key) throws RedisConnectException;

    Boolean exists(String key);

    Long pttl(String key);

    Long pexpire(String  key, Long milliseconds) throws RedisConnectException;



}
