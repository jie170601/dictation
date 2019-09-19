package util;

import bean.Param;

/**
 * 系统配置参数读写工具类
 * @author jie
 *
 */
public class Config {
	
	//配置文件保存在项目根目录下
	//用的二进制文件保存
	//所以文件没有加后缀
	private final static String configFile = "./.config";
	
	/**
	 * 将参数的改动保存到配置文件中
	 * @throws Exception
	 */
	public static void saveParam(Param param) throws Exception{
		//给配置文件写数据
		byte[] data = param2bytes(param);
		FileUtil.write(data, configFile);
	}
	
	/**
	 * 从配置文件中读取参数
	 * @return
	 * @throws Exception
	 */
	public static Param findParam() throws Exception{
		Param param = new Param();
		//检测配置文件是否存在
		if(!FileUtil.exist(configFile)) {
			//如果配置文件不存在，则创建配置文件并赋默认值
			saveParam(param);
			//返回默认配置值
			return param;
		}
		//如果配置文件存在，返回配置文件配置信息
		byte[] data = FileUtil.read(configFile);
		return bytes2param(data);
	}
	
	/**
	 * 参数转字节数组
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private static byte[] param2bytes(Param param) throws Exception{
		byte[] data = new byte[5];
		//循环次数的取值是1~10，用一个字节可以保存
		data[0] = param.getLoop();
		//循环间隔取值0~10，用一个字节保存
		data[1] = param.getPause();
		//单词间隔取值0~10，用一个字节保存
		data[2] = param.getInterval();
		//随机播放和美式发音可以用一个bit保存
		//但是为了读取方便，还是用一个字节保存
		data[3] = (byte) (param.isRandom()?1:0);
		data[4] = (byte) (param.isAmerican()?1:0);
		return data;
	}
	
	/**
	 * 将字节数组转换成Param对象
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static Param bytes2param(byte[] data) throws Exception{
		Param param = new Param();
		param.setLoop(data[0]);
		param.setPause(data[1]);
		param.setInterval(data[2]);
		param.setRandom(data[3]==1?true:false);
		param.setAmerican(data[4]==1?true:false);
		return param;
	}
}
