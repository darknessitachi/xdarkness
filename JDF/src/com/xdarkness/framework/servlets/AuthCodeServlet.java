package com.xdarkness.framework.servlets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xdarkness.framework.User;

public class AuthCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 20060808L;
	private static final String CONTENT_TYPE = "image/jpeg";
	private static final int DEFAULT_WIDTH = 50;
	private static final int DEFAULT_HEIGHT = 14;
	private static final int DEFAULT_LENGTH = 4;
	public static final String DEFAULT_CODETYPE = "2";
	private static String CodeType;
	private static String AuthKey;
	private static int Width;
	private static int Height;
	private static int Length;
	private static OutputStream out;
	private static Random rand = new Random(System.currentTimeMillis());
	private static String seed;
	private static BufferedImage image;
	private static Object mutex = new Object();

	static char[] arr = "23456789qwertyuipasdfghjkzxcvbnm".toCharArray();

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		generate(request, response);
	}

	public static void generate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		synchronized (mutex) {
			try {
				CodeType = request.getParameter("CodeType");
				AuthKey = request.getParameter("AuthKEY");
				String tWidth = request.getParameter("Width");
				String tHeight = request.getParameter("Height");
				String tLength = request.getParameter("Length");
				if ((CodeType == null) || (CodeType.equals(""))) {
					CodeType = "2";
				}
				if ((AuthKey == null) || (AuthKey.equals(""))) {
					AuthKey = "_SKY_AUTHKEY";
				}
				if ((tWidth == null) || (tWidth.equals(""))) {
					Width = 50;
				}
				if ((tHeight == null) || (tHeight.equals(""))) {
					Height = 14;
				}
				if ((tLength == null) || (tLength.equals("")))
					Length = 4;
				try {
					Width = Integer.parseInt(tWidth);
				} catch (Exception ex) {
					Width = 50;
				}
				try {
					Height = Integer.parseInt(tHeight);
				} catch (Exception ex) {
					Height = 14;
				}
				try {
					Length = Integer.parseInt(tLength);
				} catch (Exception ex) {
					Length = 4;
				}
				response.setContentType("image/jpeg");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0L);

				out = response.getOutputStream();
				seed = getSeed();
				Object o = request.getSession().getAttribute("_SKY_USER");
				if (o != null) {
					User.setCurrent((User.UserData) o);
					User.setValue(AuthKey, seed);
				} else {
					User.UserData u = new User.UserData();
					User.setCurrent(u);
					User.setValue(AuthKey, seed);
					request.getSession().setAttribute("_SKY_USER", u);
				}

				if (CodeType.equals("1"))
					code1(request, response);
				else if (CodeType.equals("2"))
					code2(request, response);
				else if (CodeType.equals("3"))
					code3(request, response);
				try {
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					JPEGEncodeParam param = encoder
							.getDefaultJPEGEncodeParam(image);
					param.setQuality(1.0F, false);
					encoder.setJPEGEncodeParam(param);
					encoder.encode(image);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static BufferedImage code1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		image = new BufferedImage(Width, Height, 1);
		Graphics g = image.getGraphics();
		g.setColor(new Color(245, 245, 245));
		g.fillRect(0, 0, Width, Height);
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Arial", 0, 12));

		g.drawString(seed, 3, 11);
		g.dispose();
		return image;
	}

	private static BufferedImage code2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		image = new BufferedImage(Width, Height, 1);
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, Width, Height);
		g.setFont(new Font("Arial", 0,
				new Double(Height * 1.0D / 14.0D * 12.0D).intValue()));

		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 80; i++)
			;
		for (int i = 0; i < Length; i++) {
			String c = seed.substring(i, i + 1);
			g.setColor(new Color(20 + rand.nextInt(110),
					20 + rand.nextInt(110), 20 + rand.nextInt(110)));

			g.drawString(c, new Double(Width * 1.0D / 50.0D * 11.0D).intValue()
					* i + new Double(Width * 1.0D / 50.0D * 3.0D).intValue(),
					new Double(Height * 1.0D / 14.0D * 11.0D).intValue());
		}
		g.dispose();
		return image;
	}

	private static BufferedImage code3(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     image = new BufferedImage(Width, Height, 1);
     Graphics2D g = (Graphics2D)image.getGraphics();
     g.setColor(getRandColor(200, 250));
     g.fillRect(0, 0, Width, Height);
     g.setFont(new Font("Times New Roman", 0, 12));
 
     g.setColor(getRandColor(160, 200));
     for (int i = 0; i < 155; i++) {
       int x = rand.nextInt(Width);
       int y = rand.nextInt(Height);
       int xl = rand.nextInt(12);
       int yl = rand.nextInt(12);
       g.drawLine(x, y, x + xl, y + yl);
     }
 
     AffineTransform fontAT = new AffineTransform();
     for (int i = 0; i < Length; i++) {
       String c = seed.substring(i, i + 1);
       g.setColor(new Color(20 + rand.nextInt(110), 20 + rand.nextInt(110), 20 + rand.nextInt(110)));
       fontAT.shear(rand.nextFloat() * 0.6D - 0.3D, 0.0D);
       FontRenderContext frc = g.getFontRenderContext();
       Font theDerivedFont = g.getFont().deriveFont(fontAT);
       TextLayout tstring2 = new TextLayout(c, theDerivedFont, frc);
       tstring2.draw(g, 7 * i + 2, 11.0F);
     }
     g.dispose();
     return image;
   }

	private static String getSeed() {
		StringBuffer sb = new StringBuffer(Length);
		for (int i = 0; i < Length; i++) {
			sb.append(arr[rand.nextInt(arr.length)]);
		}

		return sb.toString();
	}

	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + rand.nextInt(bc - fc);
		int g = fc + rand.nextInt(bc - fc);
		int b = fc + rand.nextInt(bc - fc);

		return new Color(r, g, b);
	}
}

/*
 * com.xdarkness.framework.servlets.AuthCodeServlet JD-Core Version: 0.6.0
 */