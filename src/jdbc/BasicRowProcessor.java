package jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 基本转换器
 * @author huangliy
 *
 */
public class BasicRowProcessor implements RowProcessor{
	/** 默认转换器 */
	public static BeanProcessor convert = new BeanProcessor();
	
	/* 
	 * @see jdbc.RowProcessor#toArray(java.sql.ResultSet)
	 */
	@Override
	public Object[] toArray(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int cLength = meta.getColumnCount();
		Object[] rtn = new Object[cLength];
		
		for (int i = 0; i < rtn.length; i++) {
			rtn[i] = rs.getObject(i);
		}
		
		return rtn;
	}

	/* 
	 * @see jdbc.RowProcessor#toBean(java.sql.ResultSet, java.lang.Class)
	 */
	@Override
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
		return convert.toBean(rs, type);
	}

	@Override
	public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @see jdbc.RowProcessor#toMap(java.sql.ResultSet)
	 */
	@Override
	public Map<String, Object> toMap(ResultSet rs) throws SQLException {
		Map<String, Object> rtn = new LinkedHashMap<>();
		ResultSetMetaData meta = rs.getMetaData();
		
		int cLength = meta.getColumnCount();
		
		for (int i = 0; i < cLength; i++) {
			rtn.put(meta.getColumnName(i), rs.getObject(i));
		}
		
		return rtn;
	}

}
