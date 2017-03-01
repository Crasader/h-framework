package jdbc.orm.transaction;

/**
 * TransactionListener
 * @author huangliy
 * @version 1.0.0.0 2017年3月1日下午8:28:24
 */
public interface TransactionListener {
	/**
	 * 开始事务
	 * @param transaction
	 * $Date: 2017年3月1日下午8:32:31
	 */
	void begin(Transaction transaction);
	
	/**
	 * 提交事务前
	 * @param transaction
	 * $Date: 2017年3月1日下午8:33:10
	 */
	void beforeCommit(Transaction transaction, boolean succ);
	
	/**事务提交
	 * @param transaction
	 * @param succ
	 * $Date: 2017年3月1日下午8:34:11
	 */
	void commit(Transaction transaction, boolean succ);
}
