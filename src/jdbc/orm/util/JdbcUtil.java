package jdbc.orm.util;

import java.util.HashMap;
import java.util.Map;

import common.Lang;
import common.lang.MyField;
import jdbc.NameStrategy;
import jdbc.orm.JdbcField;
import jdbc.orm.annotation.Id;
import jdbc.orm.annotation.IgnoreField;
import jdbc.orm.annotation.InserIgnoreField;
import log.InternalLoggerFactory;
import log.Logger;
import net.sf.cglib.beans.BeanMap;

/**
 * jdbc工具类
 * @author huangliy
 */
public class JdbcUtil {
	/** log */
	private static final Logger log = InternalLoggerFactory.getLogger(JdbcUtil.class);
	
	private JdbcUtil() {}
	/** cacheMap */
	private static Map<Class<?>, JdbcField[]> cacheMap = new HashMap<>();
	/** beanMapCache */
	private static Map<Class<?>, BeanMap> beanMapCache = new HashMap<>();
	
	/**
	 * 初始化beanMap
	 * @param clazz
	 * @return BeanMap
	 */
	public static BeanMap createBeanMap(Class<?> clazz) {
		try {
			BeanMap beanMap = BeanMap.create(clazz.newInstance());
			beanMapCache.put(clazz, beanMap);
		} catch (Exception e) {
			log.error("create bean map error, clazz: " + clazz.getName(), e);
		}
		return null;
	}
	
	/**
	 * 获得jdbc Field
	 * @param clazz
	 * @return JdbcField[]
	 */
	public static JdbcField[] getJdbcField(Class<?> clazz) {
		return cacheMap.get(clazz);
	}
	
	/**
	 * 获得beanMap
	 * @param clazz
	 * @return BeanMap
	 */
	public static BeanMap getBeanMap(Class<?> clazz) {
		return beanMapCache.get(clazz);
	}
	
	/**
	 * 创建JdbcField
	 * @param clazz
	 * @param nameStategy
	 * @return JdbcField[]
	 */
	public static JdbcField[] createJdbcFields(Class<?> clazz, NameStrategy nameStategy) {
		MyField[] fields = Lang.getFields(clazz);
		if (fields == null) {
			return null;
		}
		
		JdbcField[] jdbcFields = null;
		if (fields.length == 0) {
			jdbcFields = new JdbcField[0];
			cacheMap.put(clazz, jdbcFields);
			return jdbcFields;
		}
		
		jdbcFields = new JdbcField[fields.length];
		for (int i = 0; i < fields.length; i++) {
			jdbcFields[i] = new JdbcField(fields[i], nameStategy);
			jdbcFields[i].isPrimary = Lang.hasAnnotation(jdbcFields[i].field, Id.class);
			jdbcFields[i].insertIgnore = Lang.hasAnnotation(jdbcFields[i].field, InserIgnoreField.class);
			jdbcFields[i].ignore = Lang.hasAnnotation(jdbcFields[i].field, IgnoreField.class);
			jdbcFields[i].jdbcType = Lang.getJdbcType(jdbcFields[i].field.getType());
		}
		return null;
	}
}
