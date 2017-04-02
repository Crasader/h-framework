package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.InternalLoggerFactory;
import log.Logger;

/**
 * DbUtil
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日下午3:28:30
 */
public class DbUtil {
	private static final Logger log = InternalLoggerFactory.getLogger(DbUtil.class);
	
	/**
	 * 关闭结果集
	 * @param stmt
	 * @param rs
	 * $Date: 2017年4月1日下午3:31:26
	 */
	public static void closeQuietly(Statement stmt, ResultSet rs) {
		try {
			closeQuietly(rs);
		} finally {
			closeQuietly(stmt);
		}
	}

	/**
	 * closeStatement
	 * @param stmt
	 * $Date: 2017年4月1日下午3:33:21
	 */
	public static void closeQuietly(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error("cannot close jdbc statement", e);
			}
		}
		
	}

	/**
	 * closeResultSet
	 * @param rs
	 * $Date: 2017年4月1日下午3:33:32
	 */
	public static void closeQuietly(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("cannot close jdbc resultSet", e);
			}
		}
	}
}
