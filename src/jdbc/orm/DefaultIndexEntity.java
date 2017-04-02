package jdbc.orm;

import java.util.List;

import jdbc.Param;
import jdbc.Params;

/**
 * DefaultIndexEntity
 * @author huangliy
 * @version 1.0.0.0 2017年4月2日下午1:59:24
 */
public class DefaultIndexEntity implements IndexEntity{
	private JdbcField[] fields;
	private JdbcEntity entity;
	private String selectSql;
	
	/**
	 * 构造函数
	 * @param indexField
	 * @param entity
	 */
	public DefaultIndexEntity(JdbcField[] indexField, JdbcEntity entity) {
		this.fields = indexField;
		this.entity = entity;
		// 生成查询语句
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ").append(entity.getTableName()).append(" WHERE ");
		int index = 1;
		for (JdbcField field : indexField) {
			if (index > 1) {
				sb.append(" AND ");
			}
			sb.append(field.columnName).append("=?");
			index++;
		}
		selectSql = sb.toString();
	}

	/* 
	 * @see jdbc.orm.IndexEntity#selectSQL()
	 */
	@Override
	public String selectSQL() {
		return selectSql;
	}

	/* 
	 * @see jdbc.orm.IndexEntity#builderParams(java.lang.Object[])
	 */
	@Override
	public List<Param> builderParams(Object... args) {
		Params params = new Params();
		int index = 0;
		for (JdbcField field : fields) {
			params.addParam(args[index++], field.jdbcType);
		}
		return params;
	}

	/* 
	 * @see jdbc.orm.IndexEntity#getKeyValueByParams(java.lang.Object[])
	 */
	@Override
	public String getKeyValueByParams(Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @see jdbc.orm.IndexEntity#getKeyValueByObject(java.lang.Object)
	 */
	@Override
	public String getKeyValueByObject(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
