package com.atoz.capp.server.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.atoz.capp.exception.ServiceException;

/**
 * 下载文件Servlet
 * @author caicai.gao
 */
public class DownloadAttachmentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		String id = request.getParameter("id");
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
//		AnnexService annexService = (AnnexService)wac.getBean("annexManageService"); 
//		if (!StringUtil.isNullOrEmpty(id)){
//			String annexId = "";
//			if (StringUtil.isNullOrEmpty(request.getParameter("isEncrypt"))){
//				try {
//					annexId = DesPassword.decrypt(id,"12345678");
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			} else {
//				annexId = id;
//			}
//			//根据id查询到相应的附件
//			Annex annex = annexService.getAnnex(Long.parseLong(annexId));
//			if (annex !=null ) {
//				String filePath = PropertiesUtil.getMsgsMap().get(Constants.STOREHOUSE_PATH)
//						+ File.separator + "Annex" + File.separator
//						+ annex.getId()
//						+ File.separator
//						+ annex.getId()
//						+ "." + annex.getFileType();
//				try {
//					downLoad(filePath,annex.getFileName(), response, false);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}  
	
	/**
	 * 下载文件
	 * @param filePath
	 * @param response
	 * @param isOnLine
	 * @throws Exception
	 */
	public void downLoad(String filePath,String fileName, HttpServletResponse response, boolean isOnLine) throws Exception {
        File f = new File(filePath);
        System.out.println(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            System.out.println("File not found!");
            throw new ServiceException("文件不存在!");
        }
        System.out.println(f.getName());
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;

        response.reset(); // 非常重要
        if (isOnLine) { // 在线打开方式
            URL u = new URL("file:///" + filePath);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setHeader("Content-Length", String.valueOf(f.length()));
        } else { // 纯下载方式
            response.setContentType("application/octet-stream;charset=utf-8");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            // 文件名应该编码成UTF-8
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.setHeader("Content-Length", String.valueOf(f.length()));
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.flush();
        br.close();
        out.close();
    }
}
