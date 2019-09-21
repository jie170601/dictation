package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 操作按钮面板
 * 放置在主面板的右边
 * 高度可变，宽度固定
 * @author jie
 *
 */
public class OperatePanel extends JPanel{
	
	private LabelComponent loop;
	private LabelComponent interval;
	private LabelComponent pause;
	
	public JSlider loopSlider;
	public JSlider intervalSlider;
	public JSlider pauseSlider;
	
	public JCheckBox randomBox;
	public JCheckBox americanBox;
	
	public OperatePanel() {
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(300,0));
		loop = new LabelComponent();
		interval = new LabelComponent();
		pause = new LabelComponent();
		loop.setValue(Main.param.getLoop());
		interval.setValue(Main.param.getInterval());
		pause.setValue(Main.param.getPause());
		update();
		add(createParam(),BorderLayout.NORTH);
		add(createConfirm(),BorderLayout.SOUTH);
	}
	
	/**
	 * 生成参数设置面板
	 * @return
	 */
	private JPanel createParam() {
		JPanel panel = new JPanel(new GridLayout(8,1));
		
		loopSlider = new JSlider(1,10,loop.getValue());
		intervalSlider = new JSlider(0,10,interval.getValue());
		pauseSlider = new JSlider(0,10,pause.getValue());
		
		randomBox = createCheckBox("随机播放",Main.param.isRandom());
		americanBox = createCheckBox("美式发音",Main.param.isAmerican());
		
		loopSlider.addChangeListener(new SliderChangeListener(loop,loopSlider,this));
		intervalSlider.addChangeListener(new SliderChangeListener(interval,intervalSlider,this));
		pauseSlider.addChangeListener(new SliderChangeListener(pause,pauseSlider,this));
		
		randomBox.addActionListener(new CheckBoxListener(this));
		americanBox.addActionListener(new CheckBoxListener(this));
		
		panel.setPreferredSize(new Dimension(0,250));
		panel.add(loop.getLabel());
		panel.add(loopSlider);
		panel.add(pause.getLabel());
		panel.add(pauseSlider);
		panel.add(interval.getLabel());
		panel.add(intervalSlider);
		panel.add(randomBox);
		panel.add(americanBox);
		return panel;
	}
	
	/**
	 * 生成确认按钮
	 * @return
	 */
	private JPanel createConfirm() {
		JPanel panel = new JPanel(new BorderLayout());
		JButton button = new JButton("生成音频");
		panel.setPreferredSize(new Dimension(0,55));
		button.setFont(button.getFont().deriveFont(Main.FONT_SIZE));
		button.addActionListener(new MainButtonListener());
		panel.add(button);
		return panel;
	}
	
	/**
	 * 生成具有特定样式的标签
	 * @param text
	 * @return
	 */
	private JLabel createJLabel() {
		JLabel label = new JLabel();
		label.setFont(label.getFont().deriveFont(Main.FONT_SIZE));
		return label;
	}
	
	/**
	 * 生成具有特定样式的标签
	 * @param text
	 * @return
	 */
	private JLabel createJLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(Main.FONT_SIZE));
		return label;
	}
	
	private JCheckBox createCheckBox(String text,boolean selected) {
		JCheckBox checkBox = new JCheckBox(text,selected);
		checkBox.setFont(checkBox.getFont().deriveFont(Main.FONT_SIZE).deriveFont(Font.PLAIN));
		return checkBox;
	}
	
	public void update() {
		loop.setText("重复 "+loop.getValue()+"（次）：");
		pause.setText("间隔 "+pause.getValue()+"（秒）：");
		interval.setText("停顿 "+interval.getValue()+"（秒）：");
	}
}

/**
 * 设置的label
 * 由值和JLabel对象组成
 * 避免直接用JLabel带来的字符串和数值之间来回转的问题
 * @author jie
 *
 */
class LabelComponent{
	private int value = 0;
	private JLabel label = new JLabel();
	
	public LabelComponent() {
		label.setFont(label.getFont().deriveFont(Main.FONT_SIZE).deriveFont(Font.PLAIN));
	}
	public void setText(String text) {
		label.setText(text);
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public JLabel getLabel() {
		return label;
	}
}

/**
 * 滑动条改变事件处理
 * @author jie
 *
 */
class SliderChangeListener implements ChangeListener{
	
	private LabelComponent label;
	private JSlider slider;
	private OperatePanel panel;
	
	public SliderChangeListener(LabelComponent label,JSlider slider,OperatePanel panel) {
		this.label = label;
		this.slider = slider;
		this.panel = panel;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		byte value = (byte)slider.getValue();
		label.setValue(value);
		panel.update();
		if(source==panel.loopSlider) {
			Main.param.setLoop(value);
		}
		if(source==panel.intervalSlider) {
			Main.param.setInterval(value);
		}
		if(source==panel.pauseSlider) {
			Main.param.setPause(value);
		}
	}
}

/**
 * checkbox改变事件处理
 * @author 邓集阶
 *
 */
class CheckBoxListener implements ActionListener{
	
	private OperatePanel panel;
	
	public CheckBoxListener(OperatePanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==panel.randomBox) {
			Main.param.setRandom(panel.randomBox.isSelected());
		}
		if(source==panel.americanBox) {
			Main.param.setAmerican(panel.americanBox.isSelected());
		}
	}
	
}
