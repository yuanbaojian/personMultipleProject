package com.ybj.project.redis.service.impl;

import com.ybj.project.redis.service.RedisService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;


@Service
public class RedisServiceImpl implements RedisService {
    @Override
    public Map<String, Object> getKeysSize() {
        return null;
    }

    @Override
    public Map<String, Object> getMemoryInfo() {
        return null;
    }

    @Override
    public Set<String> getKeys(String pattern) {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public String set(String key, String value) {
        return null;
    }

    @Override
    public String set(String key, String value, Long millisecond) {
        return null;
    }

    @Override
    public String del(String... key) {
        return null;
    }

    @Override
    public Boolean exists(String key) {
        return null;
    }

    @Override
    public Long pttl(String key) {
        return null;
    }

    @Override
    public Long pexpire(String key, Long milliseconds) {
        return null;
    }
}
