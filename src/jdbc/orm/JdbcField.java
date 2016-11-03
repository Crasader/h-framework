package jdbc.orm;

import java.lang.reflect.Field;

import common.lang.ClassType;
import jdbc.Type;

/**
 * JdbcField
 * @author huangliy
 *
 */
public class JdbcField {
	public Field field;//变量
	public String fieldName;//变量名
	public String propertyName;//属性名
	public String columnName;//列名
	public ClassType type;//数据类型
	public boolean isPrimary;//主键
	public Type jdbcType;//数据库类型
}
