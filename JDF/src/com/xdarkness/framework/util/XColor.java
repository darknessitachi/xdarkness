package com.xdarkness.framework.util;

import java.awt.Color;
import java.util.Random;

/**
 * @author Darkness create on 2010-11-30 下午05:09:03
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class XColor {

	public static Random random = new Random();
	
	/**
	 * 获取随机颜色
	 * @return
	 */
	public static Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
}
