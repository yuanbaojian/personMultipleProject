package com.ybj.cbt.utils.Crawler.IPCrawler;

import com.ybj.cbt.model.IpBean;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.*;

public class IPUtils {

    public static boolean isValid(IpBean ipBean) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipBean.getIpAddress(), ipBean.getIpPort()));
        try {
            URLConnection httpCon = new URL("https://www.baidu.com/").openConnection(proxy);
            httpCon.setConnectTimeout(5000);
            httpCon.setReadTimeout(5000);
            int code = ((HttpURLConnection) httpCon).getResponseCode();
            System.out.println(code);
            return code == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public void testValid() {
        IpBean ipBean = new IpBean();
        ipBean.setIpAddress("121.226.188.111");
        ipBean.setIpPort(9999);
        boolean valid = isValid(ipBean);
        System.out.println("valid = " + valid);
    }

    @Test
    public void testGetServerIp() throws MalformedURLException, UnknownHostException {
        URL url = new URL("https://www.leangoo.com/kanban/board/go/3091585");
        String host = url.getHost();
        InetAddress address = InetAddress.getByName(host);
        String ip = address.getHostAddress();
        System.out.println("ip = " + ip);
    }
}
