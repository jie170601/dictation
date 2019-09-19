package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bean.Param;
import lame.MP32PCM;
import lame.PCM;
import util.Config;

public class Main extends JFrame{
	
	public static Param param = null;
	
	/**
	 * 程序入口
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
		
		//加载系统参数
		//根据参数初始化系统
		
		this.setTitle("单词听写桌面版");
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
	 * 生成空白的面板
	 * 用来占位
	 * swing没有找到方便的padding或者margin的设置方法
	 * 就用这个来代替了
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
	 * 程序关闭之前将系统参数保存到配置文件
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
