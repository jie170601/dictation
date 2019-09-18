package http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 将tts api的url下载为mp3文件
 * @author jie
 *
 */
public class Url2Mp3 {
	
	//用来存放临时音频文件的文件夹
	private static String tempPath = "./tmp/";
	
	/**
	 * 下载所有音频文件到临时文件夹
	 * @param urls
	 * @throws Exception
	 */
	public static void download(String[] urls) throws Exception {
		for(int i=0;i<urls.length;i++) {
			download(urls[i],i+".mp3");
		}
	}
	
	/**
	 * 根据url路径下载音频文件
	 * @param urlStr
	 * @param fileName
	 * @throws Exception
	 */
	private static void download(String urlStr,String fileName) throws Exception{
		URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(tempPath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
	}
	
	/**
	 * 从输入流读取数据到byte数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
}
