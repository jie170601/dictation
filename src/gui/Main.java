package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bean.Param;
import util.Config;
import util.FileUtil;

/**
 * ��������
 * ����Ƚϼ�
 * ������һ�����������������
 * @author wss
 *
 */
public class Main extends JFrame{
	
	/**
	 * ϵͳ�õ���һЩ��������
	 */
	//ϵͳ����
	public static Param param = null;
	//�����С
	public final static float FONT_SIZE = 18f;
	
	/**
	 * �������
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		super();
		
		//����ϵͳ����
		try {
			param = Config.findParam();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "ԭ��:\r\n"+e.getMessage(),"��ȡ����ʧ��",JOptionPane.ERROR_MESSAGE);
		}
		
		//��ʼ��tmpĿ¼
		//tmpĿ¼��ŵ��ǹ�lame�������м���Ƶ�ļ�
		File file = new File("./tmp");
		try {
			FileUtil.removeDir(file);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "ԭ��:\r\n"+e.getMessage(),"��ʼ��ʧ��",JOptionPane.ERROR_MESSAGE);
		}
		
		//���ݲ�����ʼ��ϵͳ
		this.setTitle("������д�����");
		this.setSize(650,400);
		ImageIcon icon = new ImageIcon("./images/logo.png");
		this.setIconImage(icon.getImage());
		//������ʾ
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(650,400));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		this.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout(10,0));
		
		panel.add(new InputPanel());
		panel.add(new OperatePanel(),BorderLayout.EAST);
	    
		//�������ӵ������壬����������10�Ŀհ�
	    this.add(panel);
	    this.add(createBlank(10,10),BorderLayout.SOUTH);
	    this.add(createBlank(10,10),BorderLayout.NORTH);
	    this.add(createBlank(10,10),BorderLayout.WEST);
	    this.add(createBlank(10,10),BorderLayout.EAST);
	    
		this.setVisible(true);
		
		this.addWindowListener(new MainWindowListener(param));
	}
	
	/**
	 * ���ɿհ׵����
	 * ����ռλ
	 * swingû���ҵ������padding����margin�����÷���
	 * ���������������
	 * @return
	 */
	private JPanel createBlank(int width,int height) {
		JPanel panel = new JPanel();
		panel.setSize(width, height);
		panel.setPreferredSize(new Dimension(width,height));
		return panel;
	}
	
	
}

/**
 * ������״̬����
 * ��Ҫ����������ر�ʱ����ϵͳ�����������ļ�
 * @author wss
 *
 */
class MainWindowListener implements WindowListener{
	
	private Param param = null;
	
	public MainWindowListener(Param param) {
		this.param = param;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ����ر�֮ǰ��ϵͳ�������浽�����ļ�
	 */
	@Override
	public void windowClosing(WindowEvent arg0) {
		try {
			Config.saveParam(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
