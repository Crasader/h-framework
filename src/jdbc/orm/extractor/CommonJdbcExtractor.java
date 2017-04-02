package jdbc.orm.extractor;

import jdbc.BaseJdbcExtractor;
import jdbc.ResultSetHandler;
import jdbc.orm.JdbcEntity;

/**
 * 通用数据库执行器
 * @author huangliy
 * @version 1.0.0.0 2017年4月2日下午12:49:03
 */
public class CommonJdbcExtractor extends BaseJdbcExtractor implements jdbc.orm.extractor.OrmJdbcExtractor {

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#read(java.lang.Object, jdbc.orm.JdbcEntity, jdbc.ResultSetHandler)
	 */
	@Override
	public <T, PK> T read(PK pk, JdbcEntity entity, ResultSetHandler<T> handler) {
		return query(entity.getSelectSQL(false), entity.builderSelectParams(pk), handler);
	}

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#readByIndex(java.lang.Object[], jdbc.orm.JdbcEntity, jdbc.ResultSetHandler)
	 */
	@Override
	public <T> T readByIndex(Object[] indexs, JdbcEntity entity, ResultSetHandler<T> handler) {
		return query(entity.getIndex().selectSQL(), entity.getIndex().builderParams(indexs), handler);
	}

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#insert(java.lang.Object, jdbc.orm.JdbcEntity, java.lang.String[])
	 */
	@Override
	public <T> void insert(T newInstance, JdbcEntity entity, String... keys) {
		int result = insert(entity.getInsertSQL(), entity.builderInsertParams(newInstance), entity.isAutoGenerator());
		if (entity.isAutoGenerator()) {
			entity.getId().setIdValue(newInstance, result);
		}
	}

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#update(java.lang.Object, jdbc.orm.JdbcEntity)
	 */
	@Override
	public <T> void update(T transientObject, JdbcEntity entity) {
		update(entity.getUpdateSQL(), entity.builderUpdateParams(transientObject));
	}

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#delete(java.lang.Object, jdbc.orm.JdbcEntity)
	 */
	@Override
	public <PK> void delete(PK id, JdbcEntity entity) {
		update(entity.getDeleteSQL(), entity.builderSelectParams(id));
	}
}
