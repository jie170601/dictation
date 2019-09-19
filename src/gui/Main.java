package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import bean.Param;
import lame.MP32PCM;
import lame.PCM;
import util.Config;

public class Main extends JFrame{
	
	public static Param param = null;
	
	/**
	 * �������
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		super();
		
		try {
			param = Config.findParam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//����ϵͳ����
		//���ݲ�����ʼ��ϵͳ
		
		this.setTitle("������д�����");
		Dimension size = getToolkit().getScreenSize();
		this.setSize(650,400);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(500,400));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		this.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout(10,0));
		
		panel.add(new InputPanel());
		panel.add(new OperatePanel(),BorderLayout.EAST);
	    
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
