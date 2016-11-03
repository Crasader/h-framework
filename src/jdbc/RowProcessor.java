package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 行转换器
 * @author huangliy
 *
 */
public interface RowProcessor {
	
	/**
	 * 返回一个数组
	 * @param rs
	 * @return
	 */
	Object[] toArray(ResultSet rs) throws SQLException;
	
	/**
	 * 返回一个javabean
	 * @param rs
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	<T> T toBean(ResultSet rs, Class<T> type) throws SQLException;
	
	/**
	 * 返回一个javabeanList
	 * @param rs
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	<T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException;
	
	/**
	 * 返回一个map结果集
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> toMap(ResultSet rs) throws SQLException;
}
