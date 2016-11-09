package jdbc.orm;

/**
 * IdEntity
 * @author huangliy
 */
public interface IdEntity {
	/**
	 * 是否自动产生
	 * @return boolean
	 */
	boolean isAutoGenerator();
	
	/**
	 * 赋值
	 * @param obj
	 * @param args void
	 */
	void setIdValue(Object obj, Object...args);
	
	/**
	 * 获取key值
	 * @param obj
	 * @return Object[]
	 */
	Object[] getIdValue(Object obj);
	
	/**
	 * 获取主键列名
	 * @return String[]
	 */
	String[] getIdColumnName();
	
	/**
	 * 用于缓存
	 * @param args
	 * @return String
	 */
	String getKeyValueByParams(Object...args);
	
	/**
	 * 用于缓存
	 * @param obj
	 * @return String
	 */
	String getKeyValueByObject(Object obj);
}
