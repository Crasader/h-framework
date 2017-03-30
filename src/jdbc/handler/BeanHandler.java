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
	
	@Override
	public T handle(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
