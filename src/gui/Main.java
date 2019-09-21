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
 * 主窗体类
 * 程序比较简单
 * 基本这一个窗体就满足需求了
 * @author wss
 *
 */
public class Main extends JFrame{
	
	/**
	 * 系统用到的一些参数变量
	 */
	//系统设置
	public static Param param = null;
	//字体大小
	public final static float FONT_SIZE = 18f;
	
	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		super();
		
		//加载系统参数
		try {
			param = Config.findParam();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "原因:\r\n"+e.getMessage(),"获取参数失败",JOptionPane.ERROR_MESSAGE);
		}
		
		//初始化tmp目录
		//tmp目录存放的是供lame操作的中间音频文件
		File file = new File("./tmp");
		try {
			FileUtil.removeDir(file);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "原因:\r\n"+e.getMessage(),"初始化失败",JOptionPane.ERROR_MESSAGE);
		}
		
		//根据参数初始化系统
		this.setTitle("单词听写桌面版");
		this.setSize(650,400);
		ImageIcon icon = new ImageIcon("./images/logo.png");
		this.setIconImage(icon.getImage());
		//居中显示
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(650,400));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		this.setLayout(new BorderLayout());
		panel.setLayout(new BorderLayout(10,0));
		
		panel.add(new InputPanel());
		panel.add(new OperatePanel(),BorderLayout.EAST);
	    
		//将面包添加到主窗体，并在四周留10的空白
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

/**
 * 主窗体状态监听
 * 主要是在主窗体关闭时保存系统参数到参数文件
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
