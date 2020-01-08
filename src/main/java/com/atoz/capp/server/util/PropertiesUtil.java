package com.atoz.capp.server.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.atoz.capp.common.Constants;

public class PropertiesUtil {

	private static Map<String, String> msgsMap;

	private static String blank = "%20";

	static {
		try {
			msgsMap = new HashMap<String, String>();
			URL url = new PropertiesUtil().getClass().getClassLoader().getResource(ProfileUtil.getActiveProfile() + "/resource.properties");
			String path = url.getPath();
			if (path.contains(blank)) {
				path = path.replace(blank, " ");
			}
			File f = new File(path);
			InputStream propIS= new FileInputStream(f);
			Properties pro = new Properties();
			pro.load(propIS);
				
			Set<Map.Entry<Object, Object>> set =pro.entrySet();
			for (Map.Entry<Object, Object> en : set) {
				msgsMap.put(en.getKey().toString(), new String(en.getValue().toString().getBytes("ISO-8859-1"),"UTF-8"));
			}
		} catch (Exception ea) {
			ea.printStackTrace();
		}
		
	}

	public static Map<String, String> getMsgsMap() {
		return msgsMap;
	}

	public static void setMsgsMap(Map<String, String> msgsMap) {
		PropertiesUtil.msgsMap = msgsMap;
	}

}
