package jdbc.orm;

import java.util.ArrayList;
import java.util.List;

import common.Lang;
import jdbc.NameStrategy;
import jdbc.Param;
import jdbc.Params;
import jdbc.orm.annotation.Index;
import jdbc.orm.cache.CacheManager;
import jdbc.orm.util.JdbcUtil;
import util.ReflectUtil;

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
		// 索引解析
		Index index = Lang.getAnnotation(entity.clazz, Index.class);
		if (null != index) {
			String[] columns = index.value();
			List<JdbcField> indexFields = new ArrayList<>();
			for (String column : columns) {
				JdbcField temp = null;
				for (JdbcField field : entity.fields) {
					if (column.equals(field.propertyName)) {
						temp = field;
						break;
					}
				}
				if (temp == null) {
					throw new RuntimeException("can not find index column, index: " + column);
				}
				indexFields.add(temp);
			}
			entity.index = new DefaultIndexEntity(indexFields.toArray(new JdbcField[0]), entity);
			
		}
		// 生成通用查询语句
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
	
	/**
	 * 构建插入参数
	 * @param keys
	 * @return
	 * $Date: 2017年4月2日下午2:46:00
	 */
	public List<Param> builderInsertParams(Object instance) {
		Params params = new Params();
		for (JdbcField field : fields) {
			if (field.ignore || field.insertIgnore) {
				continue;
			}
			params.addParam(ReflectUtil.get(field.field, instance), field.jdbcType);
		}
		return params;
	}
	
	/**
	 * 构建更新参数
	 * @param instance
	 * @return
	 * $Date: 2017年4月2日下午3:02:02
	 */
	public List<Param> builderUpdateParams(Object instance) {
		Params params = new Params();
		for (JdbcField field : fields) {
			if (field.ignore || field.isPrimary) {
				continue;
			}
			params.addParam(ReflectUtil.get(field.field, instance), field.jdbcType);
		}
		
		for (JdbcField field : idFields) {
			params.addParam(ReflectUtil.get(field.field, instance), field.jdbcType);
		}
		return params;
	}
	
	/**
	 * 获得实体类型
	 * @return
	 * $Date: 2017年4月2日下午1:51:45
	 */
	public Class<?> getEntityClass() {
		return clazz;
	}

	public JdbcField[] getFields() {
		return fields;
	}

	public IdEntity getId() {
		return id;
	}

	public IndexEntity getIndex() {
		return index;
	}

	public Class<?> getEnhanceClazz() {
		return enhanceClazz;
	}

	public NameStrategy getNameStrategy() {
		return nameStrategy;
	}

	public String getEntityName() {
		return entityName;
	}

	public JdbcField[] getIdFields() {
		return idFields;
	}

	public boolean isEnhance() {
		return enhance;
	}

	public String getInsertSQL() {
		return insertSQL;
	}

	public String getUpdateSQL() {
		return updateSQL;
	}

	public String getSelectAllCountSQL() {
		return selectAllCountSQL;
	}

	public String getSelectSQL() {
		return selectSQL;
	}

	public String getSelectForUpdateSQL() {
		return selectForUpdateSQL;
	}

	public String getDeleteSQL() {
		return deleteSQL;
	}

	public String getTableName() {
		return tableName;
	}
	
	/**
	 * 主键是否自增
	 * @return
	 * $Date: 2017年4月2日下午2:53:22
	 */
	public boolean isAutoGenerator() {
		return id.isAutoGenerator();
	}
}
