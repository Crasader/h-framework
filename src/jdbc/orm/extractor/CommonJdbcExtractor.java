package jdbc.orm.extractor;

import java.util.List;
import java.util.Map;

import jdbc.BaseJdbcExtractor;
import jdbc.Param;
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

	@Override
	public <T> T readByIndex(Object[] indexs, JdbcEntity entity, ResultSetHandler<T> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void insert(T newInstance, JdbcEntity entity, String... keys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void update(T transientObject, JdbcEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <PK> void delete(PK id, JdbcEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> List<T> query(String selectkey, String sql, List<Param> params, JdbcEntity entity,
			ResultSetHandler<T> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <PK> int update(String sql, List<Param> params, JdbcEntity entity, PK pk, String... keys) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <PK> int updateDelay(String sql, List<Param> params, JdbcEntity entity, PK pk, String... keys) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void batch(String sql, List<List<Param>> paramsList, JdbcEntity entity, String... key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean callProcedure(String sql, List<Param> params, JdbcEntity entity, String... keys) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> callProcedureWithReturn(String sql, List<Param> params, JdbcEntity entity, String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T query(String sql, List<Param> params, JdbcEntity entity, ResultSetHandler<T> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> query(String sql, List<Param> params, JdbcEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
