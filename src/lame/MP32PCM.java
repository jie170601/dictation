package lame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import gui.CallBack;

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
	public static String[] toPCM(String[] files,CallBack callBack)throws Exception{
		int length = files.length;
		String[] pcmFiles = new String[length];
		String cmd = "./lib/lame.exe --decode -t ";
		for(int i=0;i<length;i++) {
			String file = files[i];
			pcmFiles[i] = file+".pcm";
			StringBuilder builder = new StringBuilder(cmd);
			builder.append(file);
			builder.append(" "+pcmFiles[i]);
			String cmdCode = builder.toString();
			exeCmd(cmdCode);
			callBack.run(i+1,length);
		}
		return pcmFiles;
	}
	
	/**
	 * 重新采样到16kHz
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public static String[] resample(String[] files,CallBack callBack) throws Exception{
		int length = files.length;
		String[] refiles = new String[length];
		String cmd = "./lib/lame.exe --resample 16 ";
		for(int i=0;i<length;i++) {
			String file = files[i];
			refiles[i] = file+".tmp.mp3";
			StringBuilder builder = new StringBuilder(cmd);
			builder.append(file);
			builder.append(" "+refiles[i]);
			String cmdCode = builder.toString();
			exeCmd(cmdCode);
			callBack.run(i+1,length);
		}
		return refiles;
	}
	
	/**
	 * 执行cmd命令
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
			JOptionPane.showMessageDialog(null, "原因:\r\n"+e.getMessage(),"音频生成失败",JOptionPane.ERROR_MESSAGE);
		}
	}
}
