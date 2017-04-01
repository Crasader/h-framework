package jdbc.orm.session;

/**
 * JdbcSessionTrigger
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日上午11:43:26
 */
public interface JdbcSessionTrigger {
	void trigger();
	
	static final int INSERT = 1;
	static final int UPDATE = 2;
	static final int DELETE = 3;
}
