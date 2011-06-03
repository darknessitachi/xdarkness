package com.abigdreamer.java.net.comp.crypto;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 基础加密组件
 * 
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public abstract class Coder {
	public static final String KEY_MD5 = "MD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws IOException {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();
	}
}
