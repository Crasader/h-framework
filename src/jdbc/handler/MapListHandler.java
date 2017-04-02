package jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import jdbc.BasicRowProcessor;
import jdbc.RowProcessor;

/**
 * MapListHandler
 * @author huangliy
 * @version 1.0.0.0 2017年4月2日下午4:03:39
 */
public class MapListHandler extends AbstractListHandler<Map<String, Object>>{
	/** 转换器 */
	private RowProcessor convert;
	
	/**
	 * 构造函数
	 */
	public MapListHandler() {
		this.convert = BasicRowProcessor.instance;
	}
	
	/* 
	 * @see jdbc.handler.AbstractListHandler#handleRow(java.sql.ResultSet)
	 */
	@Override
	public Map<String, Object> handleRow(ResultSet rs) throws SQLException {
		return convert.toMap(rs);
	}
}
