package jdbc.orm;

public class ComplexIdEntity implements IdEntity{
	/** fields */
	JdbcField[] fields;
	
	/**
	 * 构造函数
	 * @param fields
	 */
	public ComplexIdEntity(JdbcField[] fields) {
		this.fields = fields;
	}
	
	/* 
	 * @see jdbc.orm.IdEntity#isAutoGenerator()
	 */
	@Override
	public boolean isAutoGenerator() {
		return false;
	}

	/* 
	 * @see jdbc.orm.IdEntity#setIdValue(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public void setIdValue(Object obj, Object... args) {
		try {
			
		} catch (Throwable t) {
			throw new RuntimeException("set key error", t);
		}
	}

	@Override
	public Object[] getIdValue(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getIdColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeyValueByParams(Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeyValueByObject(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
