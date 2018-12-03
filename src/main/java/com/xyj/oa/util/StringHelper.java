package com.xyj.oa.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	private static String s_Charset = "utf-8";

	private static final Pattern Keyword_Separator = Pattern.compile("[;；|]+");

	public static String[] KeywordSeparator(String keyword) {
		String[] keys = Keyword_Separator.split(keyword);
		Set<String> result = new HashSet<String>();
		for (String key : keys) {
			String k = key.trim();
			if (k.length() > 0)
				result.add(k);
		}
		return result.toArray(new String[result.size()]);
	}

	public static boolean isEmpty(String string) {
		return string == null || string.trim().equals("") || string.trim().equals("\"null\"") || string.equals("null");
	}

	public static boolean isNullEmpty(String string) {
		return string == null || string.trim().equals("\"null\"") || string.equals("null");
	}

	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	public static boolean stringEquals(String s1, String s2) {
		if (s1 == null) {
			s1 = "";
		}
		if (s2 == null) {
			s2 = "";
		}
		return s1.equals(s2);
	}

	public static String formatName(String firstname, String surname) {
		if (surname != null && surname.length() > 0 && surname.charAt(0) > 127) // 姓为中文
			return surname + (StringHelper.isEmpty(firstname) ? "" : (firstname));
		else
			// 姓为英文
			return (StringHelper.isEmpty(firstname) ? "" : (firstname + " ")) + surname;
	}

	/**
	 * 清除空白字符
	 * 包括 空格、回车、换行符、制表符
	 * @param string
	 * @return
	 */
	public static String removeBlank(String string) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(string);
		return m.replaceAll("");
	}

	public static String join(Object[] objs, String separator) {
		if (objs == null || objs.length == 0 || separator == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			if (obj == null)
				continue;
			sb.append(separator).append(obj.toString());
		}
		return sb.toString().substring(separator.length());
	}

	public static String joinRow(Object[] objs, String separator) {
		if (objs == null || objs.length == 0 || separator == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			sb.append(separator).append(obj != null ? obj.toString() : "");
		}
		return sb.toString().substring(separator.length());
	}

	@SuppressWarnings("rawtypes")
	public static String join(Collection objs, String separator) {
		if (objs == null || objs.size() == 0 || separator == null)
			return null;
		StringBuilder sb = new StringBuilder(256);
		for (Object obj : objs) {
			if (obj == null)
				continue;
			sb.append(separator).append(obj.toString());
		}
		return sb.toString().substring(separator.length());
	}

	@SuppressWarnings("rawtypes")
	public static String joinWithNull(Collection objs, String separator) {
		if (objs == null || objs.size() == 0 || separator == null)
			return null;
		StringBuilder sb = new StringBuilder(256);
		for (Object obj : objs) {
			if (obj == null)
				sb.append(separator).append(obj);
			else
				sb.append(separator).append(obj.toString());
		}
		return sb.toString().substring(separator.length());
	}

	public static String joinIds(Collection<Long> ids, String separator) {
		if (ids == null || ids.size() == 0 || separator == null)
			return null;
		Iterator<Long> itr = ids.iterator();
		Long first = itr.next();
		StringBuilder sb = new StringBuilder(ids.size() * (first.toString().length() + 1));
		sb.append(first);
		while (itr.hasNext())
			sb.append(separator).append(itr.next());
		return sb.toString();
	}

	public static String joinIds(Long[] ids, String separator) {
		if (ids == null || ids.length == 0 || separator == null)
			return null;
		StringBuilder sb = new StringBuilder(ids.length * (ids[0].toString().length() + 1));
		sb.append(ids[0]);
		for (int i = 1; i < ids.length; i++)
			sb.append(separator).append(ids[i]);
		return sb.toString();
	}

	/**
	 * 数字转换
	 * @param id  数字串
	 * @param radix 该数字串的进制
	 * @param digi  需要转换后的位数
	 * @return
	 */
	public static String smardIdConvert(String id, int radix, int digi) {
		if (StringHelper.isEmpty(id))
			return null;
		long i = 0;
		if (radix == 16) {
			if (digi == 10)
				id += "000000";
			BigInteger bigInt = new BigInteger(id, 16);
			i = bigInt.longValue();
		} else
			i = Long.parseLong(id, radix);
		if (radix == 10) {
			String hex = Long.toHexString(Long.reverseBytes(i));
			if (hex.length() < 16)
				hex = buString(hex, 16);
			return hex.substring(0, digi);
		} else if (radix == 16) {
			return buString(String.valueOf(Long.reverseBytes(i)), digi);
		}
		return null;
	}

	public static String buString(String str, Integer lenght) {
		StringBuilder s = new StringBuilder(str);
		while (s.length() < lenght) {
			s.insert(0, "0");
		}
		return s.toString();
	}

	public static String listToString(List<Long> permission) {
		StringBuilder builder = new StringBuilder();
		builder.append(" ( ");
		for (int i = 0; i < permission.size(); i++) {
			if (permission.size() == 1) {
				return builder.append(permission.get(0)).toString();
			}
			builder.append(permission.get(i)).append(",");
		}
		builder.append(" ) ");
		String str = builder.deleteCharAt(builder.length() - 4).toString();
		return str;
	}

	public static void stringToByteArray(byte[] pByteArray, String pStringToConvert) throws Exception {
		if (pByteArray == null || pStringToConvert == null)
			return;
		System.arraycopy(pStringToConvert.getBytes(s_Charset), 0, pByteArray, 0, pStringToConvert.getBytes(s_Charset).length);
	}

	public static String stradd(String str1, String str2) {
		StringBuilder hql = new StringBuilder();
		if (isNotEmpty(str1)) {
			if (isNotEmpty(str2))
				hql.append(str1).append(",").append(str2);
			else
				hql.append(str1);
		} else {
			if (isNotEmpty(str2))
				hql.append(str2);
			else
				return "";
		}
		return hql.toString();
	}

	public static String stradd2(String str1, String str2) {
		StringBuilder hql = new StringBuilder();
		if (isNotEmpty(str1)) {
			if (isNotEmpty(str2)) {
				hql.append(str1).append(";").append(str2);
			} else {
				hql.append(str1);
			}
		} else {
			if (isNotEmpty(str2)) {
				hql.append(str2);
			} else {
				return "";
			}
		}
		return hql.toString();
	}

	public static List<String> getStringByPattern(String str, String p1, String p2) {
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile("[^" + p1 + "]+(?=" + p2 + ")");
		Matcher m = pattern.matcher(str);
		while (m.find())
			list.add(m.group());
		return list;
	}

	public static String getMd5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

	}
}
