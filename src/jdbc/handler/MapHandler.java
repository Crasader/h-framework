package jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jdbc.BasicRowProcessor;
import jdbc.ResultSetHandler;
import jdbc.RowProcessor;

/**
 * MapHandler
 * @author huangliy
 * @version 1.0.0.0 2017年4月2日下午4:00:11
 */
public class MapHandler implements ResultSetHandler<Map<String, Object>>{
	/** 转换器 */
	private RowProcessor convert;
	
	/**
	 * 构造函数
	 */
	public MapHandler() {
		this.convert = BasicRowProcessor.instance;
	}
	
	/* 
	 * @see jdbc.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	@Override
	public Map<String, Object> handle(ResultSet rs) throws SQLException {
		return rs.next() ? convert.toMap(rs) : null;
	}

}
