package jdbc;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean转换器
 * @author huangliy
 */
public class BeanProcessor {
	/** 属性未找到 */
	protected static final int PROPERTY_NOT_FOUND = -1;
	
	/** 基本类型默认值map */
	private static Map<Class<?>, Object> primitiveDefualts = new HashMap<>();
	
	private NameStrategy strategy;
}
