package jdbc.orm.session;

import org.springframework.transaction.support.ResourceHolderSupport;

import jdbc.orm.transaction.Transaction;

/**
 * JdbcSessionHolder
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日上午11:49:02
 */
public class JdbcSessionHolder extends ResourceHolderSupport{
	/** session */
	private JdbcSession session;
	/** transaction */
	private Transaction transaction;
	
	/**
	 * 构造函数
	 * @param session
	 */
	public JdbcSessionHolder(JdbcSession session) {
		this.session = session;
	}
	
	/**
	 * 获得有效的session
	 * @return
	 * $Date: 2017年4月1日上午11:54:10
	 */
	public JdbcSession getValidateSession() {
		if (session != null && session.isClosed()) {
			session = null;
		}
		return session;
	}
	
	/**
	 * 判断是否包含session
	 * @param session
	 * @return
	 * $Date: 2017年4月1日上午11:55:11
	 */
	public boolean containSession(JdbcSession session) {
		return this.session == session;
	}
	
	/**
	 * 判断列队中session是否为空
	 * @return
	 * $Date: 2017年4月1日上午11:51:17
	 */
	public boolean isEmpty() {
		return session == null;
	}

	/* 
	 * @see org.springframework.transaction.support.ResourceHolderSupport#clear()
	 */
	@Override
	public void clear() {
		super.clear();
		this.transaction = null;
	}

	public JdbcSession getSession() {
		return session;
	}

	public void setSession(JdbcSession session) {
		this.session = session;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
