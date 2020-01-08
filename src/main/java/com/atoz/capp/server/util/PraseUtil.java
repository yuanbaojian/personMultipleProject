package com.atoz.capp.server.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PraseUtil {
	public static String praseHtmFile2Str(String filePath) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
			String s = reader.readLine();

			while (s != null) {
				sb.append(s);
				sb.append("\r\n");
				s = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	
	public static String avoidNull(Object obj) {
		if (null == obj) {
			return "";
		}
		
		return obj.toString().trim();
	}
}
