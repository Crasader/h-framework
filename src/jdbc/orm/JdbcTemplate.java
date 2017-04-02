package jdbc.orm;

import java.util.List;
import java.util.Map;

import jdbc.Param;
import jdbc.Params;
import jdbc.ResultSetHandler;
import jdbc.orm.extractor.OrmJdbcExtractor;
import jdbc.orm.session.JdbcSession;
import jdbc.orm.session.JdbcSessionUtil;

/**
 * JdbcTemplate
 * @author huangliy
 * @version 1.0.0.0 2017年3月28日下午8:30:04
 */
public class JdbcTemplate implements OrmJdbcExtractor{
	/** jdbcFactory */
	private JdbcFactory jdbcFactory;
	
	/**
	 * 获取session
	 * @return
	 * $Date: 2017年4月1日下午1:17:24
	 */
	protected JdbcSession getSession() {
		return JdbcSessionUtil.doGetSession(jdbcFactory, true);
	}
	
	/**
	 * 执行jdbc操作
	 * @param action
	 * @return
	 * $Date: 2017年4月1日下午1:24:54
	 */
	protected <T> T doExecute(JdbcCallBack<T> action) {
		JdbcSession session = getSession();
		boolean existingTransaction = JdbcSessionUtil.isSessionTransaction(session, getJdbcFactory());
		try {
			T result = action.doInJdbcSession(session);
			return result;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			if (!existingTransaction) {
				session.close();
			}
		}
	}
	
	public JdbcFactory getJdbcFactory() {
		return jdbcFactory;
	}

	public void setJdbcFactory(JdbcFactory jdbcFactory) {
		this.jdbcFactory = jdbcFactory;
	}

	@Override
	public <T> T query(String sql, List<Param> params, ResultSetHandler<T> handler) {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> query(String sql, List<Param> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(String sql, List<Param> params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(String sql, List<Param> params, boolean autoGenerator) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void batch(String sql, List<List<Param>> paramsList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batch(String sql, List<List<Param>> paramsList, int batchSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batch(List<String> sqlList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batch(List<String> sqlList, int batchSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean callProcedure(String sql, List<Param> params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> callProcedureWithReturn(String sql, List<Param> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> callQueryProcedure(String sql, List<Params> params) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @see jdbc.orm.extractor.BaseJdbcExtractor#read(java.lang.Object, jdbc.orm.JdbcEntity, jdbc.ResultSetHandler)
	 */
	@Override
	public <T, PK> T read(PK pk, JdbcEntity entity, ResultSetHandler<T> handler) {
		T result = doExecute(new JdbcCallBack<T>() {
			@Override
			public T doInJdbcSession(JdbcSession session) {
				return session.read(pk, entity, handler);
			}
		});
		return result;
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
}
