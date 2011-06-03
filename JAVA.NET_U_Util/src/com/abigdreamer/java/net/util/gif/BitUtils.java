package com.abigdreamer.java.net.util.gif;

import java.io.IOException;
import java.io.OutputStream;

class BitUtils {
	public static byte BitsNeeded(int n) {
		byte ret = 1;

		if (n-- == 0) {
			return 0;
		}
		do {
			ret = (byte) (ret + 1);
			n = n >>= 1;
		} while (n != 0);

		return ret;
	}

	public static void WriteWord(OutputStream output, short w)
			throws IOException {
		output.write(w & 0xFF);
		output.write(w >> 8 & 0xFF);
	}

	static void WriteString(OutputStream output, String string)
			throws IOException {
		for (int loop = 0; loop < string.length(); loop++)
			output.write((byte) string.charAt(loop));
	}
}

/*
 * com.xdarkness.framework.utility.gif.BitUtils JD-Core Version: 0.6.0
 */