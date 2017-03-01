package jdbc.orm;

import jdbc.NameStrategy;
import jdbc.orm.cache.CacheManager;

/**
 * JdbcEntity
 * @author huangliy
 */
public class JdbcEntity {
	private JdbcField[] fields;
	private IdEntity id;
	private IndexEntity index;
	private Class<?> clazz;
	private Class<?> enhanceClazz;// 增强类
	private NameStrategy nameStrategy;// 命名策略
	private String entityName;// 实体名
	private JdbcField[] idFields;
	private boolean enhance;// 是否加强过
	private String insertSQL;
	private String updateSQL;
	private String selectAllSQL;
	private String selectAllCountSQL;
	private String selectSQL;
	private String selectForUpdateSQL;
	private String deleteSQL;
	private String tableName;// 表名
	private ThreadLocal<Boolean> delaySQLEnable;//延迟执行SQL是否启用
	private CacheManager cacheManager; // 缓存管理器
	
	//TODO
	public static JdbcEntity resolve(Class<?> clazz, NameStrategy nameStrategy) {
		return null;
	}
}
