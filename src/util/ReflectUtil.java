package util;

import java.lang.reflect.Field;

/**
 * 反射工具
 * @author huangliy
 * @version 1.0.0.0 2017年4月2日下午2:50:05
 */
public class ReflectUtil {
	public static Object get(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (Throwable t) {
			throw new RuntimeException("invoke error", t);
		}
	}
}
