package com.atoz.capp.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.atoz.capp.common.Constants;



/**
 * 
 * 字符串工具
 * 
 * @author atoz-soft.com
 * 
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils{
	
	/** 短日期格式类型 */
	public static final String DATE_FORMART_SHORT = "yyyy/MM/dd";

	/**
	 * 将一个字符串转化成非 null 形式,并且去掉前后空格
	 * 
	 * @return 处理后的字符串
	 */
	public static String avoidNull(Object str) {
		if (str instanceof String) {
			return (str == null ? "" : ((String) str).trim());
		} else {
			if (str == null) {
				return "";
			}

			return str.toString().trim();
		}
	}

	/**
	 * 将一个字符串转化成非 null 形式,并且去掉前后空格
	 * 
	 * @return 处理后的字符串
	 */
	public static String avoidNull(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 将字符串转化为int
	 * 
	 * @param str
	 * @return
	 */
	public static int toInteger(String str) {
		if (null == str || str.equals("")) {
			return -1;
		}

		return Integer.parseInt(str.trim());
	}

	/**
	 * java.util.Date转为yyyy/MM/dd格式的String
	 * 
	 * @param date
	 * @return yyyy/MM/dd格式的日期字符串
	 */
	public static String dateToString(Date date) {
		// Date转String
		String str = null;
		DateFormat format = new SimpleDateFormat(DATE_FORMART_SHORT);
		str = format.format(date);
		return str;
	}
	
	/**
	 * java.util.Date转为特定格式的String
	 * 
	 * @param date 日期
	 * @param formatStr 格式字符串
	 * @return 对应格式的日期字符串
	 */
	public static String dateToString(Date date, String formatStr) {
		// Date转String
		String str = null;
		DateFormat format = new SimpleDateFormat(formatStr);
		str = format.format(date);
		return str;
	}
	
	/**
	 * String 转Date,DateFormat"yyyy-MM-dd"
	 * @param dateStr
	 * @return
	 */
	public static Date stringToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * 将字符串转化为Double
	 * 
	 * @param str
	 * @return
	 */
	public static Double toDouble(String str) {
		if (null == str || str.equals("")) {
			return (double) -1;
		}
		return Double.parseDouble(str.trim());
	}

	/**
	 * 判断字符串是空或者null
	 * 
	 * @param str
	 *            字符串
	 * @param ignoreSpace
	 *            是否忽略空格
	 * @return true:是空字符串或者null；false：不是空字符串
	 */
	public static boolean isNullOrEmpty(String str, boolean ignoreSpace) {
		if (ignoreSpace) {
			return str == null || "".equals(str.trim());
		}

		return str == null || "".equals(str);
	}

	/**
	 * 判断字符串是空或者null(不忽略空格)
	 * 
	 * @param str
	 *            字符串
	 * @return true:是空字符串或者null;false：不是空字符串
	 */
	public static boolean isNullOrEmpty(String str) {
		return isNullOrEmpty(str, true);
	}

	/**
	 * 判断Object是空或者null(忽略空格)
	 * 
	 * @param obj
	 *            Object
	 * @return true:是空字符串或者null;false：不是空字符串
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else {
			if (obj instanceof String) {
				return ((String) obj).trim().equals("");
			}
			return obj.toString().trim().equals("");
		}
	}

	/**
	 * 判断字符是否是0-9的数字
	 * 
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (!"".equals(avoidNull(str))) {
			return str.matches("^[0-9]*$");
		} else {
			return false;
		}
	}

	/**
	 * 取得字符的长度，汉字以两位算
	 * 
	 * @param value
	 * @return
	 */
	public static int getStrLength(String value) {
		if (isNullOrEmpty(value)) {
			return 0;
		}

		int totalLength = 0;
		for (int i = 0; i < value.length(); i++) {
			int tempLength = String.valueOf((value.charAt(i))).getBytes().length;
			// 如果编码后大于等于两个字节，按两个字节算
			if (tempLength >= 2) {
				totalLength += 2;
			} else {
				totalLength++;
			}
		}

		return totalLength;
	}

	/**
	 * 字符串数组是否包含字符串
	 * 
	 * @param arrString
	 * @param str
	 * @return
	 */
	public static boolean isHave(String[] arrString, String str) {
		if (arrString == null) {
			return false;
		}

		for (String string : arrString) {
			if (string.equals(str)) {
				return true;
			}
		}

		return false;
	}
	
	
	/**
	 * 根据字符分割字符串
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] splitStr(String str, String regex) {
		if (!isNullOrEmpty(str)) {
			return str.split(regex);
		}
		return null;
	}
	
	/**
	 * 分割替换字符的字符串
	 * @param str
	 * @param regex
	 * 			要替换的字符
	 * @param replacement
	 * 			替换的 和 分割的 字符
	 * @return
	 */
	public static String[] splitReplaceStr(String str,String regex,String replacement) {
		if (!isNullOrEmpty(str)) {
			return str.replaceAll(regex, replacement).split(replacement);
		}
		return null;
	}
	/**
	 * 将字符串转化为int
	 * 
	 * @param str
	 * @return
	 */
	public static int strToInt(String str) {
		if (null == str || str.equals("")) {
			return -1;
		}

		return Integer.parseInt(str.trim());
	}

	/**
	 * 判断字符串是否非空，true:是空字符或者null;false:不是空字符串
	 * 
	 * @param str
	 * @return true:是空字符或者null;false:不是空字符串
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str.trim())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据文件路径获取文件名
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		String[] arr = splitStr(filePath, "\\\\");
		if (arr == null) {
			return null;
		}
		return arr.length < 2 ? arr[0] : arr[arr.length - 1];
	}
	
	/**
	*需要被调用的js方法
	*/	
	public static native String strToMd5(String str)/*-{
		var dd = $wnd.hex_md5(str);
		return dd.toString();
	}-*/;
	
	/**
	 * 获取加前缀的字符串
	 * @return
	 */
	public static String getPrefixStr(String str) {
		return "a" + strToMd5(str);
	}
	
	/**
	 * 根据文件名获取文档名
	 * @param fileName
	 * @return
	 */
	public static String getFileNameByName(String fileName) {
		if (isNullOrEmpty(fileName)) {
			return null;
		}
		if (fileName.lastIndexOf(".")<0) {
			return fileName;
		}
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	/**
	 * 根据文件名获取文档名
	 * @param fileName
	 * @return
	 */
	public static String getFileTypeByFileName(String fileName) {
		if (isNullOrEmpty(fileName)) return null;
		if (fileName.lastIndexOf(".")<0) return null;
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}

	/**
	 * 根据扩展名得到文件类型
	 * @param suffix 文件扩展名
	 * @return
	 */
	public static String suffixToFileType(String suffix) {
		if (!StringUtil.isNullOrEmpty(suffix) && (suffix.toLowerCase().equals("doc")||suffix.toLowerCase().equals("docx"))){
			return "WORD";
		}
		if (!StringUtil.isNullOrEmpty(suffix) && (suffix.toLowerCase().equals("xls")||suffix.toLowerCase().equals("xlsx"))){
			return "EXCEL";
		}
		if (!StringUtil.isNullOrEmpty(suffix) && (suffix.toLowerCase().equals("ppt")||suffix.toLowerCase().equals("pptx"))){
			return "PPT";
		}
		if (!StringUtil.isNullOrEmpty(suffix) && (suffix.toLowerCase().equals("avi")||suffix.toLowerCase().equals("mp4"))){
			return "视频";
		}
		if (!StringUtil.isNullOrEmpty(suffix) && (suffix.toLowerCase().equals("wmv")||suffix.toLowerCase().equals("flv"))){
			return "视频";
		}
		if (!StringUtil.isNullOrEmpty(suffix) && suffix.toLowerCase().equals("mp3")){
			return "音频";
		}
		if (!StringUtil.isNullOrEmpty(suffix) && suffix.toLowerCase().equals("pdf")){
			return "PDF";
		}
		if (!StringUtil.isNullOrEmpty(suffix) && suffix.toLowerCase().equals("dwg")){
			return "CAD";
		} else {
			return "其他";
		}
	}
	
	public static String htmlFile2String (String htmlFilePath, HttpServletRequest req) {
		
		
        
	    String strBackUrl = "http://" + req.getServerName() //服务器地址  
	                        + ":"   
	                        + req.getServerPort()           //端口号  
	                        + req.getContextPath();      //项目名称  
		
		String result = "";
		String line = "";
		InputStream is;
		
	     String[] str = {"jpg","gif","png","jpeg","bmp"};
	     String[] arrPath =splitStr(htmlFilePath, "\\\\");
	     String newPath = "";
	     for (int x = 0; x < arrPath.length-1; x ++) {
	    	 newPath = newPath + arrPath[x] + "\\";
	     }
	     newPath = newPath + arrPath[arrPath.length-2] + ".files";
	     File dir =new File(newPath);
	     File[] files = dir.listFiles();
	     List<File> imageFiles = new ArrayList<File>();
	     for (int i=0; i<files.length; i++) {
	         //过滤非图片
	          String fileType = getFileTypeByFileName(files[i].getName());
	          for (int t=0; t<str.length; t++) {
	              if (str[t].equals(fileType.toLowerCase())){
	                  imageFiles.add(files[i]);           
	              }
	          }
	     } 
	     List<String> lstPath = new ArrayList<String>();
	     List<String> reletivePath = new ArrayList<String>();
	     List<String> absolutePath = new ArrayList<String>();
	     String rPath = "";
	     int location = 0;
	     for (int i = 0; i < imageFiles.size(); i++ ) {
	    	 arrPath = splitStr(imageFiles.get(i).getPath(), "\\\\");
	    	 for (int j =0; j< arrPath.length; j ++) {
	    		 lstPath.add(arrPath[j]);
	    	 }
	    	 String[] lstSh_Path = PropertiesUtil.getMsgsMap().get(Constants.STOREHOUSE_PATH).split("\\\\");
	    	 String strSh_Path = "";
	    	 for (int k=1; k < lstSh_Path.length; k++) {
	    		 strSh_Path = strSh_Path + lstSh_Path[k] + "/";
	    	 }
	    	 location = lstPath.indexOf(strSh_Path.substring(0, strSh_Path.length()-1));
	    	 for (int k = location+3; k < lstPath.size(); k ++) {
	    		 rPath = rPath + "/" + lstPath.get(k);
	    	 }
	    	 reletivePath.add(rPath.substring(1));
	    	 absolutePath.add(strBackUrl+"/"+lstPath.get(location)+"/"+lstPath.get(location+1) +"/"+lstPath.get(location+2) + rPath);
	     }
		try {
			is = new FileInputStream(htmlFilePath);
			BufferedReader bf = new BufferedReader(new InputStreamReader(is, "gb2312"));
			try {
				while ((line = bf.readLine()) != null) {// 循环一次读取一行
					for (int i = 0; i < reletivePath.size(); i++) {
						line = line.replace(reletivePath.get(i), absolutePath.get(i));
					}
					result = result + line + "\n";
				}
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// 加载文件
		catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	public static String backToReletiveUrl (String content) {
		
		String[] line = content.split("\n");
		String result = "";
		int location1 = -1;
		int location2 = -1;
		int location3 = -1;
		String imgUrl = "";
		String[] lstSh_Path = PropertiesUtil.getMsgsMap().get(Constants.STOREHOUSE_PATH).split("\\\\");
		String strSh_Path = "";
		for (int k = 1; k < lstSh_Path.length; k++) {
			strSh_Path = strSh_Path + "/" + lstSh_Path[k];
		}
		strSh_Path = strSh_Path + "/" + PropertiesUtil.getMsgsMap().get("Standard_home") + "/";
		//查找每一行内是否有包含“reportFileRoot/Standard_home”的图片URL地址，如果有取出来，替换成删除xxx.files之前部分
		String regex = ".*(http://) {1}.+"+ strSh_Path + "\\d+/\\d+.files/{1}\\w+.{1}(jpg|gif|png|jpeg|bmp) {1}.*";
		for (int i = 0; i < line.length; i++) {
			if (line[i].matches(regex)){
				//包含路径地址
				location1 = line[i].indexOf("http://");
				location2 = line[i].indexOf(strSh_Path) + strSh_Path.length();
				location3 = line[i].indexOf("/", line[i].indexOf("/", location2));
				imgUrl = line[i].substring(location1, location3 +1);
				line[i]=line[i].replace(imgUrl, "");
				}
			result = result + line[i] + "\n";
		}
		return result;
	}
	
	public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}