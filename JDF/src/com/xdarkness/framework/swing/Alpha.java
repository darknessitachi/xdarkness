package com.xdarkness.framework.swing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Darkness create on 2010-12-1 上午08:54:20
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class Alpha {

	public BufferedImage transferAlpha(Image image) {

//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ImageIcon imageIcon = new ImageIcon(image);
			BufferedImage bufferedImage = new BufferedImage(imageIcon
					.getIconWidth(), imageIcon.getIconHeight(),
					BufferedImage.TYPE_BYTE_INDEXED);//.TYPE_4BYTE_ABGR);
			Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
			g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon
					.getImageObserver());
			int alpha = 0;
			for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
					.getHeight(); j1++) {
				for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
						.getWidth(); j2++) {
					int rgb = bufferedImage.getRGB(j2, j1);

					int R = (rgb & 0xff0000) >> 16;
					int G = (rgb & 0xff00) >> 8;
					int B = (rgb & 0xff);
//					System.out.println("r:"+R+",g:"+G+",b:"+B);
//					if (((255 - R) < 30) && ((255 - G) < 30)
//							&& ((255 - B) < 30)) {
//						rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
//					}
					if (((R) < 50) && ((G) < 50)
							&& ((B) < 50)) {
						rgb = ((alpha + 1) << 24) | (rgb & 0x00000000);
					}

					bufferedImage.setRGB(j2, j1, rgb);

				}
			}

			g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
			 ImageIO.write(bufferedImage, "png", new File("d:/a.png"));

			return bufferedImage;
//			ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

//		return byteArrayOutputStream.toByteArray();
		return null;
	}
}
