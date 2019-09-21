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
 * ������Ƶ��ť����¼�����
 * ���ɵ�����Ƶ����Ҫ�߼����ڱ�����
 * @author �˼���
 *
 */
public class MainButtonListener implements ActionListener{
	
	private final String ttsApi = "https://dict.youdao.com/dictvoice?le=auto";
	private String fileName = "";
	private JButton button;
	
	/**
	 * ��ť����¼�����
	 * �����ť�󵯳��ļ�ѡ����û��Լ�ѡ���ļ������·�����ļ���
	 * ����ļ�ѡ����ȷ����ť��ִ����Ƶ�ļ���������
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		button = (JButton) arg0.getSource();
		//�ļ�ѡ������ʼ·��Ϊ��Ŀ����·��
		//�����������ɱ�����һ��ѡ��·����
		//����û������
		JFileChooser chooser = new JFileChooser(new File("./"));
		//Ĭ��ѡ��mp3��׺���ļ�
		FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3�ļ�","mp3");
		chooser.setFileFilter(filter);
		int returnValue = chooser.showSaveDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION) {
			button.setEnabled(false);
			button.setText("��Ƶ�����С���");
			fileName = chooser.getSelectedFile().getAbsolutePath();
			//���û��.mp3��׺�����Զ�����mp3��׺
			if(!fileName.endsWith(".mp3")&&!fileName.endsWith(".MP3")) {
				fileName = fileName+".mp3";
			}
			MainButtonListener listener = this;
			try {
				new Thread() {
					public void run() {
						try {
							listener.createAudio(fileName);
							JOptionPane.showMessageDialog(null, "·��Ϊ:\r\n"+fileName,"��Ƶ���ɳɹ�",JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "ԭ��:\r\n"+e.getMessage(),"��Ƶ����ʧ��",JOptionPane.ERROR_MESSAGE);
						}
						button.setEnabled(true);
						button.setText("������Ƶ");
					}
				}.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createAudio(String fileName) throws Exception{
		//��ȡ�����ַ���
		String wordStr = InputPanel.area.getText();
		//���������ַ����õ������б�
		List<String> words = getWords(wordStr);
		//���ݵ����б����ص�����Ƶ
		String[] urls = getUrls(words);
		//�����������ţ������urls�����˳��
		if(Main.param.isRandom()) {
			urls = ArrayRandom.getArray(urls);
		}
		String[] files = URL2PM3.download(urls,new MyCallBack(button,"�����У�"));
		//��mpeg-2 layout 3��ʽ����Ƶ�ļ�תΪpcm��ʽ��
		files = MP32PCM.resample(files,new MyCallBack(button,"�ز�����"));
		files = MP32PCM.toPCM(files,new MyCallBack(button,"תPCM��"));
		//���ɿհ׵���Ƶ����
		byte[] interval = Silence.pcm(Main.param.getInterval());
		byte[] pause = Silence.pcm(Main.param.getPause());
		//��������Ƶ����ƴ������
		button.setText("���ںϲ���Ƶ����");
		PCM.merge(files, Main.param.getLoop(), pause,interval);
		//���ϲ���PCM�ļ�ѹ����MP3�ļ�
		button.setText("��������MP3����");
		PCM2MP3.toMP3(fileName);
	}
	
	/**
	 * ʹ��CsvReader����csv��ʽ�ַ���
	 * �õ������б�
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
	 * ���ݵ��ʺ�ϵͳ����ƴ�ӳ���Ƶurl
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