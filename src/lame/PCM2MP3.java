package lame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

/**
 * ���ϳɵ�pcm�ļ�תΪmp3�ļ�
 * @author jie
 *
 */
public class PCM2MP3 {
	private static String path = "./tmp/";
	private static String mergedFile = "merged.pcm";
	
	public static void toMP3(String fileName) {
		String cmd = "./lib/lame.exe -r -s 16 -m m ";
		StringBuilder builder = new StringBuilder(cmd);
		builder.append(path);
		builder.append(mergedFile);
		builder.append(" ");
		builder.append(fileName);
		String cmdCode = builder.toString();
		exeCmd(cmdCode);
	}
	
	/**
	 * ִ��cmd����
	 * @param cmdCode
	 */
	private static void exeCmd(String cmdCode) {
		try {
			Process p = Runtime.getRuntime().exec(cmdCode);
			InputStream in = p.getInputStream();
			InputStream ein = p.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));
			BufferedReader ebr = new BufferedReader(new InputStreamReader(ein, "GBK"));
            String line = null;
            while((line = ebr.readLine())!=null){}
            while((line = br.readLine())!=null){}
			p.waitFor();
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "ԭ��:\r\n"+e.getMessage(),"��Ƶ����ʧ��",JOptionPane.ERROR_MESSAGE);
		}
	}
}
