package com.ybj.cbt.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybj.cbt.common.Constants;
import com.ybj.cbt.model.IpBean;
import com.ybj.cbt.service.IpBeanService;
import com.ybj.cbt.utils.Crawler.IPCrawler.IPCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ybj
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/IpBean")
public class IpBeanController {

    @Autowired
    IpBeanService ipBeanService;

    @GetMapping("/save")
    public void saveIpBeanToDB(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<IpBean> IpBeanList=null;
        IpBeanList = IPCrawler.getIpList(Constants.HTTPS_URL, 1);
        ipBeanService.saveBatch(IpBeanList);
    }

    @GetMapping("/getAllIp")
    public PageInfo<IpBean> getAllIp(int pageNum, int pageSize) throws IOException {
        //分页
        PageHelper.startPage(pageNum ,pageSize);
        List<IpBean> IpBeanList=null;
        List<IpBean> ipBeanList = ipBeanService.list();
        PageInfo<IpBean> pageInfo = new PageInfo(ipBeanList);
        return pageInfo;
    }

}

