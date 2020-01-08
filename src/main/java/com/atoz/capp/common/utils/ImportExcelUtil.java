package com.atoz.capp.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.atoz.capp.server.util.StringUtil;


public class ImportExcelUtil {
	
	private final static String excel2003L =".xls";    //2003- 版本的excel
	private final static String excel2007U =".xlsx";   //2007+ 版本的excel
	private static FormulaEvaluator evaluator;
	/**
	 * 描述：获取IO流中的数据，组装成List<List<Object>>对象
	 * @param in,fileName
	 * @param index 指定读取excel的第几个 sheet, 0:表示读取所有sheet
	 * @return
	 * @throws IOException 
	 */
	public static List<List<Object>> getBankListByExcel(InputStream in,String fileName,int index) throws Exception{
		List<List<Object>> list = null;
		
		//创建Excel工作薄
		Workbook work = getWorkbook(in,fileName);
		if (null == work) {
			throw new Exception("创建Excel工作薄为空！");
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		list = new ArrayList<List<Object>>();
		int m=0;
		if (index < 0 || index > work.getNumberOfSheets()){
			return list;
		} else if (index == 0) {
			index=work.getNumberOfSheets();
		} else {
			m=index-1;
		}
		//遍历Excel中所有的sheet
		for (int i = m; i < index; i++) {
			sheet = work.getSheetAt(i);
			if (sheet==null) {continue;}
			//遍历当前sheet中的所有行
			for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				//遍历所有的列
				List<Object> li = new ArrayList<Object>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					li.add(getCellValue(cell));
				}
				list.add(li);
			}
		}
		work.close();
		in.close();
		return list;
	}
	
	/**
	 * 描述：获取IO流中的数据，组装成List<List<Object>>对象
	 * @param in,fileName
	 * @param index 指定读取excel的第几个 sheet, 0:表示读取所有sheet
	 * @param map 文件不符合规则时，将不符合的规则信息放入map的error里: 1第一行标题的第一个标题不是PartNumber ; 2第一行有重复的标题; 3有重复的partNo,4有空值存在,5标题必须包含“材料密度”
	 * @return
	 * @throws IOException 
	 */
	public List<Map<String,Object>> getListByExcel(InputStream in,String fileName,int index,Map<String,String> map) throws Exception{
		List<Map<String,Object>> list = null;
		List<String> partNoList = new ArrayList<String>();//记录重复的partNo
		List<String> keyList = new ArrayList<String>();//记录重复的key
		Map<String,Object> valueMap = null;
		//创建Excel工作薄
		Workbook work = getWorkbook(in,fileName);
		if (null == work) {
			throw new Exception("创建Excel工作薄为空！");
		}
		evaluator = work.getCreationHelper().createFormulaEvaluator();
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		list = new ArrayList<Map<String,Object>>();
		int m=0;
		if (index < 0 || index > work.getNumberOfSheets()){
			return list;
		} else if (index == 0) {
			index=work.getNumberOfSheets();
		} else {
			m=index-1;
		}
		Object valueObj = new Object();
		int allColumn = 0;
		//遍历Excel中所有的sheet
		for (int i = m; i < index; i++) {
			sheet = work.getSheetAt(i);
			if (sheet==null) {continue;}
			//遍历当前sheet中的所有行
			for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				//遍历所有的列
				valueMap = new LinkedHashMap<String,Object>();
				if (j == 0) {
					allColumn = row.getLastCellNum();
				}
				for (int y = row.getFirstCellNum(); y <= allColumn; y++) {
					cell = row.getCell(y);
					valueObj = cell == null ? "" : getCellValue(cell);
					if (j==0) {
						if (y==0&&!"PartNumber".equals(valueObj.toString())){
							map.put("error", "上传文件1行A列必须是：PartNumber");
							return null;
						}
						if (keyList.contains(valueObj.toString())){
							map.put("error", "上传文件存在重复属性名称："+valueObj.toString());
							return null;
						}
						if (!StringUtil.isNullOrEmpty(valueObj.toString())){
							keyList.add(valueObj.toString());
						}
					} else {
						if (keyList.size()>y) {
							if (y==0) {
								if (partNoList.contains(valueObj.toString())){
									map.put("error", "上传文件PartNumber列存在重复值："+valueObj.toString());
									return null;
								}
								partNoList.add(valueObj.toString());
							}
							valueMap.put(keyList.get(y), valueObj);
						} else {
							break;
						}
					}
				}
				if ( j!= 0) {
					list.add(valueMap);
				}
			}
		}
		work.close();
		in.close();
		return list;
	}
	
	/**
	 * 描述：根据文件后缀，自适应上传文件的版本 
	 * @param inStr,fileName
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (excel2003L.equals(fileType)){
			wb = new HSSFWorkbook(inStr);  //2003-
		} else if (excel2007U.equals(fileType)){
			wb = new XSSFWorkbook(inStr);  //2007+
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

	/**
	 * 描述：对表格中数值进行格式化
	 * @param cell
	 * @return
	 */
	public static Object getCellValue(Cell cell) {
		Object value = null;
		// 日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		// 格式化数字
		DecimalFormat df2 = new DecimalFormat("0.0000");
		
		switch (cell.getCellType()) {
		case STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case NUMERIC:
			if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
				value = sdf.format(cell.getDateCellValue());
			} else {
				value = Double.parseDouble(df2.format(cell.getNumericCellValue()));
			}
			break;
		case BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case BLANK:
			value = "";
			break;
		case FORMULA:
			value = getCellValue(evaluator.evaluate(cell));
			break;
		case ERROR:
			value = "";
			break;
		default:
			value ="";
			break;
		}
		return value;
	}
	
	private static String getCellValue(CellValue cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
        case STRING:
            cellValue=cell.getStringValue();
            break;
        case NUMERIC:
            cellValue=String.valueOf(cell.getNumberValue());
            break;
		case BLANK:
			cellValue = "";
			break;
        case FORMULA:
        	cellValue="";
            break;
        default:
        	cellValue="";
            break;
        }
        
        return cellValue;
    }

}