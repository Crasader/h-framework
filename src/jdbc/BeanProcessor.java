package jdbc;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Bean转换器
 * @author huangliy
 */
public class BeanProcessor {
	/** 属性未找到 */
	protected static final int PROPERTY_NOT_FOUND = -1;
	
	/** 基本类型默认值map */
	private static Map<Class<?>, Object> primitiveDefualts = new HashMap<>();
	
	/** 命名策略 */
	private NameStrategy strategy = new DefaultNameStrategy();
	
	static {
		primitiveDefualts.put(Integer.TYPE, 0);
		primitiveDefualts.put(Short.TYPE, (short)0);
		primitiveDefualts.put(Float.TYPE, 0);
		primitiveDefualts.put(Double.TYPE, 0);
		primitiveDefualts.put(Boolean.TYPE, Boolean.FALSE);
		primitiveDefualts.put(Long.TYPE, 0);
		primitiveDefualts.put(Byte.TYPE, 0);
		primitiveDefualts.put(Character.TYPE, '\u0000');
	}
	
	/**
	 * 将结果集转换为对象
	 * @param rs
	 * @param type
	 * @return
	 * @throws SQLException
	 * $Date: 2017年3月30日上午11:56:54
	 */
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException{
		PropertyDescriptor[] props = getPropertyDescriptors(type);
		ResultSetMetaData rsmd = rs.getMetaData();
		int[] columnProperty = mapColumnToProperty(rsmd, props);
		
		return createBean(rs, type, props, columnProperty);
	}
	
	/**
	 * 获得转换对象列表
	 * @param rs
	 * @param type
	 * @return
	 * @throws SQLException
	 * $Date: 2017年3月31日下午1:29:21
	 */
	public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException{
		List<T> result = new ArrayList<>();
		if (!rs.next()) {
			return result;
		}
		PropertyDescriptor[] props = getPropertyDescriptors(type);
		ResultSetMetaData rsmd = rs.getMetaData();
		int[] columnProperty = mapColumnToProperty(rsmd, props);
		
		do {
			result.add(createBean(rs, type, props, columnProperty));
		} while (rs.next());
		return result;
	}
	
	/**
	 * 创建一个bean
	 * @param rs
	 * @param type
	 * @param props
	 * @param columnProperty
	 * @return
	 * $Date: 2017年3月30日下午4:26:25
	 */
	private <T> T createBean(ResultSet rs, Class<T> type, PropertyDescriptor[] props, int[] columnProperty) throws SQLException{
		T bean = newInstance(type);
		for (int i = 1; i < columnProperty.length; i++) {
			if (columnProperty[i] == PROPERTY_NOT_FOUND) {
				continue;
			}
			
			PropertyDescriptor prop = props[columnProperty[i]];
			Class<?> propType = prop.getPropertyType();
			Object value = processColumn(rs, i, propType);
			if (value == null && propType.isPrimitive()) {
				value = primitiveDefualts.get(propType);
			}
			
			callSetter(bean, prop, value);
		}
		return bean;
	}

