package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.csvreader.CsvReader;

import http.URL2PM3;
import lame.MP32PCM;
import lame.PCM;
import lame.PCM2MP3;
import lame.Silence;
import util.ArrayRandom;

/**
 * 生成音频按钮点击事件处理
 * 生成单词音频的主要逻辑都在本类中
 * @author 邓集阶
 *
 */
public class MainButtonListener implements ActionListener{
	
	private final String ttsApi = "https://dict.youdao.com/dictvoice?le=auto";
	private String fileName = "";
	private JButton button;
	
	/**
	 * 按钮点击事件处理
	 * 点击按钮后弹出文件选择框，用户自己选择文件保存的路径及文件名
	 * 点击文件选择框的确定按钮后执行音频文件生成流程
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		button = (JButton) arg0.getSource();
		//文件选择器初始路径为项目所在路径
		//本来打算做成保存上一次选择路径的
		//但是没有做了
		JFileChooser chooser = new JFileChooser(new File("./"));
		//默认选择mp3后缀的文件
		FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3文件","mp3");
		chooser.setFileFilter(filter);
		int returnValue = chooser.showSaveDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION) {
			button.setEnabled(false);
			button.setText("音频生成中……");
			fileName = chooser.getSelectedFile().getAbsolutePath();
			//如果没有.mp3后缀，则自动加上mp3后缀
			if(!fileName.endsWith(".mp3")&&!fileName.endsWith(".MP3")) {
				fileName = fileName+".mp3";
			}
			MainButtonListener listener = this;
			try {
				new Thread() {
					public void run() {
						try {
							listener.createAudio(fileName);
							JOptionPane.showMessageDialog(null, "路径为:\r\n"+fileName,"音频生成成功",JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "原因:\r\n"+e.getMessage(),"音频生成失败",JOptionPane.ERROR_MESSAGE);
						}
						button.setEnabled(true);
						button.setText("生成音频");
					}
				}.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createAudio(String fileName) throws Exception{
		//获取单词字符串
		String wordStr = InputPanel.area.getText();
		//解析单词字符串得到单词列表
		List<String> words = getWords(wordStr);
		//根据单词列表下载单词音频
		String[] urls = getUrls(words);
		//如果是随机播放，则打乱urls数组的顺序
		if(Main.param.isRandom()) {
			urls = ArrayRandom.getArray(urls);
		}
		String[] files = URL2PM3.download(urls,new MyCallBack(button,"下载中："));
		//将mpeg-2 layout 3格式的音频文件转为pcm格式的
		files = MP32PCM.resample(files,new MyCallBack(button,"重采样："));
		files = MP32PCM.toPCM(files,new MyCallBack(button,"转PCM："));
		//生成空白的音频数据
		byte[] interval = Silence.pcm(Main.param.getInterval());
		byte[] pause = Silence.pcm(Main.param.getPause());
		//将所有音频数据拼接起来
		button.setText("正在合并音频……");
		PCM.merge(files, Main.param.getLoop(), pause,interval);
		//将合并的PCM文件压缩成MP3文件
		button.setText("正在生成MP3……");
		PCM2MP3.toMP3(fileName);
	}
	
	/**
	 * 使用CsvReader解析csv格式字符串
	 * 得到单词列表
	 * @param wordStr
	 * @return
	 * @throws Exception
	 */
	private List<String> getWords(String wordStr) throws Exception{
		List<String> words = new ArrayList<String>();
		CsvReader reader = CsvReader.parse(wordStr);
		while(reader.readRecord()) {
			String[] temp = reader.getValues();
			for(int i=0;i<temp.length;i++) {
				words.add(temp[i]);
			}
		}
		return words;
	}
	
	/**
	 * 根据单词和系统参数拼接出音频url
	 * @param words
	 * @return
	 * @throws Exception
	 */
	private String[] getUrls(List<String> words) throws Exception{
		String[] urls = new String[words.size()];
		StringBuilder builder = null;
		for(int i=0;i<words.size();i++) {
			builder = new StringBuilder(ttsApi);
			builder.append("&audio=");
			builder.append(URLEncoder.encode(words.get(i),"UTF-8"));
			builder.append("&type=");
			builder.append(Main.param.isAmerican()?"2":"1");
			urls[i] = builder.toString();
		}
		return urls;
	}
}

class MyCallBack implements CallBack{
	
	private JButton button;
	private String info;
	
	public MyCallBack(JButton button,String info) {
		this.button = button;
		this.info = info;
	}

	@Override
	public void run(int i, int l) throws Exception {
		button.setText(info+i+"/"+l);
	}
	
}