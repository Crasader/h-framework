package jdbc;

/**
 * 命名策略转换
 * @author huangliy
 *
 */
public interface NameStrategy {
	
	/**
	 * 将列名转换成属性名称
	 * @param columnName
	 * @return
	 */
	String columnNameToPropertyName(String columnName);
	
	/**
	 * 将属性名转换成列名
	 * @param propertyName
	 * @return
	 */
	String propertyNameToColunmnName(String propertyName);
}
