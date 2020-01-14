package com.ybj.project.redis.service;

import java.util.Map;
import java.util.Set;

public interface RedisService {

    Map<String ,Object> getKeysSize();

    Map<String ,Object> getMemoryInfo();

    Set<String> getKeys(String pattern);

    String get(String key);

    String set(String key, String value);

    String set(String key, String value, Long millisecond);

    String del(String... key);

    Boolean exists(String key);

    Long pttl(String key);

    Long pexpire(String  key, Long milliseconds);



}
