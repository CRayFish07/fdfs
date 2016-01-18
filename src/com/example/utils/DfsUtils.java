package com.example.utils;

import java.io.IOException;

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
	
	public static String upload(String path) {
		try {
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageClient storageClient = new StorageClient(trackerServer, null);
			
			String extName = path.substring(path.lastIndexOf(".")+1);
			String[] results = storageClient.upload_file(path, extName , null);
			if(results!=null && results.length>0) {
				return "/"+results[0]+"/"+results[1];
			}
		} catch (IOException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
}
