package jdbc.orm;

import jdbc.orm.session.JdbcSession;

/**
 * JdbcCallBack
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日下午1:24:34
 */
public interface JdbcCallBack<T> {
	/**
	 * 在session内执行
	 * @param session
	 * @return
	 * $Date: 2017年4月1日下午1:24:24
	 */
	T doInJdbcSession(JdbcSession session);
}
