package com.example.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Hex;


/**
 * 文件处理工具
 * @author Administrator
 *
 */
public class FileUtils {

	
	/**
	 * 获取输入流的文件的魔数
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String getFileHeader(InputStream in) throws IOException {
		byte[] buf = new byte[28];
		in.read(buf, 0, 28);
		
		return Hex.encodeHexString(buf);
	}
	
	/**
	 * 根据文件的魔数判断文件的类型并返回
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static FileType getType(InputStream in) throws IOException {
		String fileHead = getFileHeader(in);
		if(fileHead==null || fileHead.length() ==0) {
			return null;
		}
		
		fileHead = fileHead.toUpperCase();
		FileType[] types = FileType.values();
		for(FileType fileType : types) {
			if(fileHead.startsWith(fileType.getValue())) {
				return fileType;
			}
		}
		
		return null;
	}
}
