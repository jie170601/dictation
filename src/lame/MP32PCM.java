package lame;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * ʹ��lame��mp3�ļ������pcm�ļ�
 * �����������
 * @author jie
 *
 */
public class MP32PCM {
	
	/**
	 * ��mp3�ļ�����Ϊpcm�ļ�
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
	 * ���²�����16kHz
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
	 * ִ��cmd����
	 * @param cmdCode
	 */
	private static void exeCmd(String cmdCode) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(cmdCode);
			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
