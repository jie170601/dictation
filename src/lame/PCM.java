package lame;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * pcm文件合并类
 * 将多个pcm文件合并成一个pcm
 * @author jie
 *
 */
public class PCM {
	
	private static String path = "./tmp/";
	private static String mergedFile = "merged.pcm";
	
	public static void merge(String[] files,int loop,byte[] pause,byte[] interval) throws Exception {
		List<Byte> byteList = new ArrayList<Byte>();
		for(int i=0;i<files.length;i++) {
			String fileStr = files[i];
			byte[] bytes = readFile(fileStr);
			for(int j=0;j<loop;j++) {
				for(int k=0;k<bytes.length;k++) {
					byteList.add(bytes[k]);
				}
				for(int k=0;k<pause.length;k++) {
					byteList.add(pause[k]);
				}
			}
			for(int j=0;j<interval.length;j++) {
				byteList.add(interval[j]);
			}
		}
		byte[] bs = new byte[byteList.size()];
		for(int i=0;i<byteList.size();i++) {
			bs[i] = byteList.get(i);
		}
		writeFile(bs);
	}
	
	private static byte[] readFile(String fileStr) throws Exception {
		byte[] buffer = new byte[1024];
		int len = 0;
		File file = new File(path+fileStr);
		FileInputStream inputStream = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		inputStream.close();
		bos.close();
		return bos.toByteArray();
	}
	
	private static void writeFile(byte[] bytes) throws IOException {
		File file = new File(path+mergedFile);
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(bytes);
		outputStream.close();
	}
}
