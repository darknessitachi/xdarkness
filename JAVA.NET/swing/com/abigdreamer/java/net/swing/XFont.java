package com.abigdreamer.java.net.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.abigdreamer.java.net.Config;

/**
 * @author Darkness 
 * create on 2010-11-30 上午11:50:37
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class XFont {
	public static final String MINIDY = "fonts/minidy.ttf";
	private String path = MINIDY;
	private String color = "0x0099FF";
	private Color thecolor = Color.decode(color);
	private Font frontfont;
	private int size = 22;

	/**
	 * 获取颜色
	 * @return
	 */
	public Color getColor() {
		return thecolor;
	}

	/**
	 * 获取颜色——十六进制字符串
	 * @return
	 */
	public String getColorHexString() {
		return color;
	}

	/**
	 * 设置颜色——十六进制字符串
	 * @param color
	 */
	public void setColor(String color) {
		if (color != null && !color.trim().equals("")) {
			try {
				this.color = color;
				thecolor = Color.decode(this.color);
			} catch (java.lang.NumberFormatException e) {
				this.color = "0x0099FF";
				thecolor = Color.decode(this.color);
			}
		}
	}

	/**
	 * 获取字体路径
	 * @return
	 */
	public String getPath() {
		return Config.getJDFClassPath() + path;
	}

	/**
	 * 设置字体路径
	 * @param path
	 */
	public void setPath(String path) {
		if (path != null && !path.trim().equals("") && !path.equals(this.path)) {
			this.path = path;
			initFontPath();
		}
	}

	/**
	 * 设置字体大小
	 * @param size
	 */
	public void setFontSize(int size) {
		if (size > 0 && size != this.size) {
			this.size = size;
			initFontSize();
		}
	}

	/**
	 * 获取字体大小
	 * @return
	 */
	public int getFontSize() {
		return size;
	}

	/**
	 * 初始化字体
	 */
	private void initFontPath() {
		InputStream is = null;
		try {
			is = XFont.class.getClassLoader().getResourceAsStream(this.path);
			if(is == null) {
				is = new FileInputStream(getPath());
			}
			if(is == null) {
				throw new RuntimeException("没有找到该的字体【"+this.path+"】！");
			}
			frontfont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(
					(float) size);
		} catch (FontFormatException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 初始化字体
	 */
	private void initFontSize() {
		if (frontfont != null) {
			if (size != frontfont.getSize()) {
				frontfont = frontfont.deriveFont((float) size);
			}
		} else {
			initFontPath();
		}
	}

	/**
	 * 获取字体
	 * @return
	 */
	public Font getFont() {
		if (frontfont == null) {
			initFontPath();
		}
		return frontfont;
	}

	private static XFont singleton = null;

	private XFont() {
	}

	/**
	 * 获取字体实例
	 * @return
	 */
	public static XFont getInstance() {
		if (singleton == null) {
			synchronized (XFont.class) {
				if (singleton == null) {
					singleton = new XFont();
				}
			}
		}
		return singleton;
	}
	
	public static void main(String[] args) {
		System.out.println(XFont.getInstance().getFont());
	}
}

