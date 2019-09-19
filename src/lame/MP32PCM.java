package lame;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 使用lame将mp3文件解码成pcm文件
 * 方便后续操作
 * @author jie
 *
 */
public class MP32PCM {
	
	/**
	 * 将mp3文件解码为pcm文件
	 * @param files
	 * @throws Exception
	 */
	public static String[] toPCM(String[] files)throws Exception{
		String[] pcmFiles = new String[files.length];
		String cmd = "./lib/lame.exe --decode -t ";
		for(int i=0;i<files.length;i++) {
			String file = files[i];
			pcmFiles[i] = file+".pcm";
			StringBuilder builder = new StringBuilder(cmd);
			builder.append(file);
			builder.append(" "+pcmFiles[i]);
			String cmdCode = builder.toString();
			exeCmd(cmdCode);
		}
		return pcmFiles;
	}
	
	/**
	 * 重新采样到16kHz
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public static String[] resample(String[] files) throws Exception{
		String[] refiles = new String[files.length];
		String cmd = "./lib/lame.exe --resample 16 ";
		for(int i=0;i<files.length;i++) {
			String file = files[i];
			refiles[i] = file+".tmp.mp3";
			StringBuilder builder = new StringBuilder(cmd);
			builder.append(file);
			builder.append(" "+refiles[i]);
			String cmdCode = builder.toString();
			exeCmd(cmdCode);
		}
		return refiles;
	}
	
	/**
	 * 执行cmd命令
	 * @param cmdCode
	 */
	private static String exeCmd(String cmdCode) {
		BufferedReader br = null;
		String line = null;
		try {
			Process p = Runtime.getRuntime().exec(cmdCode);
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
}
