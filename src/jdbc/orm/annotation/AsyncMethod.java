package jdbc.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记异步执行方法
 * @author huangliy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface AsyncMethod {
	/**
	 * 返回参数sql
	 * @return
	 */
	String sql() default "";
	
	/**
	 * 返回异步操作类型
	 * @return
	 */
	AsyncOp type() default AsyncOp.COMMON;
}
