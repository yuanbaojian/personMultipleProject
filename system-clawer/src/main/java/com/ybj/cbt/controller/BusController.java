package com.ybj.cbt.controller;

import com.ybj.cbt.common.Constants;
import com.ybj.cbt.model.IpBean;
import com.ybj.cbt.utils.Crawler.IPCrawler.IPCrawler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Author BusController
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
@RestController
public class BusController {

    @RequestMapping(value = "/getAllIp", method = RequestMethod.POST)
    public List<IpBean> getAllIp() throws IOException {
        List<IpBean> IpBeanList=null;
        IpBeanList = IPCrawler.getIpList(Constants.HTTPS_URL, 1);
        return IpBeanList;
    }

}
