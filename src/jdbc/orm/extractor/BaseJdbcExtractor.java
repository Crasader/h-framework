package jdbc.orm.extractor;

import java.util.List;
import java.util.Map;

import jdbc.JdbcExtractor;
import jdbc.Param;
import jdbc.ResultSetHandler;
import jdbc.orm.JdbcEntity;

/**
 * BaseJdbcExtractor
 * @author huangliy
 * @version 1.0.0.0 2017年3月1日下午8:03:23
 */
public interface BaseJdbcExtractor extends JdbcExtractor{
	/**
	 * 按照主键从数据库中检索结果
	 * @param pk
	 * @param entity
	 * @param handler
	 * @return
	 */
	<T, PK> T read(final PK pk, final JdbcEntity entity, final ResultSetHandler<T> handler);
	
	/**
	 * 按照索引从数据库检索
	 * @param indexs
	 * @param entity
	 * @param handler
	 * @return
	 */
	<T> T readByIndex(final Object[] indexs, final JdbcEntity entity, final ResultSetHandler<T> handler);
	
	/**
	 * 插入
	 * @param newInstance
	 * @param entity
	 * @param keys
	 */
	<T> void insert(final T newInstance, final JdbcEntity entity, final String... keys);
	
	/**
	 * 更新
	 * @param transientObject
	 * @param entity
	 */
	<T> void update(final T transientObject, final JdbcEntity entity);
	
	/**
	 * 删除
	 * @param id
	 * @param entity
	 */
	<PK> void delete(final PK id, final JdbcEntity entity);
	
	/**
	 * 查询
	 * @param selectkey
	 * @param sql
	 * @param params
	 * @param entity
	 * @param handler
	 * @return
	 */
	<T> List<T> query(final String selectkey, final String sql, final List<Param> params, final JdbcEntity entity, final ResultSetHandler<T> handler);

	/**
	 * 更新数据
	 * @param sql
	 * @param params
	 * @param entity
	 * @param pk
	 * @param keys
	 * @return
	 */
	<PK> int update(final String sql, final List<Param> params, final JdbcEntity entity, final PK pk, final String... keys);
	
	/**
	 * 更新数据可被延迟
	 * @param sql
	 * @param params
	 * @param entity
	 * @param pk
	 * @param keys
	 * @return
	 */
	<PK> int updateDelay(final String sql, final List<Param> params, final JdbcEntity entity, final PK pk, final String... keys);
	
	/**
	 * 批量执行
	 * @param sql
	 * @param paramsList
	 * @param entity
	 * @param key
	 */
	void batch(final String sql, final List<List<Param>> paramsList, final JdbcEntity entity, final String... key);
	
	/**
	 * 调用存储过程
	 * @param sql
	 * @param params
	 * @param entity
	 * @param keys
	 * @return
	 */
	boolean callProcedure(final String sql, final List<Param> params, final JdbcEntity entity, final String... keys);

	/**
	 * 调用存储过程带返回
	 * @param sql
	 * @param params
	 * @param entity
	 * @param keys
	 * @return
	 */
	List<Object> callProcedureWithReturn(final String sql, final List<Param> params, final JdbcEntity entity, final String... keys);
	
	/**
	 * 查询获得结果集
	 * @param sql
	 * @param params
	 * @param entity
	 * @param handler
	 * @return
	 */
	<T> T query(String sql, List<Param> params, final JdbcEntity entity, ResultSetHandler<T> handler);
	
	/**
	 * 查询获得结果集
	 * @param sql
	 * @param params
	 * @param entity
	 * @return
	 */
	List<Map<String, Object>> query(String sql, List<Param> params, final JdbcEntity entity);
}
