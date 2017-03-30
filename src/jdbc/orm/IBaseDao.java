package jdbc.orm;

import java.io.Serializable;
import java.util.List;

import jdbc.Params;

/**
 * IBaseDao
 * @author huangliy
 * @version 1.0.0.0 2017年3月16日下午2:34:46
 */
public interface IBaseDao<T extends JdbcModel, PK extends Serializable> {
	/**
	 * 创建新的实例
	 * @param newInstance
	 * $Date: 2017年3月16日下午2:36:34
	 */
	void create(T newInstance);
	
	/**
	 * 按照主键获取对象
	 * @param id
	 * @return
	 * $Date: 2017年3月28日下午8:19:35
	 */
	T read(PK id);
	
	/**
	 * 按照索引获得对象
	 * @param keys
	 * @return
	 * $Date: 2017年3月28日下午8:20:12
	 */
	T readByIndex(Object[] keys);
	
	/**
	 * 更新对象
	 * @param transientObject
	 * $Date: 2017年3月28日下午8:20:53
	 */
	void update(T transientObject);
	
	/**
	 * 根据主键删除对象
	 * @param id
	 * $Date: 2017年3月28日下午8:21:17
	 */
	void delete(PK id);
	
	/**
	 * 查询所有
	 * @return
	 * $Date: 2017年3月28日下午8:22:14
	 */
	List<T> getModels();
	
	/**
	 * 通过sql查询结果集
	 * @param sql
	 * @return
	 * $Date: 2017年3月28日下午8:23:02
	 */
	List<T> getResultByHQL(String sql);
	
	/**
	 * 执行sql更新
	 * @param sql
	 * $Date: 2017年3月28日下午8:23:44
	 */
	void update(String sql);
	
	/**
	 * 统计总和
	 * @return
	 * $Date: 2017年3月28日下午8:24:41
	 */
	long count(String sql, Params params);
}
