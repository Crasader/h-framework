package jdbc.orm.session;

import org.springframework.transaction.support.TransactionSynchronizationManager;

import jdbc.orm.JdbcFactory;

/**
 * JdbcSessionUtil
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日上午11:22:32
 */
public class JdbcSessionUtil {
	/**
	 * 获取session
	 * @param jdbcFactory
	 * @param allowCreate
	 * @return
	 * $Date: 2017年4月1日下午1:29:27
	 */
	public static JdbcSession doGetSession(JdbcFactory jdbcFactory, boolean allowCreate) {
		JdbcSessionHolder sessionHolder = (JdbcSessionHolder) TransactionSynchronizationManager.getResource(jdbcFactory);
		JdbcSession session = null;
		
		if (sessionHolder != null && !sessionHolder.isEmpty()) {
			session = sessionHolder.getValidateSession();
			if (session != null) {
				return session;
			}
		}
		if (allowCreate) {
			session = jdbcFactory.openSession();
			if (TransactionSynchronizationManager.isSynchronizationActive()) {
				JdbcSessionHolder holderToUse = sessionHolder;
				if (holderToUse == null) {
					holderToUse = new JdbcSessionHolder(session);
				} else {
					holderToUse.setSession(session);
				}
				
				if (holderToUse != sessionHolder) {
					TransactionSynchronizationManager.bindResource(jdbcFactory, holderToUse);
				}
			}
		}
		
		return session;
	}

	/**
	 * 检查session是否处于事务中
	 * @param session
	 * @param jdbcFactory
	 * @return
	 * $Date: 2017年4月1日下午1:26:41
	 */
	public static boolean isSessionTransaction(JdbcSession session, JdbcFactory jdbcFactory) {
		if (jdbcFactory == null) {
			return false;
		}
		
		JdbcSessionHolder sessionHolder = (JdbcSessionHolder) TransactionSynchronizationManager.getResource(jdbcFactory);
		return sessionHolder != null && sessionHolder.containSession(session);
	}
}
