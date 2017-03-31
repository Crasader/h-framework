package common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.lang.ClassType;
import common.lang.MyField;
import jdbc.Type;

/**
 * Lang
 * @author huangliy
 */
public class Lang {
	/** 类--成员列表 缓存 */
	public static Map<Class<?>, MyField[]> FIELD_MAP = new HashMap<>(1024);
	
	/**
	 * 获得一个类中所有成员列表
	 * @param clazz
	 * @return MyField[]
	 */
	public static MyField[] getFields(Class<?> clazz) {
		MyField[] myFields = FIELD_MAP.get(clazz);
		if (myFields != null) {
			return myFields;
		}
		synchronized (FIELD_MAP) {
			myFields = FIELD_MAP.get(clazz);
			if (myFields != null) {
				return myFields;
			}
			
			try {
				BeanInfo bi = Introspector.getBeanInfo(clazz);
				PropertyDescriptor[] pds = bi.getPropertyDescriptors();
				Map<String, PropertyDescriptor> map = new HashMap<>();
				for (PropertyDescriptor pd : pds) {
					if (pd.getPropertyType() == Class.class) continue;
					map.put(pd.getDisplayName(), pd);
				}
				
				// 解析field
				Field[] fields = clazz.getDeclaredFields();
				List<MyField> list = new ArrayList<>();
				for (Field field : fields) {
					PropertyDescriptor pd = map.get(field.getName());
					if (pd == null) continue;
					
					MyField myField = new MyField();
					myField.fieldName = pd.getDisplayName();
					myField.field = field;
					myField.type = getType(pd.getPropertyType());
					myField.getter = pd.getReadMethod();
					myField.setter = pd.getWriteMethod();
					list.add(myField);
				}
				myFields = list.toArray(new MyField[0]);
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
			FIELD_MAP.put(clazz, myFields);
		}
		return myFields;
	}

	/**
	 * 获取类的类型
	 * @param propertyType
	 * @return ClassType
	 */
	private static ClassType getType(Class<?> clazzType) {
		ClassType type = ClassType.PRIMITIVE_TYPE;
		if (clazzType.isAssignableFrom(Date.class)) {
			type = ClassType.DATE_TYPE;
		} else if (clazzType.isAssignableFrom(Map.class)) {
			type = ClassType.MAP_TYPE;
		} else if (clazzType.isAssignableFrom(List.class)) {
			type = ClassType.LIST_TYPE;
		} else if (clazzType.isArray()) {
			type = ClassType.ARRAY_TYPE;
		}
		
		return type;
	}

	/**
	 * 判断Field上是否包含指定声明
	 * @param field
	 * @param anClazz
	 * @return boolean
	 */
	public static boolean hasAnnotation(Field field, Class<? extends Annotation> anClazz) {
		Annotation annotation = field.getAnnotation(anClazz);
		return annotation == null;
	}

	/**
	 * 获取类的类型
	 * @param type
	 * @return Type
	 */
	public static Type getJdbcType(Class<?> clazzType) {
		Type type = Type.Object;
		if (isInteger(clazzType)) {
			type = Type.Int;
		} else if (isLong(clazzType)) {
			type = Type.Long;
		} else if (isDouble(clazzType)) {
			type = Type.Double;
		} else if (isFloat(clazzType)) {
			type = Type.Float;
		} else if (isStringLike(clazzType)) {
			type = Type.String;
		} else if (isByte(clazzType)) {
			type = Type.Byte;
		} else if (isShort(clazzType)) {
			type = Type.Int;
		} else if (isChar(clazzType)) {
			type = Type.Int;
		} else if (is(clazzType, java.sql.Date.class)) {
			type = Type.SqlDate;
		} else if (is(clazzType, java.util.Date.class)) {
			type = Type.Date;
		} else if (is(clazzType, java.sql.Time.class)) {
			type = Type.Time;
		} else if (is(clazzType, java.sql.Timestamp.class)) {
			type = Type.Timestamp;
		} else if (BigDecimal.class.isAssignableFrom(clazzType)) {
			type = Type.BigDecimal;
		} else if (Blob.class.isAssignableFrom(clazzType)) {
			type = Type.Blob;
		} else if (Clob.class.isAssignableFrom(clazzType)) {
			type = Type.Clob;
		} else if (NClob.class.isAssignableFrom(clazzType)) {
			type = Type.NClob;
		} else if (isBoolean(clazzType)) {
			type = Type.Bool;
		} else if (isBytes(clazzType)) {
			type = Type.Bytes;
		}
		
		return type;
	}
	
	/**
	 * 判断是否为byte[]
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isBytes(Class<?> clazz) {
		return is(clazz, byte[].class) || is(clazz, Byte[].class);
	}

	/**
	 * 判断是否为boolean
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isBoolean(Class<?> clazz) {
		return is(clazz, boolean.class) || is(clazz, Boolean.class);
	}

	/**
	 * 判断两个类型是否相同
	 * @param clazz1
	 * @param clazz2
	 * @return boolean
	 */
	public static boolean is(Class<?> clazz1, Class<?> clazz2) {
		return clazz1 == clazz2;
	}

	/**
	 * 判断是否为char
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isChar(Class<?> clazz) {
		return is(clazz, char.class) || is(clazz, Character.class);
	}

	/**
	 * 判断是否为short
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isShort(Class<?> clazz) {
		return is(clazz, short.class) || is(clazz, Short.class);
	}

	/**
	 * 判断是否为byte
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isByte(Class<?> clazz) {
		return is(clazz, byte.class) || is(clazz, Byte.class);
	}
	
	/**
	 * 判断是否为String
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isStringLike(Class<?> clazz) {
		return CharSequence.class.isAssignableFrom(clazz);
	}

	/**
	 * 判断是否为float
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isFloat(Class<?> clazz) {
		return is(clazz, float.class) || is(clazz, Float.class);
	}
	
	/**
	 * 判断是否为double
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isDouble(Class<?> clazz) {
		return is(clazz, double.class) || is(clazz, Double.class);
	}

	/**
	 * 判断是否为long
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isLong(Class<?> clazz) {
		return is(clazz, long.class) || is(clazz, Long.class);
	}

	/**
	 * 判断是否为int
	 * @param clazzType
	 * @return boolean
	 */
	public static boolean isInteger(Class<?> clazz) {
		return is(clazz, int.class) || is(clazz, Integer.class);
	}

	/**
	 * 获得类上指定的Annotation
	 * @param clazz
	 * @param anClass
	 * @return
	 * $Date: 2017年3月31日下午3:17:26
	 */
	public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> anClass) {
		while (null != clazz && clazz != Object.class) {
			T annotation = clazz.getAnnotation(anClass);
			if (null != annotation) {
				return annotation;
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}
}
