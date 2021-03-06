package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class DfsUtils {
	
	static {//加载配置文件
		String config = DfsUtils.class.getClassLoader().getResource("fdfs_client.conf").getPath();
		try {
			ClientGlobal.init(config);
		} catch (IOException | MyException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 通过输入流的方式上传文件至fastdfs, 将byte[]的长度默认设为1024*100, 即100kb
	 * @param in	输入流
	 * @param extName	文件的扩展名
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static String upload(InputStream in, String extName) throws IOException, MyException {
		return upload(in, extName, 1024*100);
	}
	
	
	/**
	 * 通过输入流的方式上传文件至fastdfs
	 * @param in	输入流
	 * @param extName	文件的扩展名
	 * @param len	通过byte[]向fastfds写入,每次循环时byte[]的长度
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static String upload(InputStream in, String extName, int len) throws IOException, MyException {
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageClient storageClient = new StorageClient(trackerServer, null);
		byte[] buf = new byte[len];
		in.read(buf);
		String[] results = storageClient.upload_appender_file(buf, extName, null);
		if(results!=null && results.length>=2) {
			while(in.read(buf)!=-1) {
				storageClient.append_file(results[0], results[1], buf);
			}
			String remotePath = "/"+results[0]+"/"+results[1];
			String localPath = getStorePath(WebUtils.makeRandomStr(6), extName);
			RedisUtils.getInstance().set(Const.KEY_PREFIX+localPath , remotePath);
			return localPath;
		}
		return null;
	}
	
	
	/**
	 * 上传文件
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static String upload(String path) throws IOException, MyException {
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageClient storageClient = new StorageClient(trackerServer, null);
		
		String extName = path.substring(path.lastIndexOf(".")+1);
		String[] results = storageClient.upload_file(path, extName , null);
		if(results!=null && results.length>0) {
			String remotePath = "/"+results[0]+"/"+results[1];
			String localPath = getStorePath(path, extName);
			RedisUtils.getInstance().set(Const.KEY_PREFIX + localPath, remotePath);
			return localPath;
		}
		return "";
	}
	
	/**
	 * 删除文件
	 * @param path
	 * @throws IOException
	 * @throws MyException 
	 */
	public static void remove(String path) throws IOException, MyException {
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageClient storageClient = new StorageClient(trackerServer, null);
		String temp = RedisUtils.getInstance().get(Const.KEY_PREFIX+path);
		RedisUtils.getInstance().del(Const.KEY_PREFIX+path);
		System.out.println(temp);
		if(StringUtils.isNotEmpty(temp)) {
			path = temp;
		}
		path = path.substring(1);
		int index = path.indexOf("/");
		String group = path.substring(0, index);
		System.out.println("group="+group);
		String remoteFilePath = path.substring(index+1);
		System.out.println("remoteFilePath="+remoteFilePath);
		storageClient.delete_file(group, remoteFilePath);
	}
	
	
	public static String getStorePath(String path, String extName) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String filename = Encodes.encodeByMD5(path+System.currentTimeMillis());
		return Const.UPLOAD_DIR + "/" + dateFormat.format(new Date()) + "/" + filename + "."+extName;
	}
}
