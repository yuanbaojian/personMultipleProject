package com.atoz.capp.server.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

/**
 * 使用ant下的包 zip的解压缩工具类
 */
public class ZipUtils {
	static final int BUFFER = 1024;

	public static void main(String[] args) {
		try {
//			zip("c:\\alipay", "d:\\zip\\z.zip");
			unZip("d:\\zip\\z.zip", "d:\\zip\\temp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 用zip格式压缩文件
	 * 
	 * @param inputFile
	 *            要压缩的文件 可以是文件或文件夹 如："c:\\test" 或 "c:\\test.doc"
	 * @param zipFileDir
	 *            压缩后的文件名 包含路径 如："c:\\test.zip"
	 * @throws Exception
	 *             ant下的zip工具默认压缩编码为UTF-8编码，
	 *             而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
	 *             用ant压缩后放到windows上面会出现中文文件名乱码，用winRAR压缩的文件，用ant解压也会出现乱码，
	 *             所以，在用ant处理winRAR压缩的文件时，要设置压缩编码
	 */
	public static void zip(String inputFile,String zipFileDir) throws Exception {
		File zipFile = new File(zipFileDir);
		if (!zipFile.exists()){
			zipFile.mkdirs();
			zipFile.delete();
			zipFile.createNewFile();
		}
		File f = new File(inputFile);
		// File temp = new File(zipFileName);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
		// 设置压缩编码
		out.setEncoding("GBK");// 设置为GBK后在windows下就不会乱码了，如果要放到Linux或者Unix下就不要设置了
		File[] fl = f.listFiles();
		String base = "";
		for (int i = 0; i < fl.length; i++) {
			zip(out, fl[i], base + fl[i].getName());
		}
		//zip(out, f, f.getName());// 递归压缩方法
		System.out.println("zip done");
		out.close();
	}

	private static void zip(ZipOutputStream out, File f, String base) throws Exception {
		System.out.println("Zipping   " + f.getName()); // 记录日志，开始压缩
		if (f.isDirectory()) { // 如果是文件夹，则获取下面的所有文件
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));// 此处要将文件写到文件夹中只用在文件名前加"/"再加文件夹名
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else { // 如果是文件，则压缩
			out.putNextEntry(new ZipEntry(base)); // 生成下一个压缩节点
			FileInputStream in = new FileInputStream(f); // 读取文件内容
			int len;
			byte[] buf = new byte[BUFFER];
			while ((len = in.read(buf, 0, BUFFER)) != -1) {
				out.write(buf, 0, len); // 写入到压缩包
			}
			in.close();
		}
	}

	/**
	 * 解压缩zip文件
	 * 
	 * @param fileName
	 *            要解压的文件名 包含路径 如："c:\\test.zip"
	 * @param filePath
	 *            解压后存放文件的路径 如："c:\\temp\\"
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void unZip(String fileName, String filePath) throws Exception {
		if (!filePath.endsWith(File.separator)){
			filePath += File.separator;
		}
		ZipFile zipFile = new ZipFile(fileName, "GBK"); // 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
		Enumeration emu = zipFile.getEntries();

		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			if (entry.isDirectory()) {
				new File(filePath + entry.getName()).mkdirs();
				continue;
			}
			BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));

			File file = new File(filePath + entry.getName());
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
			byte[] buf = new byte[BUFFER];
			int len = 0;
			while ((len = bis.read(buf, 0, BUFFER)) != -1) {
				fos.write(buf, 0, len);
			}
			bos.flush();
			bos.close();
			bis.close();
			System.out.println("解压文件：" + file.getName());
		}
		System.out.println("unzip done");
		zipFile.close();
	}
	
	/** 
     * @param rarFileName rar file name 
     * @param outFilePath output file path   
     * @throws Exception 
     */  
    public static void unrar(String rarFileName, String outFilePath)  throws  Exception{   
    	rarFileName = rarFileName.replaceAll("\\\\", "/");
    	FileInputStream is = new FileInputStream(rarFileName);
    	 Archive archive = new  Archive(is);
         List<FileHeader> files =  archive.getFileHeaders();  
         for (FileHeader fh : files) {
        	 String fileName = fh.getFileNameString();  
             if (fileName != null && fileName.trim().length() > 0 && fh.getFileAttr() == 32) {
                 String saveFileName = outFilePath+"\\"+fileName;  
                 File saveFile = new File(saveFileName);  
                 File parent =  saveFile.getParentFile();  
                 if (!parent.exists()){
                     parent.mkdirs();  
                 }
                 if (!saveFile.exists()){
                     saveFile.createNewFile();  
                 }
                 FileOutputStream fos = new FileOutputStream(saveFile);  
                 archive.extractFile(fh, fos);   
                 fos.flush();  
                 fos.close();
             }  
    	}
        archive.close();
    }
}
