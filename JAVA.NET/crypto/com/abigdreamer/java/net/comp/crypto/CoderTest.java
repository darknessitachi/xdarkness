package com.abigdreamer.java.net.comp.crypto;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public class CoderTest {

	@Test
	public void test() throws Exception {
		String inputStr = "简单加密";
		System.err.println("原文:\n" + inputStr);

		byte[] inputData = inputStr.getBytes();
		String code = Coder.encryptBASE64(inputData);

		System.err.println("BASE64加密后:\n" + code);

		byte[] output = Coder.decryptBASE64(code);

		String outputStr = new String(output);

		System.err.println("BASE64解密后:\n" + outputStr);

		// 验证BASE64加密解密一致性
		Assert.assertEquals(inputStr, outputStr);

		// 验证MD5对于同一内容加密是否一致
		Assert.assertArrayEquals(Coder.encryptMD5(inputData), Coder
				.encryptMD5(inputData));

		BigInteger md5 = new BigInteger(Coder.encryptMD5(inputData));
		System.err.println("MD5:\n" + md5.toString(16));
	}
}
