package jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jdbc.BasicRowProcessor;
import jdbc.ResultSetHandler;
import jdbc.RowProcessor;

/**
 * BeanListHandler
 * @author huangliy
 * @version 1.0.0.0 2017年3月31日下午1:55:45
 */
public class BeanListHandler<T> implements ResultSetHandler<List<T>>{
	/** 转换器 */
	private RowProcessor convert;
	/** 类型 */
	private Class<T> type;
	
	/**
	 * 构造函数
	 * @param type
	 */
	public BeanListHandler(Class<T> type) {
		this.type = type;
		this.convert = BasicRowProcessor.instance;
	}
	
	/* 
	 * @see jdbc.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		return convert.toBeanList(rs, type);
	}
}
