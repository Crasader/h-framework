package memorydb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * B+ Tree索引
 * @author huangliy
 * @version 1.0.0.0 2017年4月18日下午3:37:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface BTreeIndex {
	/**
	 * 索引名称
	 * @return
	 * $Date: 2017年4月18日下午3:38:41
	 */
	String name();
	
	/**
	 * 索引列
	 * @return
	 * $Date: 2017年4月18日下午3:39:00
	 */
	String[] value();
	
	/**
	 * 是否是唯一索引
	 * @return
	 * $Date: 2017年4月18日下午3:39:32
	 */
	boolean unique() default true;
}
