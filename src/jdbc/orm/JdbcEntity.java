package jdbc.orm;

import java.util.ArrayList;
import java.util.List;

import jdbc.NameStrategy;
import jdbc.orm.cache.CacheManager;
import jdbc.orm.util.JdbcUtil;

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
	
	/**
	 * 解析实体
	 * @param clazz
	 * @param nameStrategy
	 * @return
	 * $Date: 2017年3月31日下午3:34:24
	 */
	public static JdbcEntity resolve(Class<?> clazz, NameStrategy nameStrategy) {
		JdbcEntity entity = new JdbcEntity();
		entity.clazz = clazz;
		entity.fields = JdbcUtil.createJdbcFields(clazz, nameStrategy);
		entity.nameStrategy = nameStrategy;
		entity.entityName = clazz.getSimpleName();
		entity.tableName = nameStrategy.propertyNameToColunmnName(entity.entityName);
		//TODO 生成通用查询语句
		// 主键解析
		List<JdbcField> idFields = new ArrayList<>();
		for (JdbcField field : entity.fields) {
			if (field.isPrimary) {
				idFields.add(field);
			}
		}
		entity.idFields = idFields.toArray(new JdbcField[0]);
		if (entity.idFields.length == 1) {
			entity.id = new SingleIdEntity(entity.idFields[0]);
		} else if (entity.idFields.length == 0) {
			throw new RuntimeException(entity.entityName + " doesn't has primary key.");
		} else {
			entity.id = new ComplexIdEntity(entity.idFields);
		}
		//TODO 索引解析
		return entity;
	}

	/**
	 * 获得查询所有sql
	 * @return
	 * $Date: 2017年3月28日下午8:40:32
	 */
	public String getSelectAllSQL() {
		return selectAllSQL;
	}
}
