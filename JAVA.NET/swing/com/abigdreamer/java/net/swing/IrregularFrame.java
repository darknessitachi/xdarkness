package com.abigdreamer.java.net.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

/**
 * 不规则窗体
 */
public abstract class IrregularFrame extends JFrame {

	private static final long serialVersionUID = 1591148060234668913L;
	protected Image irregularImage; // 用来设定窗体不规则样式的图片
	protected float windowOpacity = 0.8f;// 窗体透明度

	public IrregularFrame() {
		irregularImage = initIrregularImage();
		
		/*
		 * 首先初始化一张图片，我们可以选择一张有透明部分的不规则图片 (当然我们要选择支持Alpha(透明)层的图片格式，如PNG)，这张
		 * 图片将被用来生成与其形状相同的不规则窗体
		 */
		MediaTracker mt = new MediaTracker(this);

		mt.addImage(irregularImage, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		_initialize();// 窗体初始化

		initialize();
	}

	/**
	 * 开放给子类的初始化操作
	 */
	public void initialize() {
	}

	/**
	 * 初始化
	 */
	private void _initialize() {
		// 设定窗体大小和图片一样大
		this.setSize(irregularImage.getWidth(null), irregularImage.getHeight(null));
		// 设定禁用窗体装饰，这样就取消了默认的窗体结构
		this.setUndecorated(true);

		// 调用AWTUtilities的setWindowShape方法设定本窗体为制定的Shape形状
		AWTUtilities.setWindowShape(this, XImage.getImageShape(irregularImage));
		// 设定窗体可见度
		AWTUtilities.setWindowOpacity(this, windowOpacity);

		this.setLocationRelativeTo(null);
	}

	/*
	 * 我们可以选择在窗体上绘制图片，是窗体完全呈现出图片的样式， 当然我们也可以根据需要不绘制它，而采取其他操作
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(irregularImage, 0, 0, null);
	}

	/**
	 * 初始化不规则图片
	 * @return
	 */
	public abstract Image initIrregularImage() ;
}
