package com.xdarkness.swing;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

import com.abigdreamer.java.net.swing.IrregularFrame;

/**
 * 不规则窗体示例
 */
public class IrregularFormSample extends IrregularFrame {

	private static final long serialVersionUID = 1L;
	private Point origin; // 用于移动窗体

	public IrregularFormSample() {
	}

	/**
	 * 初始化不规则图片
	 */
	@Override
	public Image initIrregularImage() {
		return Toolkit.getDefaultToolkit().createImage(
				this.getClass().getClassLoader().getResource(
						"SelectColor_Button_Over.png"));
	}

	/**
	 * 窗体初始化
	 */
	@Override
	public void initialize() {

		// 初始化用于移动窗体的原点
		this.origin = new Point();

		// 由于取消了默认的窗体结构，所以我们要手动设置一下移动窗体的方法
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}

			// 窗体上单击鼠标右键关闭程序
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					System.exit(0);
			}

			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				repaint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
						- origin.y);
			}
		});
	}

	public static void main(String[] args) {
		IrregularFormSample sample = new IrregularFormSample();
		sample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sample.setVisible(true);
	}
}
