package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 单词输入框面板
 * 放置在主窗口的右边
 * 用来提示软件的使用方法
 * 和输入单词字符串
 * @author jie
 *
 */
public class InputPanel extends JPanel{
	
	public static JTextArea area;
	
	/**
	 * 使用边界布局，上面是固定高度的操作提示框
	 * 下面是可变高度的单词输入框
	 * 宽度都是可变的
	 */
	public InputPanel() {
		super(new BorderLayout());
		add(createMsgPanel(),BorderLayout.NORTH);
		add(createWordsPanel());
	}
	
	/**
	 * 生成使用说明面板
	 * 使用说明面板使用网格布局
	 * 每个步骤占用一行
	 * @return
	 */
	private JPanel createMsgPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setPreferredSize(new Dimension(0,130));
		panel.add(createLine("1.单词之间用英文输入法（半角）逗号【,】隔开"));
		panel.add(createLine("2.单词文档生成可以用Excel保存为csv格式"));
		panel.add(createLine("3.纸质单词本可以拍照通过OCR软件生成文档"));
		return panel;
	}
	
	/**
	 * 生成单词输入面板
	 * 单词输入框宽高都可变以适应屏幕
	 * 输入框居中，四周留出空白
	 * 所以也用边界布局
	 * @return
	 */
	private JPanel createWordsPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createArea());
		return panel;
	}
	
	/**
	 * 生成一行说明文字
	 * @param text
	 * @return
	 */
	private JLabel createLine(String text) {
		JLabel label = new JLabel(text);
		Font font = label.getFont();
		label.setFont(label.getFont().deriveFont(18f).deriveFont(Font.PLAIN));
		return label;
	}
	
	/**
	 * 生成可滚动的文本框
	 * @return
	 */
	private JScrollPane createArea() {
		area = new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(area.getFont().deriveFont(18f));
		area.setMargin(new Insets(10, 10, 10, 10));
		return new JScrollPane(area);
	}
}
