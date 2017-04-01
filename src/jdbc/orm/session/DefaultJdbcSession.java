package jdbc.orm.session;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import jdbc.Param;
import jdbc.Params;
import jdbc.ResultSetHandler;
import jdbc.orm.JdbcEntity;
import jdbc.orm.JdbcFactory;
import jdbc.orm.extractor.BaseJdbcExtractor;
import jdbc.orm.transaction.Transaction;
import jdbc.orm.transaction.TransactionListener;
import log.InternalLoggerFactory;
import log.Logger;

/**
 * DefaultJdbcSession
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日上午11:56:21
 */
public class DefaultJdbcSession implements JdbcSession, TransactionListener{
	private static final Logger log = InternalLoggerFactory.getLogger("jdbc.orm.session.jdbc");
	
	private Connection connection;
	private DataSource dataSource;
	private Transaction transaction;
	private boolean hasTransaction;
	private boolean closed;
	private BaseJdbcExtractor jdbcExtractor;
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
		// TODO 之后添加缓存策略
		T t = jdbcExtractor.read(pk, entity, handler);
		return t;
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

	@Override
	public <T> T query(String sql, List<Param> params, ResultSetHandler<T> handler) {
		// TODO Auto-generated method stub
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

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JdbcFactory getJdbcFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
