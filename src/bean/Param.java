package bean;

/**
 * 系统参数实体类
 * @author jie
 *
 */
public class Param {
	
	//循环次数
	private byte loop = 1;
	//循环间停顿时间，单位：s
	private byte pause = 3;
	//单词间停顿时间，单位：s
	private byte interval = 5;
	//是否乱序播放
	private boolean random;
	//是否美式发音
	private boolean american;
	
	public byte getLoop() {
		return loop;
	}
	public void setLoop(byte loop) {
		this.loop = loop;
	}
	public byte getPause() {
		return pause;
	}
	public void setPause(byte pause) {
		this.pause = pause;
	}
	public byte getInterval() {
		return interval;
	}
	public void setInterval(byte interval) {
		this.interval = interval;
	}
	public boolean isRandom() {
		return random;
	}
	public void setRandom(boolean random) {
		this.random = random;
	}
	public boolean isAmerican() {
		return american;
	}
	public void setAmerican(boolean american) {
		this.american = american;
	}
}
