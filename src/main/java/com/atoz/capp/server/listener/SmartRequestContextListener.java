package com.atoz.capp.server.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextListener;

public class SmartRequestContextListener extends RequestContextListener { 
	
	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		
		HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		request.setAttribute("BASEPATH", basePath);
		request.setAttribute("PATHINFO", request.getServletPath());
	}

}
