package jdbc.orm;

import java.util.List;

import jdbc.Param;

/**
 * IndexEntity
 * @author huangliy
 */
public interface IndexEntity {
	
	/**
	 * 获取依靠索引用于查询语句
	 * @return String
	 */
	String selectSQL();
	
	/**
	 * 获取依靠索引需要构建的参数列表
	 * @param args
	 * @return List<Param>
	 */
	List<Param> builderParams(Object...args);
	
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
