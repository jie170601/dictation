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
	 * 后面发现每个音频文件的采样率并不是一样的
	 * 所以将每个音频文件的采样率保存了下来
	 * 后面转换回mp3的时候需要用到
	 */
	public static String[] rates = null;
	
	/**
	 * 将mp3文件解码为pcm文件
	 * @param files
	 * @throws Exception
	 */
	public static String[] toPCM(String[] files)throws Exception{
		String[] pcmFiles = new String[files.length];
		rates = new String[files.length];
		String cmd = "./lib/lame.exe --decode -t ";
		for(int i=0;i<files.length;i++) {
			String file = files[i];
			pcmFiles[i] = file+".pcm";
			StringBuilder builder = new StringBuilder(cmd);
			builder.append(file);
			builder.append(" "+pcmFiles[i]);
			String cmdCode = builder.toString();
			rates[i] = exeCmd(cmdCode);
		}
		for(int i=0;i<rates.length;i++) {
			System.out.println(rates[i]);
		}
		return pcmFiles;
	}
	
	/**
	 * 执行cmd命令
	 * @param cmdCode
	 */
	public static String exeCmd(String cmdCode) {
		BufferedReader br = null;
		String line = null;
		try {
			Process p = Runtime.getRuntime().exec(cmdCode);
			System.out.println(cmdCode);
			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
			System.out.println(br.read());
			while((line = br.readLine())!=null) {
				System.out.println(line);
			}
			int s = line.indexOf('(');
			int e = line.indexOf(" kHz");
			line = line.substring(s+1,e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return line;
	}
}
