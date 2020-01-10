package com.atoz.capp.common;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * @Author log
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
@Slf4j
public class log {

    @Test
    public void test(){
        log.error("错了");
    }
}
