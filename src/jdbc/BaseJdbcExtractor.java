package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import jdbc.handler.MapListHandler;
import log.InternalLoggerFactory;
import log.Logger;

/**
 * BaseJdbcExtractor
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日下午2:41:58
 */
public class BaseJdbcExtractor implements JdbcExtractor, InitializingBean{
	/** log */
	private static final Logger log = InternalLoggerFactory.getLogger("jdbc");
	
	@Autowired
	private SqlFactory sqlFactory;
	@Autowired
	private DataSource dataSource;
	
	/** sql填充器 */
	SqlBuilder sqlBuilder = new SqlBuilder();
	
	private MapListHandler maplistHandler = new MapListHandler();
	
	/* 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// doNothing
	}

	/* 
	 * @see jdbc.JdbcExtractor#query(java.lang.String, java.util.List, jdbc.ResultSetHandler)
	 */
	@Override
	public <T> T query(String sql, List<Param> params, ResultSetHandler<T> handler) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		sql = sqlFactory.get(sql);
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			// 填充参数
			sqlBuilder.buildParameters(pstmt, params);
			rs = pstmt.executeQuery();
			
			return handler.handle(rs);
		} catch (Exception e) {
			RuntimeException re = new RuntimeException("query data error", e);
			throw re;
		} finally {
			DbUtil.closeQuietly(pstmt, rs);
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
	}

	/* 
	 * @see jdbc.JdbcExtractor#query(java.lang.String, java.util.List)
	 */
	@Override
	public List<Map<String, Object>> query(String sql, List<Param> params) {
		return query(sql, params, maplistHandler);
	}

	/* 
	 * @see jdbc.JdbcExtractor#update(java.lang.String, java.util.List)
	 */
	@Override
	public int update(String sql, List<Param> params) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int result = 0;
		sql = sqlFactory.get(sql);
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			// 填充参数
			sqlBuilder.buildParameters(pstmt, params);
			result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			throw new RuntimeException("execute sql: " +sql);
		} finally {
			DbUtil.closeQuietly(pstmt, rs);
			releaseConnection(conn);
		}
	}

	/**
	 * 释放连接
	 * @param conn
	 * $Date: 2017年4月1日下午3:35:04
	 */
	private void releaseConnection(Connection conn) {
		DataSourceUtils.releaseConnection(conn, dataSource);
	}

	/**
	 * 获得连接
	 * @return
	 * $Date: 2017年4月1日下午2:48:04
	 */
	protected Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}

	/* 
	 * @see jdbc.JdbcExtractor#insert(java.lang.String, java.util.List, boolean)
	 */
	@Override
	public int insert(String sql, List<Param> params, boolean autoGenerator) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int result = 0;
		sql = sqlFactory.get(sql);
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			sqlBuilder.buildParameters(pstmt, params);
			result = pstmt.executeUpdate();
			
			if (result != 0 && autoGenerator) {
				pstmt.close();
				pstmt = conn.prepareStatement("select last_insert_id()");
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("insert db error: " + sql);
		}
		
		return result;
	}
}
