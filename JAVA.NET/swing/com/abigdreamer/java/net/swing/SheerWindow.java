package com.abigdreamer.java.net.swing;

import javax.swing.JWindow;

import com.sun.awt.AWTUtilities;

/**
 * 透明窗体
 * @author Darkness 
 * create on 2010-11-30 下午01:41:15
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class SheerWindow extends JWindow {
	private static final long serialVersionUID = 2185841785636054488L;

	{
		// 设置窗体透明
		AWTUtilities.setWindowOpaque(this, false);
	}
}
