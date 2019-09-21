package util;

import java.util.Random;

/**
 * ϴ���㷨
 * �������һ������
 * @author wss
 *
 */
public class ArrayRandom {
	
	/**
	 * ͨ���������λ������������
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
