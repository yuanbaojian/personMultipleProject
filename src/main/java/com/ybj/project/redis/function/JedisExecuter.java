package com.ybj.project.redis.function;


//函数式接口
@FunctionalInterface
public interface JedisExecuter <T,R>{

    R excute(T t);

}
