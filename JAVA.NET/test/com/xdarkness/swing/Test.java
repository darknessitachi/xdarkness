package com.xdarkness.swing;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import com.abigdreamer.java.net.swing.Alpha;


/**
 * @author Darkness 
 * create on 2010-11-30 下午12:47:18
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class Test {

	public Test(BufferedImage... images){
		JTabbedPane tabbedPane = new JTabbedPane();
		
		for (BufferedImage bufferedImage : images) {
	        tabbedPane.add("Affine Transform", new JLabel(new ImageIcon(bufferedImage)));  
		}
  
        JFrame jframe = new JFrame();  
        jframe.setSize(800, 600);  
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        jframe.getContentPane().add(tabbedPane);  
        jframe.setVisible(true);  
	}
	
	public static void main(String[] args) throws IOException {  
        URL resource = Test.class.getResource("/images/BALL_BK2.png");  
        BufferedImage sourceImage = ImageIO.read(resource);  
        BufferedImage dstImage = null;  
        AffineTransform transform = AffineTransform.getScaleInstance(0.5, 0.5);// 返回表示缩放变换的变换  
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);  
        dstImage = op.filter(sourceImage, null);  
   
        /********** save到本地 *****************/  
        try {  
            ImageIO.write(dstImage, "png", new File("D:\\bubble2.png"));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        /********** save end *****************/  
  
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(sourceImage, "png", byteArrayOutputStream);

		byte[] bytes = byteArrayOutputStream.toByteArray();
		for (byte b : bytes) {
			System.out.println(b);
		}
        new Test(sourceImage, dstImage,new Alpha().transferAlpha(sourceImage));
    }  

}
