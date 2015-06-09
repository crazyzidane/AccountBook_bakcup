package com.example.accountbook.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {
	
	private static final int BYTE_SIZE = 4 * 1024 * 1024;
	
	
	public static byte[] getBytesFromFile(File file){
		if(!file.exists()){
			throw new IllegalArgumentException("文件：" + file + "不存在");
		}
		
		if(!file.isFile()){
			throw new IllegalArgumentException(file + "不是文件");
		}
		
		byte[] ret = null;
		
		try {
			
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(BYTE_SIZE);
			byte[] b = new byte[BYTE_SIZE];
			int n;
			while((n=fis.read(b)) != -1){
				baos.write(b, 0, n);
			}
			
			ret = baos.toByteArray();
			
			fis.close();
			baos.close();
			
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param path
	 * @param fileBody
	 * @param fileName
	 * @return
	 */
	public static void sendFormByPost(final String path, 
											final byte[] fileBody, final String fileName){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(path);
					MultipartEntity entity = new MultipartEntity();
					entity.addPart("thedbfile", new ByteArrayBody(fileBody, fileName));
					httpPost.setEntity(entity);
					httpClient.execute(httpPost);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}).start();
		
	}

}
