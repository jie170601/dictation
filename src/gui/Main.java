package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Main extends JFrame{
	
	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		super();
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
