package jdbc;

import java.util.ArrayList;

/**
 * JDBC参数集合
 * @author huangliy
 *
 */
public class Params extends ArrayList<Param>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** EMPTY */
	public static final Params EMPTY = new Params();
	
	/**
	 * 添加参数
	 * @param param
	 */
	public void addParam(Param param) {
		super.add(param);
	}
	
	/**
	 * 添加参数
	 * @param obj
	 * @param type
	 */
	public void addParam(Object obj, Type type) {
		super.add(new Param(obj, type));
	}
}
