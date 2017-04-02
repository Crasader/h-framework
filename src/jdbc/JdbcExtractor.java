package jdbc;

import java.util.List;
import java.util.Map;

/**
 * JdbcExtractor
 * @author huangliy
 * @version 1.0.0.0 2017年3月1日下午7:47:46
 */
public interface JdbcExtractor {
	/**
	 * 查询活动结果集
	 * @param sql
	 * @param params
	 * @param handler
	 * @return
	 */
	<T> T query(String sql, List<Param> params, ResultSetHandler<T> handler);
	
	/**
	 * 查询返回结果
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> query(String sql, List<Param> params);
	
	/**
	 * 更新数据库
	 * @param sql
	 * @param params
	 * @return	执行成功条数
	 */
	int update(String sql, List<Param> params);
	
	/**
	 * 插入数据库
	 * @param sql
	 * @param params
	 * @param autoGenerator
	 * @return
	 */
	int insert(String sql, List<Param> params, boolean autoGenerator);
	
	/**
	 * 执行批量操作
	 * @param sql
	 * @param paramsList
	 */
	void batch(String sql, List<List<Param>> paramsList);
	void batch(String sql, List<List<Param>> paramsList, int batchSize);
	void batch(List<String> sqlList);
	void batch(List<String> sqlList, int batchSize);
}
