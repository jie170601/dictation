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
	
	public OperatePanel() {
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(300,0));
		loop = new LabelComponent();
		interval = new LabelComponent();
		pause = new LabelComponent();
		loop.setValue(1);
		interval.setValue(1);
		pause.setValue(1);
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
		
		JSlider loopSlider = new JSlider(1,10,1);
		JSlider intervalSlider = new JSlider(1,10,1);
		JSlider pauseSlider = new JSlider(1,10,1);
		
		loopSlider.addChangeListener(new SliderChangeListener(loop,loopSlider,this));
		intervalSlider.addChangeListener(new SliderChangeListener(interval,intervalSlider,this));
		pauseSlider.addChangeListener(new SliderChangeListener(pause,pauseSlider,this));
		
		panel.setPreferredSize(new Dimension(0,250));
		panel.add(loop.getLabel());
		panel.add(loopSlider);
		panel.add(interval.getLabel());
		panel.add(intervalSlider);
		panel.add(pause.getLabel());
		panel.add(pauseSlider);
		panel.add(createCheckBox("随机播放"));
		panel.add(createCheckBox("美式发音"));
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
		button.setFont(button.getFont().deriveFont(18f));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(new File("./"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("MPEG-2 Layout 3 file","mp3");
				chooser.setFileFilter(filter);
				chooser.showSaveDialog(new JLabel());
			}
		});
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
		label.setFont(label.getFont().deriveFont(18f));
		return label;
	}
	
	/**
	 * 生成具有特定样式的标签
	 * @param text
	 * @return
	 */
	private JLabel createJLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(20f));
		return label;
	}
	
	private JCheckBox createCheckBox(String text) {
		JCheckBox checkBox = new JCheckBox(text);
		checkBox.setFont(checkBox.getFont().deriveFont(20f).deriveFont(Font.PLAIN));
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
		label.setFont(label.getFont().deriveFont(20f).deriveFont(Font.PLAIN));
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
		label.setValue(slider.getValue());
		panel.update();
	}
}
