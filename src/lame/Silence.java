package lame;

/**
 * 生成指定时间的空白声音数据
 * 为16kHz的16位单声道pcm文件对应数据
 * @author jie
 *
 */
public class Silence {
	
	//16kHz的采样率
	private static int rate = 16000;
	
	/**
	 * 生成对应时间（秒）的空白音频数据
	 * @param s 要生成音频数据的时长，单位是”秒“
	 */
	public static byte[] pcm(int s) {
		//每秒rate个数据，s秒共s*rate个数据
		int length = s*rate;
		return new byte[length];
	}
}