	/**
	 * 填充属性
	 * @param bean
	 * @param prop
	 * @param value
	 * $Date: 2017年3月30日下午5:08:22
	 */
	private void callSetter(Object bean, PropertyDescriptor prop, Object value) throws SQLException {
		Method setter = prop.getWriteMethod();
		if (setter == null) {
			return;
		}
		
		try {
			Class<?>[] params = setter.getParameterTypes();
			if (value != null) {
				if (value instanceof java.util.Date) {
					if (params[0].getName().equals("java.sql.Date")) {
						value = new java.sql.Date(((java.util.Date) value).getTime());
					} else if (params[0].getName().equals("java.sql.Time")) {
						value = new java.sql.Time(((java.util.Date) value).getTime());
					} else if (params[0].getName().equals("java.sql.Timestamp")) {
						value = new java.sql.Timestamp(((java.util.Date) value).getTime());
					}
				}
			}
			
			if (isCompatibleType(value, params[0])) {
				setter.invoke(bean, new Object[] {value});
			} else {
				throw new SQLException("cannot set " + prop.getName() + ": incompatible types.");
			}
		} catch (IllegalAccessException e) {
			throw new SQLException("cannot set " + prop.getName() + ": " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new SQLException("cannot set " + prop.getName() + ": " + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new SQLException("cannot set " + prop.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 是否是兼容类型
	 * @param value
	 * @param class1
	 * @return
	 * $Date: 2017年3月30日下午5:10:58
	 */
	private boolean isCompatibleType(Object value, Class<?> type) {
		if (value == null || type.isInstance(value)) {
			return true;
		} else if (type.equals(Integer.TYPE) || Integer.class.isInstance(value)) {
			return true;
		} else if (type.equals(Short.TYPE) || Short.class.isInstance(value)) {
			return true;
		} else if (type.equals(Long.TYPE) || Long.class.isInstance(value)) {
			return true;
		} else if (type.equals(Double.TYPE) || Double.class.isInstance(value)) {
			return true;
		} else if (type.equals(Float.TYPE) || Float.class.isInstance(value)) {
			return true;
		} else if (type.equals(Boolean.TYPE) || Boolean.class.isInstance(value)) {
			return true;
		} else if (type.equals(Byte.TYPE) || Byte.class.isInstance(value)) {
			return true;
		} else if (type.equals(Character.TYPE) || Character.class.isInstance(value)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取指定列的返回值
	 * @param rs
	 * @param i
	 * @param propType
	 * @return
	 * $Date: 2017年3月30日下午4:36:51
	 * @throws SQLException 
	 */
	private Object processColumn(ResultSet rs, int index, Class<?> propType) throws SQLException {
		if (propType.equals(String.class)) {
			return rs.getString(index);
		} else if (propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
			return rs.getInt(index);
		} else if (propType.equals(Boolean.TYPE) || propType.equals(Boolean.class)) {
			return rs.getBoolean(index);
		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			return rs.getLong(index);
		} else if (propType.equals(Double.TYPE) || propType.equals(Double.class)) {
			return rs.getDouble(index);
		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return rs.getFloat(index);
		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return rs.getShort(index);
		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return rs.getByte(index);
		} else if (propType.equals(Timestamp.class)) {
			return rs.getTimestamp(index);
		} else {
			return rs.getObject(index);
		}
	}

	/**
	 * 创建一个新的实例
	 * @param type
	 * @return
	 * $Date: 2017年3月30日下午4:29:03
	 */
	private <T> T newInstance(Class<T> type) throws SQLException{
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			throw new SQLException("cannot create " + type.getName() + ":" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new SQLException("cannot create " + type.getName() + ":" + e.getMessage());
		}
	}

	/**
	 * 将列和属性进行映射
	 * @param rsmd
	 * @param props
	 * @return
	 * $Date: 2017年3月30日下午3:54:38
	 * @throws SQLException 
	 */
	private int[] mapColumnToProperty(ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {
		int cols = rsmd.getColumnCount();
		int[] columnToProperty = new int[cols + 1];
		Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);
		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnLabel(col);
			if (StringUtils.isBlank(columnName)) {
				columnName = rsmd.getColumnName(col);
			}
			// 从属性列表中查找匹配项
			for (int i = 0; i < props.length; i++) {
				if (strategy.columnNameToPropertyName(columnName).equals(props[i].getName())) {
					columnToProperty[col] = i;
					break;
				}
			}
		}
		
		return columnToProperty;
	}

	/**
	 * 获得propertyDescriptors类集合
	 * @param c
	 * @return
	 * @throws SQLException
	 * $Date: 2017年3月30日下午12:01:09
	 */
	private PropertyDescriptor[] getPropertyDescriptors(Class<?> c) throws SQLException {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(c);
		} catch (Exception e) {
			throw new SQLException("bean introspection failed:" + e.getMessage());
		}
		return beanInfo.getPropertyDescriptors();
	}
}
