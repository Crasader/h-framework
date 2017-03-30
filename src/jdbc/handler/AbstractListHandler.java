package jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.ResultSetHandler;

/**
 * AbstractListHandler
 * @author huangliy
 * @version 1.0.0.0 2017年3月28日下午8:50:50
 */
public abstract class AbstractListHandler<T> implements ResultSetHandler<List<T>>{
	
	/* 
	 * @see jdbc.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		List<T> rows = new ArrayList<>();
		while(rs.next()) {
			rows.add(this.handleRow(rs));
		}
		return rows;
	}

	/**
	 * 处理每一条信息
	 * @param rs
	 * @return
	 * $Date: 2017年3月28日下午8:50:36
	 */
	public abstract T handleRow(ResultSet rs) throws SQLException;
}
