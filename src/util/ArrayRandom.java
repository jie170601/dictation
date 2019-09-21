package util;

import java.util.Random;

/**
 * 洗牌算法
 * 随机打乱一个数组
 * @author wss
 *
 */
public class ArrayRandom {
	
	/**
	 * 通过随机交换位置来打乱数组
	 * @param array
	 * @return
	 * @throws Exception
	 */
	public static String[] getArray(String[] array) throws Exception{
		if(array==null) return null;
		int length = array.length;
		Random random = new Random();
		for(int i=0;i<length;i++) {
			int index = random.nextInt(length);
			String tmp = array[i];
			array[i] = array[index];
			array[index] = tmp;
		}
		return array;
	}

}
