package jdbc.orm.session;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import jdbc.Param;
import jdbc.ResultSetHandler;
import jdbc.orm.JdbcEntity;
import jdbc.orm.JdbcFactory;
import jdbc.orm.extractor.OrmJdbcExtractor;
import jdbc.orm.transaction.Transaction;
import jdbc.orm.transaction.TransactionListener;
import log.InternalLoggerFactory;
import log.Logger;

/**
 * DefaultJdbcSession
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日上午11:56:21
 */
//TODO 之后添加缓存策略
public class DefaultJdbcSession implements JdbcSession, TransactionListener{
	private static final Logger log = InternalLoggerFactory.getLogger("jdbc.orm.session.jdbc");
	
	private Connection connection;
	private DataSource dataSource;
	private Transaction transaction;
	private boolean hasTransaction;
	private boolean closed;
	private OrmJdbcExtractor jdbcExtractor;
	private JdbcFactory jdbcFactory;
	
	/**
	 * 构造函数
	 * @param jdbcFactory
	 */
	public DefaultJdbcSession(JdbcFactory jdbcFactory) {
		this.dataSource = jdbcFactory.getDataSource();
		this.jdbcFactory = jdbcFactory;
		this.jdbcExtractor = jdbcFactory.getBaseJdbcExtractor();
		try {
			this.connection = DataSourceUtils.getConnection(this.dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			throw new RuntimeException("cannot get jdbcConnection");
		}
	}
	
	/* 
	 * @see jdbc.orm.extractor.BaseJdbcExtractor#read(java.lang.Object, jdbc.orm.JdbcEntity, jdbc.ResultSetHandler)
	 */
	@Override
	public <T, PK> T read(PK pk, JdbcEntity entity, ResultSetHandler<T> handler) {
		T t = jdbcExtractor.read(pk, entity, handler);
		return t;
	}

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#readByIndex(java.lang.Object[], jdbc.orm.JdbcEntity, jdbc.ResultSetHandler)
	 */
	@Override
	public <T> T readByIndex(Object[] indexs, JdbcEntity entity, ResultSetHandler<T> handler) {
		return jdbcExtractor.readByIndex(indexs, entity, handler);
	}

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#insert(java.lang.Object, jdbc.orm.JdbcEntity, java.lang.String[])
	 */
	@Override
	public <T> void insert(T newInstance, JdbcEntity entity, String... keys) {
		jdbcExtractor.insert(newInstance, entity, keys);
	}

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#update(java.lang.Object, jdbc.orm.JdbcEntity)
	 */
	@Override
	public <T> void update(T transientObject, JdbcEntity entity) {
		jdbcExtractor.update(transientObject, entity);
	}

	/* 
	 * @see jdbc.orm.extractor.OrmJdbcExtractor#delete(java.lang.Object, jdbc.orm.JdbcEntity)
	 */
	@Override
	public <PK> void delete(PK id, JdbcEntity entity) {
		jdbcExtractor.delete(id, entity);
	}

	/* 
	 * @see jdbc.JdbcExtractor#query(java.lang.String, java.util.List, jdbc.ResultSetHandler)
	 */
	@Override
	public <T> T query(String sql, List<Param> params, ResultSetHandler<T> handler) {
		return jdbcExtractor.query(sql, params, handler);
	}

	/* 
	 * @see jdbc.JdbcExtractor#query(java.lang.String, java.util.List)
	 */
	@Override
	public List<Map<String, Object>> query(String sql, List<Param> params) {
		return jdbcExtractor.query(sql, params);
	}

	/* 
	 * @see jdbc.JdbcExtractor#update(java.lang.String, java.util.List)
	 */
	@Override
	public int update(String sql, List<Param> params) {
		return jdbcExtractor.update(sql, params);
	}

	@Override
	public int insert(String sql, List<Param> params, boolean autoGenerator) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void begin(Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeCommit(Transaction transaction, boolean succ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit(Transaction transaction, boolean succ) {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * @see jdbc.orm.session.JdbcSession#getConnection()
	 */
	@Override
	public Connection getConnection() {
		return connection;
	}

	/* 
	 * @see jdbc.orm.session.JdbcSession#getTransaction()
	 */
	@Override
	public Transaction getTransaction() {
		return null;
	}

	/* 
	 * @see jdbc.orm.session.JdbcSession#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return closed;
	}

	/* 
	 * @see jdbc.orm.session.JdbcSession#close()
	 */
	@Override
	public void close() {
		if (isClosed()) {
			throw new RuntimeException("session is already closed");
		}
		// 释放连接
		DataSourceUtils.releaseConnection(connection, dataSource);
		// 清理数据
		clear();
		closed = true;
	}

	/* 
	 * @see jdbc.orm.session.JdbcSession#clear()
	 */
	@Override
	public void clear() {
		
	}

	/* 
	 * @see jdbc.orm.session.JdbcSession#getJdbcFactory()
	 */
	@Override
	public JdbcFactory getJdbcFactory() {
		return jdbcFactory;
	}

}
