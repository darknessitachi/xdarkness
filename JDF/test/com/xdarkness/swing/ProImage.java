package com.xdarkness.swing;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.xdarkness.framework.util.XHex;

/**
 * 优化png图片的小工具 熟悉png格式的朋友都知道png有3个必须的文件块,其他的可以去除.
 * 这个工具的作用就是提取3个文件块.当然,文件头与文件尾还是单加上的.对图片的优化还是有一定作用的:
 * 
 * @author Darkness create on 2010-12-1 上午10:13:12
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class ProImage {

	/**
	 * 数据块结构 PNG文件中，每个数据块由4个部分组成，如下： 名称 字节数 说明 Length (长度) 4字节
	 * 指定数据块中数据域的长度，其长度不超过(231－1)字节 Chunk Type Code (数据块类型码) 4字节
	 * 数据块类型码由ASCII字母(A-Z和a-z)组成 Chunk Data (数据块数据) 可变长度 存储按照Chunk Type
	 * Code指定的数据 CRC (循环冗余检测) 4字节 存储用来检测是否有错误的循环冗余码
	 */
	public static final int CHUNK_Length_BYTE = 4;
	public static final int CHUNK_TPYE_CODE_BYTE = 4;
	public static final int CHUNK_CRC_BYTE = 4;

	private byte[] IHDR;
	private byte[] PLTE;
	private byte[] IDAT;
	
	private String fileUrl;

	public ProImage(String url) {
		this.fileUrl = url;
	}

	/**
	 * 读取文件的二进制数组
	 * 
	 * @param url
	 * @throws IOException
	 */
	public byte[] readImage(String url) {
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(
					new File(url)));
			int size = dis.available();
			byte[] b = new byte[size];
			for (int i = 0; i < size; i++) {
				b[i] = dis.readByte();
			}

			dis.close();
			return b;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件不存在，详细信息：" + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("读取文件发生错误：" + e.getMessage());
		}
	}

	/**
	 * 获取文件头
	 * 对于一个PNG文件来说，其文件头总是由位固定的字节来描述的：
	 * 十进制数： 137 80 78 71 13 10 26 10 
	 * 十六进制数： 89 50 4E 47 0D 0A 1A 0A 
	 */
	public byte[] getHeadData() {
		byte[] headBytes = new byte[8];
		headBytes[0] = (byte) 0x89;
		headBytes[1] = (byte) 0x50;
		headBytes[2] = (byte) 0x4E;
		headBytes[3] = (byte) 0x47;

		headBytes[4] = (byte) 0x0D;
		headBytes[5] = (byte) 0x0A;
		headBytes[6] = (byte) 0x1A;
		headBytes[7] = (byte) 0x0A;
		return headBytes;
	}

	/**
	 * IHDR标识: 49 48 44 52
	 */
	public boolean isIHDR(byte b1, byte b2, byte b3, byte b4) {
		return b1 == 0x49 && b2 == 0x48 && b3 == 0x44 && b4 == 0x52;
	}

	/**
	 * PLTE标识: 50 4C 54 45
	 */
	private boolean isPLTE(byte b1, byte b2, byte b3, byte b4) {
		return b1 == 0x50 && b2 == 0x4C && b3 == 0x54 && b4 == 0x45;
	}

	/**
	 * IDAT标识: 49 44 41 54
	 */
	private boolean isIDAT(byte b1, byte b2, byte b3, byte b4) {
		return b1 == 0x49 && b2 == 0x44 && b3 == 0x41 && b4 == 0x54;
	}

	/**
	 * IEND: 00 00 00 00 49 45 4E 44 AE 42 60 82
	 * 
	 * @param a2
	 * @param start
	 */
	private byte[] getEndData() {
		byte[] endBytes = new byte[12];
		endBytes[0] = (byte) 0x00;
		endBytes[1] = (byte) 0x00;
		endBytes[2] = (byte) 0x00;
		endBytes[3] = (byte) 0x00;
		endBytes[4] = (byte) 0x49;
		endBytes[5] = (byte) 0x45;
		endBytes[6] = (byte) 0x4E;
		endBytes[7] = (byte) 0x44;
		endBytes[8] = (byte) 0x4E;
		endBytes[9] = (byte) 0x42;
		endBytes[10] = (byte) 0x60;
		endBytes[11] = (byte) 0x82;

		return endBytes;
	}

	/**
	 * 处理必须的数据
	 * 
	 * @param b
	 * @param i
	 */
	private void dealImportData(byte[] b) {

		int size = b.length;

		for (int i = 0; i < size - 4; i++) {

			/**
			 * 当前位置为IHDR，将IHDR数据块数据加入必须的数据中
			 */
			if (isIHDR(b[i], b[i + 1], b[i + 2], b[i + 3])) {
				System.out.println("init IHDR...");
				IHDR = getData(b, i);
				i += IHDR.length;
			}

			/**
			 * 当前位置为PLTE，将PLTE数据块数据加入必须的数据中
			 */
			if (isPLTE(b[i], b[i + 1], b[i + 2], b[i + 3])) {
				System.out.println("init PLTE...");
				PLTE = getData(b, i);
				i += PLTE.length;
			}

			/**
			 * 当前位置为IDAT，将IDAT数据块数据加入必须的数据中
			 */
			if (isIDAT(b[i], b[i + 1], b[i + 2], b[i + 3])) {
				System.out.println("init IDAT...");
				IDAT = getData(b, i);
				i += IDAT.length;
			}

		}
	}

	/**
	 * 初始化当前代码块数据data
	 * 
	 * @param b
	 * @param i
	 * @param data
	 * @return 处理完后的数据索引
	 */
	private byte[] getData(byte[] b, int i) {
		int dataLength = (int) ((((b[i - 4]) & 0xff) << 24)
				| ((b[i - 3] & 0xff) << 16) | ((b[i - 2] & 0xff) << 8) | (b[i - 1] & 0xff));

		int chunkLength = CHUNK_Length_BYTE + CHUNK_TPYE_CODE_BYTE + dataLength
				+ CHUNK_CRC_BYTE;
		byte[] data = new byte[chunkLength];
		for (int c = 0; c < chunkLength; c++) {
			data[c] = b[i - 4];
			i++;
		}
		return data;
	}

	public void writeImage(String pngName) throws IOException {

		byte[] b = readImage(this.fileUrl);
		dealImportData(b);
		
		FileOutputStream fos = new FileOutputStream(pngName);

		fos.write(getHeadData());
		fos.write(this.IHDR);
		if(this.PLTE != null)
			fos.write(this.PLTE);
		if(this.IDAT != null)
			fos.write(this.IDAT);
		fos.write(getEndData());
		fos.close();
	}
	
	public byte[] getIDAT(){
		return this.IDAT;
	}
	
	public String getIHDR(){
		return XHex.format(this.IHDR);
	}
	
	public String getPLTE(){
		return XHex.format(this.PLTE);
	}
	
	public static void main(String[] args) throws IOException {
		int startTime = new Date().getMinutes();
		ProImage proImage = new ProImage("D:\\testpng\\BALL_BK2.png");
		proImage.writeImage("D:\\testpng\\2.png");
		System.out.println("total cost time:"
				+ (new Date().getMinutes() - startTime));
		
		System.out.println(0x0100);
	}


}
