package com.ybj.cbt.utils.Crawler.FIleDownload;

import com.ybj.cbt.utils.NetWorkUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class nineOneTest {

    @Test
    void startDownload() throws IOException {
        String urlSource = NetWorkUtils.getPageContent("http://www.91porn.com/view_video.php?viewkey=8f980f284de43fa16fa1&page=1&viewtype=basic&category=rf","GET");
        Document doc= Jsoup.parse(urlSource);
        //第二步，根据我们需要得到的标签，选择提取相应标签的内容
        Elements elements = doc.select("video[class=vjs-tech]").select("source");
        for (int i = 0; i <elements.size(); i++) {
            String urlString = elements.get(i).attr("src");
            urlString=urlString.substring(0, urlString.indexOf("?"));
            System.out.println("urlString = " + urlString);
        }
    }
}