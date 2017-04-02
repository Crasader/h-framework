package jdbc.orm.extractor;

import jdbc.JdbcExtractor;
import jdbc.ResultSetHandler;
import jdbc.orm.JdbcEntity;

/**
 * BaseJdbcExtractor
 * @author huangliy
 * @version 1.0.0.0 2017年3月1日下午8:03:23
 */
public interface OrmJdbcExtractor extends JdbcExtractor{
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
}
