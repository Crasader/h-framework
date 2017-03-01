package jdbc.orm.transaction;

/**
 * 事务
 * @author huangliy
 * @version 1.0.0.0 2017年3月1日下午8:29:00
 */
public interface Transaction {

	/**
	 * 事务开始
	 */
	void begin();
	
	/**
	 * 事务提交
	 */
	void commit();
	
	/**
	 * 事务回滚
	 */
	void rollback();
	
	/**
	 * 是否是活动事务
	 */
	boolean isActive();
}
