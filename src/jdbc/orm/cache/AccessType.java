package jdbc.orm.cache;

/**
 * 访问方式
 * @author huangliy
 */
public enum AccessType {
	READ_WRITE("read-write");
	
	private String value;// 值
	
	/**
	 * 构造函数
	 * @param value
	 */
	private AccessType(String value) {
		this.value = value;
	}
	
	/**
	 * 获得值
	 * @return String
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * 获得访问类型
	 * @param value
	 * @return AccessType
	 */
	public AccessType getAccessType(String value) {
		if ("read-write".equalsIgnoreCase(value)) {
			return READ_WRITE;
		}
		return READ_WRITE;
	}
}
