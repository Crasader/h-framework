package jdbc.orm;

import jdbc.orm.annotation.AutoGenerator;

/**
 * SingleIdEntity
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日上午10:58:10
 */
public class SingleIdEntity implements IdEntity{
	/** field */
	private JdbcField field;
	/** 自动产生 */
	private boolean autoGenerator;
	
	/**
	 * 构造函数
	 * @param field
	 */
	public SingleIdEntity(JdbcField field) {
		this.field = field;
		this.autoGenerator = field.field.getAnnotation(AutoGenerator.class) != null;
	}
	
	/* 
	 * @see jdbc.orm.IdEntity#isAutoGenerator()
	 */
	@Override
	public boolean isAutoGenerator() {
		return autoGenerator;
	}

	/* 
	 * @see jdbc.orm.IdEntity#setIdValue(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public void setIdValue(Object obj, Object... args) {
		if (!autoGenerator) {
			return;
		}
		try {
			field.field.setAccessible(true);
			field.field.set(obj, args[0]);
		} catch (Throwable t) {
			throw new RuntimeException("set key error", t);
		} 
	}

	/* 
	 * @see jdbc.orm.IdEntity#getIdValue(java.lang.Object)
	 */
	@Override
	public Object[] getIdValue(Object obj) {
		try {
			field.field.setAccessible(true);
			Object result = field.field.get(obj);
			return new Object[] {result};
		} catch (Throwable t) {
			throw new RuntimeException("get key error", t);
		}
	}

	/* 
	 * @see jdbc.orm.IdEntity#getIdColumnName()
	 */
	@Override
	public String[] getIdColumnName() {
		return new String[] {field.columnName};
	}

	/* 
	 * @see jdbc.orm.IdEntity#getKeyValueByParams(java.lang.Object[])
	 */
	@Override
	public String getKeyValueByParams(Object... args) {
		return null;
	}

	/* 
	 * @see jdbc.orm.IdEntity#getKeyValueByObject(java.lang.Object)
	 */
	@Override
	public String getKeyValueByObject(Object obj) {
		return null;
	}

}
