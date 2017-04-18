package memorydb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * B+ TREE 多索引
 * @author huangliy
 * @version 1.0.0.0 2017年4月18日下午3:40:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface BTreeIndexs {
	/**
	 * 多重索引
	 * @return
	 * $Date: 2017年4月18日下午3:41:22
	 */
	BTreeIndex[] value();
}
