package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件读写工具类
 * @author jie
 *
 */
public class FileUtil {
	
	/**
	 * 通过文件名读取文件数据到byte数组
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static byte[] read(String fileName) throws Exception{
		int len = 0;
		byte[] buffer = new byte[1024];
		File file = new File(fileName);
		FileInputStream inputStream = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		inputStream.close();
		bos.close();
		return bos.toByteArray();
	}
	
	/**
	 * 删除指定目录下所有的文件
	 * @param dir
	 * @throws Exception
	 */
	public static void removeDir(File dir) throws Exception{
		File[] files=dir.listFiles();
		for(File file:files){
			if(file.isFile()){
				file.delete();
			}
		}
	}
	
	/**
	 * 将byte数组写入指定文件中
	 * @param data
	 * @param fileName
	 * @throws Exception
	 */
	public static void write(byte[] data,String fileName) throws Exception{
		File file = new File(fileName);
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(data);
		outputStream.close();
	}
	
	/**
	 * 检测文件是否存在
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean exist(String fileName) throws Exception{
		File file = new File(fileName);
		return file.exists();
	}
}
