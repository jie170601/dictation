package lame;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 将合成的pcm文件转为mp3文件
 * @author jie
 *
 */
public class PCM2MP3 {
	private static String path = "./tmp/";
	private static String mergedFile = "merged.pcm";
	
	public static void toMP3() {
		String cmd = "./lib/lame.exe -r -s 16 --resample 16 -m m ";
		StringBuilder builder = new StringBuilder(cmd);
		builder.append(path);
		builder.append(mergedFile);
		builder.append(" ./tmp/");
		builder.append("megred.mp3");
		String cmdCode = builder.toString();
		exeCmd(cmdCode);
	}
	
	/**
	 * 执行cmd命令
	 * @param cmdCode
	 */
	private static void exeCmd(String cmdCode) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(cmdCode);
			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			System.out.println(sb.toString());
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
	}
}
