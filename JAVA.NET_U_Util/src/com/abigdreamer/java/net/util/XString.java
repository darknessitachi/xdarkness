package com.abigdreamer.java.net.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.abigdreamer.java.net.Constant;

/**
 * @author Darkness create on 2010-12-1 下午04:09:38
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class XString {
	private static Pattern chinesePattern = Pattern.compile("[^一-龥]+",
			Pattern.CASE_INSENSITIVE);
	public static final byte[] BOM = { -17, -69, -65 };

	public static final char[] HexDigits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static final Pattern PTitle = Pattern.compile(
			"<title>(.+?)</title>", Pattern.CASE_INSENSITIVE);

	public static Pattern patternHtmlTag = Pattern.compile("<[^<>]+>", 32);

	public static final Pattern PLetterOrDigit = Pattern.compile("^\\w*$", Pattern.CASE_INSENSITIVE);

	public static final Pattern PLetter = Pattern.compile("^[A-Za-z]*$", Pattern.CASE_INSENSITIVE);

	public static final Pattern PDigit = Pattern.compile("^\\d*$", Pattern.CASE_INSENSITIVE);

	private static Pattern idPattern = Pattern.compile("[\\w\\s\\_\\.\\,]*",
			Pattern.CASE_INSENSITIVE);


    /**
     * 保证字符串中含有单引号时也能正确进行TranSql语句的执行
     */
    public static String sqlEncode(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("'", "''");
    }
    

    /**
     * HtmlEncode(str) 后 将回车符进行编码，再将双空格替换为HTML空格
     * @param str 待编码字符串
     * @return 编码之后的字符串
     */
     public static String HtmlEncodeBR(String str) {
         if (str == null) {
             return "";
         }
         return HtmlEncode(str).replace("\r\n", "<BR>").replace("  ", "&nbsp; ");
     }
     

     /**
      * 编码单引号、双引号、AND符号及左尖括号用于HTML显示
      * @param str 待编码字符串
      * @return 编码之后的字符串
      */
     public static String HtmlEncode(String str) {
         if (str == null) {
             return "";
         }
         return str.replace("&", "&#38;").replace("<", "&#60;").replace("'",
                 "&#39;").replace("\"", "&#34;");
     }
    
     /**
      * 将sql的where子句中的"where "去除，仅包含条件部分
      * 此操作的作用为，使where适合DataTable的FilterExpression参数规则
      */
     public static String ruleSqlWhereClause(String m_wheres) {
         String m_where = (m_wheres == null || m_wheres.length() <= 3) ? "1=1"
                 : m_wheres.toLowerCase().trim();
         if (m_where.indexOf("where ") == 0) {
             m_where = m_where.substring(5).trim();
         }
         return m_where;
     }
     
	/**
	 * 从前往后直到找到一个非空的字符串为止，如果都为空，返回空字符串
	 * 
	 * @param strings
	 * @return
	 */
	public static String or(String... strings) {
		for (String string : strings) {
			if (!isEmpty(string)) {
				return string;
			}
		}
		return "";
	}

	public static String replaceFirst(String str, String subStr, String reStr) {
		if (str == null)
			return null;
		if (subStr == null || subStr.equals("")
				|| subStr.length() > str.length() || reStr == null)
			return str;
		StringBuffer sb = new StringBuffer();
		String tmp = str;
		int index = -1;
		do {
			index = tmp.indexOf(subStr);
			if (index >= 0) {
				sb.append(tmp.substring(0, index));
				sb.append(reStr);
				tmp = tmp.substring(index + subStr.length());
			}

			sb.append(tmp);
			return sb.toString();
		} while (true);
	}

	public static byte[] md5(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src.getBytes());
			return md;
		} catch (Exception e) {
		}
		return null;
	}

	public static byte[] md5(byte[] src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src);
			return md;
		} catch (Exception e) {
		}
		return null;
	}

	public static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src.getBytes());
			return hexEncode(md);
		} catch (Exception e) {
		}
		return null;
	}

	public static String md5Base64(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return base64Encode(md5.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String md5Base64FromHex(String md5str) {
		char[] cs = md5str.toCharArray();
		byte[] bs = new byte[16];
		for (int i = 0; i < bs.length; i++) {
			char c1 = cs[(i * 2)];
			char c2 = cs[(i * 2 + 1)];
			byte m1 = 0;
			byte m2 = 0;
			for (byte k = 0; k < 16; k = (byte) (k + 1)) {
				if (HexDigits[k] == c1) {
					m1 = k;
				}
				if (HexDigits[k] == c2) {
					m2 = k;
				}
			}
			bs[i] = (byte) (m1 << 4 | 0 + m2);
		}

		String newstr = base64Encode(bs);
		return newstr;
	}

	public static String md5HexFromBase64(String base64str) {
		return hexEncode(base64Decode(base64str));
	}

	public static String hexEncode(byte[] bs) {
		return new String(new Hex().encode(bs));
	}

	public static byte[] hexDecode(String str) {
		try {
			if (str.endsWith("\n")) {
				str = str.substring(0, str.length() - 1);
			}
			char[] cs = str.toCharArray();
			return Hex.decodeHex(cs);
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String byteToBin(byte[] bs) {
		char[] cs = new char[bs.length * 9];
		for (int i = 0; i < bs.length; i++) {
			byte b = bs[i];
			int j = i * 9;
			cs[j] = ((b >>> 7 & 0x1) == 1 ? 49 : '0');
			cs[(j + 1)] = ((b >>> 6 & 0x1) == 1 ? 49 : '0');
			cs[(j + 2)] = ((b >>> 5 & 0x1) == 1 ? 49 : '0');
			cs[(j + 3)] = ((b >>> 4 & 0x1) == 1 ? 49 : '0');
			cs[(j + 4)] = ((b >>> 3 & 0x1) == 1 ? 49 : '0');
			cs[(j + 5)] = ((b >>> 2 & 0x1) == 1 ? 49 : '0');
			cs[(j + 6)] = ((b >>> 1 & 0x1) == 1 ? 49 : '0');
			cs[(j + 7)] = ((b & 0x1) == 1 ? 49 : '0');
			cs[(j + 8)] = ',';
		}
		return new String(cs);
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
			resultSb.append(" ");
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return (HexDigits[d1] + HexDigits[d2]) + "";
	}

	public static boolean isUTF8(byte[] bs) {
		if (hexEncode(ArrayUtils.subarray(bs, 0, 3)).equals("efbbbf")) {
			return true;
		}
		int lLen = bs.length;
		for (int i = 0; i < lLen;) {
			byte b = bs[(i++)];
			if (b >= 0) {
				continue;
			}
			if ((b < -64) || (b > -3)) {
				return false;
			}
			int c = b > -32 ? 2 : b > -16 ? 3 : b > -8 ? 4 : b > -4 ? 5 : 1;
			if (i + c > lLen) {
				return false;
			}
			for (int j = 0; j < c; i++) {
				if (bs[i] >= -64)
					return false;
				j++;
			}

		}

		return true;
	}

	public static String base64Encode(byte[] b) {
		if (b == null) {
			return null;
		}
		return new BASE64Encoder().encode(b);
	}

	public static byte[] base64Decode(String s) {
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				return decoder.decodeBuffer(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String javaEncode(String txt) {
		if ((txt == null) || (txt.length() == 0)) {
			return txt;
		}
		txt = replaceEx(txt, "\\", "\\\\");
		txt = replaceEx(txt, "\r\n", "\n");
		txt = replaceEx(txt, "\r", "\\r");
		txt = replaceEx(txt, "\n", "\\n");
		txt = replaceEx(txt, "\"", "\\\"");
		txt = replaceEx(txt, "'", "\\'");
		return txt;
	}

	public static String javaDecode(String txt) {
		if ((txt == null) || (txt.length() == 0)) {
			return txt;
		}
		txt = replaceEx(txt, "\\\\", "\\");
		txt = replaceEx(txt, "\\n", "\n");
		txt = replaceEx(txt, "\\r", "\r");
		txt = replaceEx(txt, "\\\"", "\"");
		txt = replaceEx(txt, "\\'", "'");
		return txt;
	}

	public static String[] splitEx(String str, String spilter) {
		if (str == null) {
			return null;
		}
		if ((spilter == null) || (spilter.equals(""))
				|| (str.length() < spilter.length())) {
			String[] t = { str };
			return t;
		}
		ArrayList al = new ArrayList();
		char[] cs = str.toCharArray();
		char[] ss = spilter.toCharArray();
		int length = spilter.length();
		int lastIndex = 0;
		for (int i = 0; i <= str.length() - length;) {
			boolean notSuit = false;
			for (int j = 0; j < length; j++) {
				if (cs[(i + j)] != ss[j]) {
					notSuit = true;
					break;
				}
			}
			if (!notSuit) {
				al.add(str.substring(lastIndex, i));
				i += length;
				lastIndex = i;
			} else {
				i++;
			}
		}
		if (lastIndex <= str.length()) {
			al.add(str.substring(lastIndex, str.length()));
		}
		String[] t = new String[al.size()];
		for (int i = 0; i < al.size(); i++) {
			t[i] = ((String) al.get(i));
		}
		return t;
	}

	public static String replaceEx(String str, String subStr, String reStr) {
		if (str == null) {
			return null;
		}
		if ((subStr == null) || (subStr.equals(""))
				|| (subStr.length() > str.length()) || (reStr == null)) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (true) {
			int index = str.indexOf(subStr, lastIndex);
			if (index < 0) {
				break;
			}
			sb.append(str.substring(lastIndex, index));
			sb.append(reStr);

			lastIndex = index + subStr.length();
		}
		sb.append(str.substring(lastIndex));
		return sb.toString();
	}

	public static String replaceAllIgnoreCase(String source, String oldstring,
			String newstring) {
		Pattern p = Pattern.compile(oldstring, 34);
		Matcher m = p.matcher(source);
		return m.replaceAll(newstring);
	}

	public static String urlEncode(String str) {
		return urlEncode(str, Constant.GlobalCharset);
	}

	public static String urlDecode(String str) {
		return urlDecode(str, Constant.GlobalCharset);
	}

	public static String urlEncode(String str, String charset) {
		try {
			return new URLCodec().encode(str, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String urlDecode(String str, String charset) {
		try {
			return new URLCodec().decode(str, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String htmlEncode(String txt) {
		return StringEscapeUtils.escapeHtml(txt);
	}

	public static String htmlDecode(String txt) {
		txt = replaceEx(txt, "&#8226;", "·");
		return StringEscapeUtils.unescapeHtml(txt);
	}

	public static String quotEncode(String txt) {
		if ((txt == null) || (txt.length() == 0)) {
			return txt;
		}
		txt = replaceEx(txt, "&", "&amp;");
		txt = replaceEx(txt, "\"", "&quot;");
		return txt;
	}

	public static String quotDecode(String txt) {
		if ((txt == null) || (txt.length() == 0)) {
			return txt;
		}
		txt = replaceEx(txt, "&quot;", "\"");
		txt = replaceEx(txt, "&amp;", "&");
		return txt;
	}

	public static String escape(String src) {
		StringBuffer sb = new StringBuffer();
		sb.ensureCapacity(src.length() * 6);
		for (int i = 0; i < src.length(); i++) {
			char j = src.charAt(i);
			if ((Character.isDigit(j)) || (Character.isLowerCase(j))
					|| (Character.isUpperCase(j))) {
				sb.append(j);
			} else if (j < 'Ā') {
				sb.append("%");
				if (j < '\020') {
					sb.append("0");
				}
				sb.append(Integer.toString(j, 16));
			} else {
				sb.append("%u");
				sb.append(Integer.toString(j, 16));
			}
		}
		return sb.toString();
	}

	public static String unescape(String src) {
		StringBuffer sb = new StringBuffer();
		sb.ensureCapacity(src.length());
		int lastPos = 0;
		int pos = 0;

		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					char ch = (char) Integer.parseInt(src.substring(pos + 2,
							pos + 6), 16);
					sb.append(ch);
					lastPos = pos + 6;
				} else {
					char ch = (char) Integer.parseInt(src.substring(pos + 1,
							pos + 3), 16);
					sb.append(ch);
					lastPos = pos + 3;
				}
			} else if (pos == -1) {
				sb.append(src.substring(lastPos));
				lastPos = src.length();
			} else {
				sb.append(src.substring(lastPos, pos));
				lastPos = pos;
			}
		}

		return sb.toString();
	}

	public static String leftPad(String srcString, char c, int length) {
		if (srcString == null) {
			srcString = "";
		}
		int tLen = srcString.length();

		if (tLen >= length)
			return srcString;
		int iMax = length - tLen;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < iMax; i++) {
			sb.append(c);
		}
		sb.append(srcString);
		return sb.toString();
	}

	public static String subString(String src, int length) {
		if (src == null) {
			return null;
		}
		int i = src.length();
		if (i > length) {
			return src.substring(0, length);
		}
		return src;
	}

	public static String subStringEx(String src, int length) {
		length *= 2;
		if (src == null) {
			return null;
		}
		int k = lengthEx(src);
		if (k > length) {
			int m = 0;
			boolean unixFlag = false;
			String osname = System.getProperty("os.name").toLowerCase();
			if ((osname.indexOf("sunos") > 0)
					|| (osname.indexOf("solaris") > 0)
					|| (osname.indexOf("aix") > 0))
				unixFlag = true;
			try {
				byte[] b = src.getBytes("Unicode");
				for (int i = 2; i < b.length; i += 2) {
					byte flag = b[(i + 1)];
					if (unixFlag) {
						flag = b[i];
					}
					if (flag == 0)
						m++;
					else {
						m += 2;
					}
					if (m > length)
						return src.substring(0, (i - 2) / 2);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException("执行方法getBytes(\"Unicode\")时出错！");
			}
		}
		return src;
	}

	public static int lengthEx(String src) {
		int length = 0;
		boolean unixFlag = false;
		String osname = System.getProperty("os.name").toLowerCase();
		if ((osname.indexOf("sunos") > 0) || (osname.indexOf("solaris") > 0)
				|| (osname.indexOf("aix") > 0))
			unixFlag = true;
		try {
			byte[] b = src.getBytes("Unicode");
			for (int i = 2; i < b.length; i += 2) {
				byte flag = b[(i + 1)];
				if (unixFlag) {
					flag = b[i];
				}
				if (flag == 0)
					length++;
				else
					length += 2;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("执行方法getBytes(\"Unicode\")时出错！");
		}
		return length;
	}

	public static String rightPad(String srcString, char c, int length) {
		if (srcString == null) {
			srcString = "";
		}
		int tLen = srcString.length();

		if (tLen >= length)
			return srcString;
		int iMax = length - tLen;
		StringBuffer sb = new StringBuffer();
		sb.append(srcString);
		for (int i = 0; i < iMax; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	public static boolean verify(String value, String rule) {
		// VerifyRule vr = new VerifyRule(rule);
		// boolean flag = vr.verify(value);
		// return flag;
		return true;
	}

	public static String rightTrim(String src) {
		if (src != null) {
			char[] chars = src.toCharArray();
			for (int i = chars.length - 1; i > 0; i--) {
				if ((chars[i] != ' ') && (chars[i] != '\t')) {
					return new String(ArrayUtils.subarray(chars, 0, i + 1));
				}
			}
		}
		return src;
	}

	public static void printStringWithAnyCharset(String str) {
		Map map = Charset.availableCharsets();
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			LogUtil.info(keys[i]);
			for (int j = 0; j < keys.length; j++) {
				System.out.print("\t");
				try {
					System.out.println("From "
							+ keys[i]
							+ " To "
							+ keys[j]
							+ ":"
							+ new String(str.getBytes(keys[i].toString()),
									keys[j].toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String toSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++)
			if (c[i] == ' ') {
				c[i] = '　';
			} else {
				if (((c[i] > '@') && (c[i] < '['))
						|| ((c[i] > '`') && (c[i] < '{'))) {
					continue;
				}
				if (c[i] < '')
					c[i] = (char) (c[i] + 65248);
			}
		return new String(c);
	}

	public static String toNSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '　';
			} else if (c[i] < '')
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '　') {
				c[i] = ' ';
			} else if ((c[i] > 65280) && (c[i] < 65375))
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String getChineseFullSpell(String cnStr) {
		if ((cnStr == null) || ("".equals(cnStr.trim()))) {
			return cnStr;
		}
		return ChineseSpelling.convert(cnStr);
	}

	public static String getChineseFamilyNameSpell(String cnStr) {
		if ((cnStr == null) || ("".equals(cnStr.trim()))) {
			return cnStr;
		}
		return ChineseSpelling.convertName(cnStr);
	}

	public static String getChineseFirstAlpha(String cnStr) {
		if ((cnStr == null) || ("".equals(cnStr.trim()))) {
			return cnStr;
		}
		return ChineseSpelling.getFirstAlpha(cnStr);
	}

	public static String getHtmlTitle(File f) {
		String html = FileUtil.readText(f);
		String title = getHtmlTitle(html);
		return title;
	}

	public static String getHtmlTitle(String html) {
		Matcher m = PTitle.matcher(html);
		if (m.find()) {
			return m.group(1).trim();
		}
		return null;
	}

	public static String clearHtmlTag(String html) {
		String text = patternHtmlTag.matcher(html).replaceAll("");
		if (isEmpty(text)) {
			return "";
		}
		text = htmlDecode(html);
		return text.replaceAll("[\\s　]{2,}", " ");
	}

	public static String capitalize(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return str;
		return strLen + Character.toTitleCase(str.charAt(0)) + str.substring(1);
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static final String noNull(String string, String defaultString) {
		return isEmpty(string) ? defaultString : string;
	}

	public static final String noNull(String string) {
		return noNull(string, "");
	}

	public static String join(Object[] arr) {
		return join(arr, ",");
	}

	public static String join(Object[][] arr) {
		return join(arr, "\n", ",");
	}

	public static String join(Object[] arr, String spliter) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append(spliter);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	public static String join(Object[][] arr, String spliter1, String spliter2) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append(spliter2);
			}
			sb.append(join(arr[i], spliter2));
		}
		return sb.toString();
	}

	public static String join(List list) {
		return join(list, ",");
	}

	public static String join(List list, String spliter) {
		if (list == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				sb.append(spliter);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	public static int count(String str, String findStr) {
		int lastIndex = 0;
		int length = findStr.length();
		int count = 0;
		int start = 0;
		while ((start = str.indexOf(findStr, lastIndex)) >= 0) {
			lastIndex = start + length;
			count++;
		}
		return count;
	}

	public static boolean isLetterOrDigit(String str) {
		return PLetterOrDigit.matcher(str).find();
	}

	public static boolean isLetter(String str) {
		return PLetter.matcher(str).find();
	}

	public static boolean isDigit(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return PDigit.matcher(str).find();
	}

	public static boolean checkID(String str) {
		if (isEmpty(str)) {
			return true;
		}

		return idPattern.matcher(str).matches();
	}

	public static Mapx<String, String> splitToMapx(String str,
			String entrySpliter, String keySpliter) {
		Mapx<String, String> map = new Mapx<String, String>();
		String[] arr = splitEx(str, entrySpliter);
		for (int i = 0; i < arr.length; i++) {
			String[] arr2 = splitEx(arr[i], keySpliter);
			String key = arr2[0];
			if (isEmpty(key)) {
				continue;
			}
			key = key.trim();
			String value = null;
			if (arr2.length > 1) {
				value = arr2[1];
			}
			map.put(key, value);
		}
		return map;
	}

	public static String getURLExtName(String url) {
		if (isEmpty(url)) {
			return null;
		}
		int index1 = url.indexOf('?');
		if (index1 == -1) {
			index1 = url.length();
		}
		int index2 = url.lastIndexOf('.', index1);
		if (index2 == -1) {
			return null;
		}
		int index3 = url.indexOf('/', 8);
		if (index3 == -1) {
			return null;
		}
		String ext = url.substring(index2 + 1, index1);
		if (ext.matches("[^\\/\\\\]*")) {
			return ext;
		}
		return null;
	}

	public static String getURLFileName(String url) {
		if (isEmpty(url)) {
			return null;
		}
		int index1 = url.indexOf('?');
		if (index1 == -1) {
			index1 = url.length();
		}
		int index2 = url.lastIndexOf('/', index1);
		if ((index2 == -1) || (index2 < 8)) {
			return null;
		}
		String ext = url.substring(index2 + 1, index1);
		return ext;
	}

	public static byte[] GBKToUTF8(String chinese) {
		return GBKToUTF8(chinese, false);
	}

	public static byte[] GBKToUTF8(String chinese, boolean bomFlag) {
		return CharsetConvert.GBKToUTF8(chinese, bomFlag);
	}

	public static byte[] UTF8ToGBK(String chinese) {
		return CharsetConvert.UTF8ToGBK(chinese);
	}

	public static String clearForXML(String str) {
		char[] cs = str.toCharArray();
		char[] ncs = new char[cs.length];
		int j = 0;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] > 65533)
				continue;
			if (cs[i] < ' ')
				if (((cs[i] != '\t' ? 1 : 0) & (cs[i] != '\n' ? 1 : 0) & (cs[i] != '\r' ? 1
						: 0)) != 0) {
					continue;
				}
			ncs[(j++)] = cs[i];
		}
		ncs = ArrayUtils.subarray(ncs, 0, j);
		return new String(ncs);
	}

	/**
	 * 格式化字符串
	 * 
	 * @param str
	 * @param inParams
	 * @return
	 */
	public static String format(String str, String... inParams) {
		return new StringFormat(str, inParams).toString();
	}

	/**
	 * 根据长度截断内容
	 * 
	 * @param content
	 *            内容
	 * @param endLength
	 *            截断长度
	 * @return
	 */
	public static String getSubString(String content, int endLength) {
		if (endLength < content.length()) {
			return content.substring(0, endLength) + "...";
		}

		return content;
	}

	public static boolean containsChinese(String str) {
		return !chinesePattern.matcher(str).matches();
	}

	public static void main(String[] args) {
		System.out.println(containsChinese("幻影"));
	}

	/**
	 * @TODO 貌似实现不了， 如果str为空，改变str而不需要返回
	 * @param str
	 * @param strings
	 *            public static void setIfEmpty(String str, String... strings) {
	 *            if(isEmpty(str)){ str = or(strings); } }
	 */
	
	/**
     * 获取字符串, null转换为空字符串
     * @param src 源对象
     * @return 字符串
     */
    public static String getStr(Object src) {
        return getStr(src, -1);
    }

    public static String getTrimedStr(Object src) {
        return getTrimedStr(src, -1);
    }

    public static String getStr(Object src, String defaultValue) {
        return getStr(src, -1, defaultValue);
    }

    public static String getTrimedStr(Object src, String defaultValue) {
        return getTrimedStr(src, -1, defaultValue);
    }

    /**
     * 获取定长的字符串, null转换为空字符串
     * @param src 源对象
     * @param length 字符串长度
     * @return 字符串
     */
    public static String getStr(Object src, int length) {
        return getStr(src, 0, length);
    }

    public static String getTrimedStr(Object src, int length) {
        return getTrimedStr(src, 0, length);
    }

    public static String getStr(Object src, int length, String defaultValue) {
        return getStr(src, 0, length, defaultValue);
    }

    public static String getTrimedStr(Object src, int length, String defaultValue) {
        return getTrimedStr(src, 0, length, defaultValue);
    }

    /**
     * 从start位置开始获取定长字符串, null转换为空字符串
     * @param src 源对象
     * @param start 起始位置
     * @param length 长度
     * @return
     */
    public static String getStr(Object src, int start, int length) {
        return getStr(src, start, length, "", false);
    }

    public static String getTrimedStr(Object src, int start, int length) {
        return getStr(src, start, length, "", true);
    }

    public static String getStr(Object src, int start, int length, String defaultValue) {
        return getStr(src, start, length, defaultValue, false);
    }

    public static String getTrimedStr(Object src, int start, int length, String defaultValue) {
        return getStr(src, start, length, defaultValue, true);
    }
    
	public static String getStr(Object src, int start, int length, String defaultValue, boolean trim) {
        if (src == null) {
            return defaultValue;
        }

        String value = src.toString();

        if (value.length() > start) {
            if (length < 0 || value.length() < start + length) {
                value = value.substring(start);
            } else {
                value = value.substring(start, start + length);
            }
        } else {
            value = "";
        }

        return trim ? value.trim() : value;
    }

	/**
	 * 替换最后一个字符
	 * @param str
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replaceLast(String str, String pattern, String replace) {
		Pattern ptn = Pattern.compile(pattern);
		Matcher match = ptn.matcher(str);
		int lastIndex = 0;
		while (match.find(lastIndex)) {

			lastIndex = match.end();
		}
		
		return str.substring(0, lastIndex-1) + replace;
	}
}
