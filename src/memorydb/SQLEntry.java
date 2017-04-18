package memorydb;

import jdbc.orm.annotation.AsyncOp;
import jdbc.orm.annotation.JdbcEntity;

/**
 * sql实体
 * @author huangliy
 * @version 1.0.0.0 2017年4月18日下午4:07:39
 */
public class SQLEntry {
	/** 语句编号 */
	public int id;
	/** 实体 */
	public JdbcEntity entity;
	/** 主键 */
	public String idKey;
	/** sql语句 */
	public String sql;
	/** 存活时间 */
	public long aliveTime;
	/** 数据库操作类型 */
	public AsyncOp op;
	/** 父节点 */
	public SQLEntry parent;
}
