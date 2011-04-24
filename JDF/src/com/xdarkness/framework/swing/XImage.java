package com.xdarkness.framework.swing;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.xdarkness.framework.Config;

/**
 * @author Darkness create on 2010-11-30 下午01:17:32
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class XImage {
	
	/**
	 * 将Image图像转换为Shape图形
	 * 
	 * @param img
	 * @param isFiltrate
	 * @return Image图像的Shape图形表示
	 * @author Hexen
	 */
	public static Shape getImageShape(Image img) {
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		int width = img.getWidth(null);// 图像宽度
		int height = img.getHeight(null);// 图像高度

		// 筛选像素
		// 首先获取图像所有的像素信息
		PixelGrabber pgr = new PixelGrabber(img, 0, 0, -1, -1, true);
		try {
			pgr.grabPixels();
		} catch (InterruptedException ex) {
			ex.getStackTrace();
		}
		int pixels[] = (int[]) pgr.getPixels();

		// 循环像素
		for (int i = 0; i < pixels.length; i++) {
			// 筛选，将不透明的像素的坐标加入到坐标ArrayList x和y中
			int alpha = getAlpha(pixels[i]);
			if (alpha == 0) {
				continue;
			} else {
				x.add(i % width > 0 ? i % width - 1 : 0);
				y
						.add(i % width == 0 ? (i == 0 ? 0 : i / width - 1) : i
								/ width);
			}
		}

		// 建立图像矩阵并初始化(0为透明,1为不透明)
		int[][] matrix = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				matrix[i][j] = 0;
			}
		}

		// 导入坐标ArrayList中的不透明坐标信息
		for (int c = 0; c < x.size(); c++) {
			matrix[y.get(c)][x.get(c)] = 1;
		}

		/*
		 * 由于Area类所表示区域可以进行合并，我们逐一水平"扫描"图像矩阵的每一行，
		 * 将不透明的像素生成为Rectangle，再将每一行的Rectangle通过Area类的rec
		 * 对象进行合并，最后形成一个完整的Shape图形
		 */
		Area rec = new Area();
		int temp = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (matrix[i][j] == 1) {
					if (temp == 0)
						temp = j;
					else if (j == width) {
						if (temp == 0) {
							Rectangle rectemp = new Rectangle(j, i, 1, 1);
							rec.add(new Area(rectemp));
						} else {
							Rectangle rectemp = new Rectangle(temp, i,
									j - temp, 1);
							rec.add(new Area(rectemp));
							temp = 0;
						}
					}
				} else {
					if (temp != 0) {
						Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
						rec.add(new Area(rectemp));
						temp = 0;
					}
				}
			}
			temp = 0;
		}
		return rec;
	}

	/**
	 * 获取像素的Alpha值
	 * 
	 * @param pixel
	 * @return 像素的Alpha值
	 */
	private static int getAlpha(int pixel) {
		return (pixel >> 24) & 0xff;
	}
	
	private String path;
	private BufferedImage image;

	public String getPath() {
		return Config.getClassPath() + path;
	}

	public void setPath(String path) {
		if (path != null && !path.trim().equals("") && !this.path.equals(path)) {
			this.path = path;
			initImage();
		}
	}

	public BufferedImage getImage() {
		if (image == null)
			initImage();
		return image;
	}
	
	public static BufferedImage getImage(String resource) {
		InputStream is = null;
		try {
			is = XImage.class.getClassLoader().getResourceAsStream(resource);
			
			if(is == null){
				is = new FileInputStream(Config.getClassPath() + resource);
			}
			if(is == null){
				is = XImage.class.getClassLoader().getResourceAsStream(Config.getClassPath() + resource);
			}
			return ImageIO.read(is);
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
		
		return null;
	}

	private void initImage() {
		InputStream is = null;
		try {
			is = XImage.class.getClassLoader().getResourceAsStream(this.getPath());
			if(is == null){
				is = new FileInputStream(this.getPath());
			}
			image = ImageIO.read(is);
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

	public XImage() {
	}

	public XImage(String path) {
		this.path = path;
	}

	public static void filterImageAlpha(String path, int backgroundRGB) {
		List<ImageRGB> imageRGBs = new ArrayList<ImageRGB>();
		try {
			ImageIcon imageIcon = new ImageIcon(path);
			BufferedImage bufferedImage = new BufferedImage(imageIcon
					.getIconWidth(), imageIcon.getIconHeight(),
					BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
			g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon
					.getImageObserver());
			// 循环每一个像素点，改变像素点的Alpha值
			int alpha = 100;
			for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
					.getHeight(); j1++) {
				for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
						.getWidth(); j2++) {
					
					int rgb = bufferedImage.getRGB(j2, j1);
					if(rgb != backgroundRGB){
						imageRGBs.add(new ImageRGB(j2, j1, rgb));
					}
					System.out.println(rgb);
					rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
					bufferedImage.setRGB(j2, j1, rgb);
				}
			}
			g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

			bufferedImage = new BufferedImage(imageIcon
					.getIconWidth(), imageIcon.getIconHeight(),
					BufferedImage.TYPE_4BYTE_ABGR);
			
			for (ImageRGB imageRGB : imageRGBs) {
				bufferedImage.setRGB(imageRGB.x, imageRGB.y, imageRGB.rgb);
			}
			
			// 生成图片为PNG
			ImageIO.write(bufferedImage, "png", new File("D:/a.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取图片rgb信息
	 * 
	 * @param os
	 */
	public static List<ImageRGB> getImageRGB(String path) {
		List<ImageRGB> imageRGBs = new ArrayList<ImageRGB>();
	
		try {
			ImageIcon imageIcon = new ImageIcon(path);
			BufferedImage bufferedImage = new BufferedImage(imageIcon
					.getIconWidth(), imageIcon.getIconHeight(),
					BufferedImage.TYPE_4BYTE_ABGR);
			
			for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
					.getHeight(); j1++) {
				for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
						.getWidth(); j2++) {
					int rgb = bufferedImage.getRGB(j2, j1);
					imageRGBs.add(new ImageRGB(j2, j1, rgb));
				}
			}

			return imageRGBs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 设置图片的每个象素Alpha，使得图片透明
	 * 
	 * @param os
	 */
	public static BufferedImage getAlphaImage(String path) {
		/**
		 * 增加测试项 读取图片，绘制成半透明
		 */
		try {
			ImageIcon imageIcon = new ImageIcon(path);
			BufferedImage bufferedImage = new BufferedImage(imageIcon
					.getIconWidth(), imageIcon.getIconHeight(),
					BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
			g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon
					.getImageObserver());
			// 循环每一个像素点，改变像素点的Alpha值
			int alpha = 100;
			for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
					.getHeight(); j1++) {
				for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
						.getWidth(); j2++) {
					int rgb = bufferedImage.getRGB(j2, j1);
					System.out.println(rgb);
					rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
					bufferedImage.setRGB(j2, j1, rgb);
				}
			}
			g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

			// 生成图片为PNG
			ImageIO.write(bufferedImage, "png", new File("D:/a.png"));
			return bufferedImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 绘制图形，其背景是黑色的，如何才能背景透明呢？
	 */
	public static void createImage(){
		createImage(1.0f);
	}
	
	/**
	 * 这样绘制的图形应该说是前景透明的，背景依然是黑色，:(
	 */
	public static void createImage(float alpha){
		int width = 400, height = 300; 
		// 创建BufferedImage对象 
		BufferedImage image = new BufferedImage(width, height,     BufferedImage.TYPE_INT_RGB); 
		// 获取Graphics2D 
		Graphics2D g2d = image.createGraphics(); 
		// 设置透明度
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha)); // 1.0f为透明度 ，值从0-1.0，依次变得不透明
		
		// ---------- 增加下面的代码使得背景透明 -----------------
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		// ---------- 背景透明代码结束 -----------------
		
		// 画图 
		g2d.setColor(new Color(255,0,0)); 
		g2d.setStroke(new BasicStroke(1)); 
		g2d.drawString("Darkness", 20, 30);
		
		//透明度设置 结束
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		//释放对象 
		g2d.dispose(); 
		// 保存文件    
		try {
			ImageIO.write(image, "png", new File("d:/test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	
	public static void main(String[] args) {
//		XImage.createImage();
//		URL resource = ImageTest.class.getResource("images/BALL_BK.png");  
        String src = "D:\\QQ\\MultClrBubbleScreenSaver\\bin\\images\\BALL_BK.png"; 
//		XImage.getAlphaImage(src);
        XImage.filterImageAlpha(src, -16777216);
	}
}

class ImageRGB {
	int x;
	int y;
	int rgb;
	public ImageRGB(int x, int y, int rgb) {
		this.x = x;
		this.y = y;
		this.rgb = rgb;
	}
}
