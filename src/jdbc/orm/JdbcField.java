package jdbc.orm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import common.lang.ClassType;
import common.lang.MyField;
import jdbc.NameStrategy;
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
	public boolean ignore;//忽略的字段
	public boolean insertIgnore;//是否插入时忽略
	public Type jdbcType;//数据库类型
	public Method getter;//getter
	public Method setter;//setter
	
	/**
	 * 构造函数
	 * @param field
	 * @param nameStrategy
	 */
	public JdbcField(MyField field, NameStrategy nameStrategy) {
		this.field = field.field;
		this.fieldName = field.fieldName;
		this.propertyName = field.fieldName;
		this.columnName = nameStrategy.propertyNameToColunmnName(fieldName);
		this.type = field.type;
		this.getter = field.getter;
		this.setter = field.setter;
	}
}
