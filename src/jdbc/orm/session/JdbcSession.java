package jdbc.orm.session;

import java.sql.Connection;

import jdbc.orm.JdbcFactory;
import jdbc.orm.extractor.OrmJdbcExtractor;
import jdbc.orm.transaction.Transaction;

/**
 * JdbcSession
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日上午11:24:16
 */
public interface JdbcSession extends OrmJdbcExtractor{
	/**
	 * 获取连接
	 * @return
	 * $Date: 2017年4月1日上午11:24:43
	 */
	Connection getConnection();
	
	/**
	 * 获取事务
	 * @return
	 * $Date: 2017年4月1日上午11:25:17
	 */
	Transaction getTransaction();
	
	/**
	 * 是否关闭
	 * @return
	 * $Date: 2017年4月1日上午11:25:34
	 */
	boolean isClosed();
	
	/**
	 * 关闭session
	 * $Date: 2017年4月1日上午11:25:48
	 */
	void close();
	
	/**
	 * 清理
	 * $Date: 2017年4月1日上午11:26:24
	 */
	void clear();
	
	/**
	 * 获得JdbcFactory
	 * @return
	 * $Date: 2017年4月1日上午11:32:18
	 */
	JdbcFactory getJdbcFactory();
}
