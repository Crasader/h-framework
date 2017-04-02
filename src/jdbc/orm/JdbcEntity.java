package jdbc.orm;

import java.util.ArrayList;
import java.util.List;

import jdbc.NameStrategy;
import jdbc.Param;
import jdbc.Params;
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
	private JdbcField[] idFields;//主键集
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
		//TODO 生成通用查询语句
		entity.insertSQL = generatorInsertSQL(entity);
		entity.updateSQL = generatorUpdateSQL(entity);
		entity.selectSQL = generatorSelectSQL(entity);
		entity.deleteSQL = generatorDeleteSQL(entity);
		entity.selectForUpdateSQL = entity.selectSQL + " FOR UPDATE";
		entity.selectAllSQL = "SELECT * FORM " + entity.tableName;
		entity.selectAllCountSQL = "SELECT COUNT(*) FROM " + entity.tableName;
		entity.delaySQLEnable = new ThreadLocal<>();
		return entity;
	}

	/**
	 * 生成删除SQL
	 * @param entity
	 * @return
	 * $Date: 2017年4月2日下午1:22:37
	 */
	private static String generatorDeleteSQL(JdbcEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ")
		.append(entity.tableName)
		.append(" WHERE ");
		int index = 1;
		for (JdbcField field : entity.idFields) {
			if (index > 1) {
				sb.append(" AND ");
			}
			sb.append(field.columnName).append("=?");
			index++;
		}
		return sb.toString();
	}

	/**
	 * 生成查询语句
	 * @param entity
	 * @return
	 * $Date: 2017年4月2日下午1:18:27
	 */
	private static String generatorSelectSQL(JdbcEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ")
		.append(entity.tableName)
		.append(" WHERE ");
		int index = 1;
		for (JdbcField field : entity.idFields) {
			if (index > 1) {
				sb.append(" AND ");
			}
			sb.append(field.columnName).append("=?");
			index++;
		}
		return sb.toString();
	}

	/**
	 * 生成更新语句
	 * @param entity
	 * @return
	 * $Date: 2017年4月2日下午1:13:51
	 */
	private static String generatorUpdateSQL(JdbcEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ")
		.append(entity.tableName)
		.append(" SET ");
		int index = 1;
		for (JdbcField field : entity.fields) {
			if (field.ignore || field.isPrimary) {
				continue;
			}
			if (index > 1) {
				sb.append(", ");
			}
			sb.append(field.columnName).append("=?");
			index++;
		}
		sb.append(" WHERE ");
		index = 1;
		for (JdbcField field : entity.idFields) {
			if (index > 1) {
				sb.append(" AND ");
			}
			sb.append(field.columnName).append("=?");
			index++;
		}
		return null;
	}

	/**
	 * 生成插入语句
	 * @param entity
	 * @return
	 * $Date: 2017年4月2日下午1:02:32
	 */
	private static String generatorInsertSQL(JdbcEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ")
		.append(entity.tableName)
		.append("(");
		int index = 1;
		for (JdbcField field : entity.fields) {
			if (field.ignore || field.insertIgnore) {
				continue;
			}
			if (index > 1) {
				sb.append(", ");
			}
			sb.append(field.columnName);
			index++;
		}
		sb.append(") VALUES (");
		index = 1;
		for (JdbcField field : entity.fields) {
			if (field.ignore || field.insertIgnore) {
				continue;
			}
			if (index > 1) {
				sb.append(", ");
			}
			sb.append("?");
			index++;
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 获得查询所有sql
	 * @return
	 * $Date: 2017年3月28日下午8:40:32
	 */
	public String getSelectAllSQL() {
		return selectAllSQL;
	}
	
	/**
	 * 获得查询所有语句
	 * @param forUpdate
	 * @return
	 * $Date: 2017年4月2日下午12:52:37
	 */
	public String getSelectSQL(boolean forUpdate) {
		if (!forUpdate) {
			return selectSQL;
		} else {
			return selectForUpdateSQL;
		}
	}
	
	/**
	 * 构建查询参数
	 * @param keys
	 * @return
	 * $Date: 2017年4月2日下午12:58:27
	 */
	public List<Param> builderSelectParams(Object... keys) {
		Params params = new Params();
		int index = 0;
		for (JdbcField field : idFields) {
			params.addParam(keys[index++], field.jdbcType);
		}
		return params;
	}
}
