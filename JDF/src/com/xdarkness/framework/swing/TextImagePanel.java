package com.xdarkness.framework.swing;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * @author Darkness 
 * create on 2010-11-30 下午01:27:59
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class TextImagePanel extends JPanel {
	private static final long serialVersionUID = -3706372434006423230L;
	private XFont frontfont;
	private XImage bgimage;

	private Font smallfont;
	private String fontname;
	private int fontsize;

	public TextImagePanel(XImage bgimage, XFont frontfont) {
		this.bgimage = bgimage;
		this.frontfont = frontfont;
	}

	protected Font getSmallFont() {
		if (smallfont == null
				|| !fontname.equals(this.frontfont.getFont().getName())
				|| fontsize != this.frontfont.getFont().getSize()) {
			smallfont = this.frontfont.getFont().deriveFont(
					(float) (this.frontfont.getFontSize() - 4));
			fontname = this.frontfont.getFont().getName();
			fontsize = this.frontfont.getFont().getSize();
		}
		return smallfont;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// 绘制panel背景,也是整个窗体的背景
		g2d.drawImage(bgimage.getImage(), 0, 0, null);

		// 前景文字颜色
		g2d.setColor(frontfont.getColor());
		// 文字抗锯齿
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// 前景文字字体,默认大小为22
		g2d.setFont(frontfont.getFont());

		draw(g2d);
		
		// 再一次绘制图片,使用40%的透明度覆盖文字,使文字有在图片中间的感官
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				0.4f));
		
		// ----------  增加下面的代码使得背景透明  ----------------- 
//		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
//		g2d.dispose(); 
//		g2d = image.createGraphics(); 
		// ----------  背景透明代码结束  ----------------- 
		
		g2d.drawImage(bgimage.getImage(), 0, 0, null);
		g2d.dispose();
	}
	
	public void draw(Graphics2D graphics2d) {
	}
	
}
