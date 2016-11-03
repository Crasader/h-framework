package jdbc;

/**
 * JDBC 执行参数
 * @author huangliy
 *
 */
public class Param {
	public Object obj;// 参数
	public Type type;// 参数类型
	
	/**
	 * 构造函数
	 * @param obj
	 * @param type
	 */
	public Param(Object obj, Type type) {
		this.obj = obj;
		this.type = type;
	}
}
