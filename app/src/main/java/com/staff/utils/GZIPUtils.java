package com.staff.utils;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPUtils {

	private GZIPUtils()
	{
        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static final int BUFFER = 1024;
	public static final String EXT = ".gz";
	

	/**
	 * 数据压缩
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	@TargetApi(VERSION_CODES.KITKAT)
	private static void compress(InputStream is, OutputStream os) throws IOException{
		GZIPOutputStream gos = null;
		// API高于19时，gos.flush()默认为同步刷新时会导致异常
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT){
			gos = new GZIPOutputStream(os, false);
		}else {
			gos = new GZIPOutputStream(os);
		}
		int count;
		byte[] data = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1){
			gos.write(data, 0, count);
		}
		gos.finish();
		gos.flush();
		gos.close();
	}
	
	/**
	 * 数据压缩
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] compress(byte[] data) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		compress(bais, baos);
		
		byte[] output = baos.toByteArray();
		
		baos.flush();
		baos.close();
		bais.close();
		return output;
	}
	
	/**
	 * 文件压缩
	 * @param file
	 * @param isDelete
	 * @throws Exception
	 */
	public static void compress(File file, boolean isDelete) throws Exception{
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);
		compress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();
		if (isDelete){
			file.delete();
		}
	}
	
	/**
	 * 压缩文件
	 * @param file
	 * @throws Exception 
	 */
	public static void compress(File file) throws Exception{
		compress(file, true);
	}
	
	/**
	 * 解压缩数据
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	private static void decompress(InputStream is, OutputStream os) throws Exception{
		GZIPInputStream gis = new GZIPInputStream(is);
		int count;
		byte[] data = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1){
			os.write(data, 0, count);
		}
		gis.close();
	}
	
	/**
	 * 数据解压缩
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		decompress(bais, baos);
		data = baos.toByteArray();
		
		baos.flush();
		baos.close();
		bais.close();
		return data;
	}
	
	/**
	 * 文件解压缩
	 * @param file
	 * @param isDelete
	 * @throws Exception
	 */
	public static void decompress(File file, boolean isDelete) throws Exception{
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT, ""));
		decompress(fis, fos);
		
		fis.close();
		fos.flush();
		fos.close();
		if(isDelete){
			file.delete();
		}
	}
	
	/**
	 * 文件解压缩
	 * @param file
	 * @throws Exception
	 */
	public static void decompress(File file) throws Exception{
		decompress(file, true);
	}
	
	/**
	 * 文件解压缩
	 * @param filename
	 * @param isDelete
	 * @throws Exception
	 */
	public static void decompress(String filename, boolean isDelete) throws Exception{
		File file = new File(filename);
		decompress(file, isDelete);
	}
}
