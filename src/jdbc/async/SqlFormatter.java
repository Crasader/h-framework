package jdbc.async;

import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common.Lang;
import jdbc.Param;
import util.DateUtil;

public class SqlFormatter {
	private List<TextPattern> patternList = new ArrayList<>();// 格式列表
	private String pattern;
	private List<Integer> offsetList = new ArrayList<>();
	
	/** cacheMap */
	private static Map<String, SqlFormatter> cacheMap = new ConcurrentHashMap<>();
	/** lock */
	private static Object lock = new Object();
	
	/**
	 * 构造函数
	 * @param pattern
	 */
	public SqlFormatter(String pattern) {
		parsePattern(pattern);
	}
	
	/**
	 * 解析pattern
	 * @param pattern void
	 */
	private void parsePattern(String pattern) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (int i = 0; i < pattern.length(); i++) {
			char ch = pattern.charAt(i);
			if (ch == '?') {
				offsetList.add(sb.length());
				patternList.add(new TextPattern(index++, null));
			} else {
				sb.append(ch);
			}
		}
		this.pattern = sb.toString();
	}
	
	/**
	 * 格式化字符串
	 * @param pattern
	 * @param paramList
	 * @return String
	 */
	public static String format(String pattern, List<Param> paramList) {
		SqlFormatter formatter = cacheMap.get(pattern);
		if (null == formatter) {
			synchronized (lock) {
				formatter = cacheMap.get(pattern);
				if (null == formatter) {
					formatter = new SqlFormatter(pattern);
					cacheMap.put(pattern, formatter);
				}
			}
		}
		return formatter.format(paramList);
	}

	/**
	 * 格式化
	 * @param pattern
	 * @param params
	 * @return String
	 */
	public static String format(String pattern, Object...params) {
		SqlFormatter formatter = cacheMap.get(pattern);
		if (null == formatter) {
			synchronized (lock) {
				formatter = cacheMap.get(pattern);
				if (null == formatter) {
					formatter = new SqlFormatter(pattern);
					cacheMap.put(pattern, formatter);
				}
			}
		}
		return formatter.format(params);
	}
	
	/**
	 * 格式化字符串
	 * @param list
	 * @return String
	 */
	private String format(List<Param> list) {
		StringBuilder sb = new StringBuilder(this.pattern.length());
		int lastOffset = 0;
		int i = 0;
		for (Integer offset : offsetList) {
			sb.append(this.pattern.substring(lastOffset, offset));
			int index = this.patternList.get(i).index;
			if (index < list.size()) {
				sb.append(getValue(list.get(index)));
			} else {
				sb.append("?");
			}
			lastOffset = offset;
			i++;
		}
		
		sb.append(this.pattern.substring(lastOffset, this.pattern.length()));
		return sb.toString();
	}
	
	/**
	 * 格式化字符串
	 * @param params
	 * @return String
	 */
	private String format(Object... params) {
		StringBuilder sb = new StringBuilder(this.pattern.length());
		int lastOffset = 0;
		int i = 0;
		for (Integer offset : offsetList) {
			sb.append(this.pattern.substring(lastOffset, offset));
			int index = this.patternList.get(i).index;
			if (index < params.length) {
				sb.append(getValue(params[index]));
			} else {
				sb.append("?");
			}
			lastOffset = offset;
			i++;
		}
		
		sb.append(this.pattern.substring(lastOffset, this.pattern.length()));
		return sb.toString();
	}
	
	/**
	 * 返回值
	 * @param object
	 * @return String
	 */
	private String getValue(Object obj) {
		if (null == obj) {
			return null;
		}
		
		Class<?> clazz = obj.getClass();
		if (Lang.isStringLike(clazz)) {
			return getValue(String.valueOf(obj));
		} else if (clazz.isAssignableFrom(Date.class) || Date.class.isAssignableFrom(clazz)) {
			return "'" + DateUtil.formatDate((Date) obj, DateUtil.PATTERN_FULLHYPHEN) + "'";
		}
		return String.valueOf(obj);
	}
	
	/**
	 * 返回参数值
	 * @param obj
	 * @return String
	 */
	private String getValue(Param obj) {
		if (null == obj || obj.obj == null) {
			return null;
		}
		
		switch (obj.type) {
		case String:
			return getValue((String)obj.obj);
		case Date:
			return "'" + DateUtil.formatDate((Date) obj.obj, DateUtil.PATTERN_FULLHYPHEN) + "'";
		default:
			break;
		}
		return null;
	}
	
	/**
	 * 返回加工后的String
	 * @param value
	 * @return String
	 */
	private String getValue(String value) {
		if (null == value) {
			return value;
		}
		value = value.replace("'", "''");
		return "'" + value + "'";
	}

	/**
	 * 文本格式
	 * @author huangliy
	 */
	private class TextPattern {
		public int index;// 序号
		@SuppressWarnings("unused")
		public Format format;// 格式化器
		public TextPattern(int index, Format format) {
			this.index = index;
			this.format = format;
		}
	}
}
