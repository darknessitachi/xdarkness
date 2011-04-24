package com.xdarkness.framework.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * @author Darkness 
 * create on 2010-11-30 下午04:06:18
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class Screen {

	// 获得屏幕的大小
	public static final Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();
	public static final int HEIGHT;
	public static final int WIDTH;
	static {
		HEIGHT = screenSize.height;
		WIDTH = screenSize.width;
	}
	
	/**
	 * 获取屏幕内任意一点
	 * @return
	 */
	public static Point getRandomPoint(){
		int x = new Float(Math.round(Math.random() * (Screen.WIDTH - 100))).intValue();
		int y = new Float(Math.round(Math.random() * (Screen.HEIGHT - 100))).intValue();
		
		return new Point(x, y); 
	}
}
