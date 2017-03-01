package jdbc.async;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import jdbc.SqlFactory;
import jdbc.orm.JdbcEntity;
import jdbc.orm.annotation.AsyncMethod;
import log.Logger;

/**
 * 数据库异步操作
 * @author huangliy
 */
public class DbAsyncCallback implements AsyncCallback{
	/** idGenerator */
	private static AtomicInteger idGenerator = new AtomicInteger();
	
	private Method method;
	private Object obj;
	private Object[] args;
	private String sql;
	private int id;
	
	/**
	 * 构造函数
	 * @param async
	 * @param entity
	 * @param factory
	 * @param method
	 * @param obj
	 * @param args
	 */
	public DbAsyncCallback(AsyncMethod async, JdbcEntity entity, SqlFactory factory, Method method, Object obj, Object... args) {
		this.method = method;
		this.obj = obj;
		this.args = args;
		this.id = idGenerator.incrementAndGet();
		this.sql = getSQL(async, entity, factory, args);
	}
	
	/**
	 * 获取异步执行的sql
	 * @param async
	 * @param entity
	 * @param factory
	 * @param args
	 * @return String
	 */
	private String getSQL(AsyncMethod async, JdbcEntity entity, SqlFactory factory, Object[] args) {
		String sql = async.sql();
		if (StringUtils.isNotBlank(sql)) {
			String temp = factory.get(sql);
			sql = (null == temp) ? sql : temp;
			sql = sql.trim();
			return SqlFormatter.format(sql, args);
		}
		// 添加jdbc实体逻辑
		sql = null;
		switch (async.type()) {
		case INSERT:
			
			break;

		default:
			break;
		}
		return null;
	}

	/* 
	 * @see jdbc.async.AsyncCallback#callback()
	 */
	@Override
	public void callback() {
		try {
			method.invoke(obj, args);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/* 
	 * @see jdbc.async.AsyncCallback#doLog(log.Logger, int)
	 */
	@Override
	public void doLog(Logger log, int type) {
		if (null != sql) {
			switch (type) {
			case 1:
				// 插入列队
				log.info("{}#{}#{}", id, type, sql);
				break;
			case 2:// 执行成功
			case 3:// 执行失败
				log.info("{}#{}", id, type);
				break;
			default:
				break;
			}
		}
	}

}
