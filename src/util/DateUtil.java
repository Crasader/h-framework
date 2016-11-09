package util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具
 * @author huangliy
 */
public class DateUtil {
	
	/** yyyy-MM-dd HH:mm:ss */
	public static final String PATTERN_FULLHYPHEN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 转换时间
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isBlank(pattern)) {
			return null;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		String rtn = fmt.format(date);
		return rtn;
	}
}
