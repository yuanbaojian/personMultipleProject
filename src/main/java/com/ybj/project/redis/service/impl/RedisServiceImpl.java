package com.ybj.project.redis.service.impl;

import com.ybj.project.Exception.RedisConnectException;
import com.ybj.project.redis.function.JedisExecuter;
import com.ybj.project.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    JedisPool jedisPool;

    private <T> T excuteByJedis(JedisExecuter<Jedis,T> j) throws RedisConnectException {
        try(Jedis jedis=jedisPool.getResource()) {
            return j.excute(jedis);
        } catch (Exception e){
            log.error("redis异常", e);
            //抛出自定义异常  还得new
            throw new RedisConnectException("连接异常");
        }
    }


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



    //这个是什么设计模式？？？？？？
    @Override
    public String get(String key) throws RedisConnectException {
        return this.excuteByJedis(j -> j.get(key.toLowerCase()));
    }

    //存入的key 都是小写
    @Override
    public String set(String key, String value) throws RedisConnectException {
        return excuteByJedis(j -> j.set(key.toLowerCase(), value));
    }

    @Override
    public String set(String key, String value, Long millisecond) throws RedisConnectException {
        String result=set(key,value);
        pexpire(key,millisecond);
        return result;
    }

    @Override
    public Long del(String... key) throws RedisConnectException {
        return this.excuteByJedis(j -> j.del(key));
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
    public Long pexpire(String key, Long milliseconds) throws RedisConnectException {
        return excuteByJedis(j -> j.pexpire(key, milliseconds));
    }
}
