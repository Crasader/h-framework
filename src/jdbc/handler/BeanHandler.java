package jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.ResultSetHandler;
import jdbc.RowProcessor;

/**
 * BeanHandler
 * @author huangliy
 * @version 1.0.0.0 2017年3月29日下午5:45:37
 */
public class BeanHandler<T> implements ResultSetHandler<T>{
	/** 转换器 */
	private RowProcessor convert;
	
	/** 类型 */
	private Class<T> type;
	
	/**
	 * 构造函数
	 * @param type
	 * @param convert
	 */
	public BeanHandler(Class<T> type, RowProcessor convert) {
		this.type = type;
		this.convert = convert;
	}
	
	/* 
	 * @see jdbc.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	@Override
	public T handle(ResultSet rs) throws SQLException {
		return !rs.next() ? null : convert.toBean(rs, type);
	}

}
