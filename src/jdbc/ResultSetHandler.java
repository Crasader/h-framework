package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果处理handler
 * @author huangliy
 * @version 1.0.0.0 2017年3月1日下午7:50:27
 */
public interface ResultSetHandler<T> {
	/**
	 * 处理结果集
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	T handle(ResultSet rs) throws SQLException;
}
