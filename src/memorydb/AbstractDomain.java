package memorydb;

import jdbc.orm.JdbcModel;

/**
 * 抽象实体
 * @author huangliy
 * @version 1.0.0.0 2017年4月18日下午3:06:07
 */
public abstract class AbstractDomain implements JdbcModel{

	/** serialVersionUID */
	private static final long serialVersionUID = 4075216088737690878L;
	
	/** 老值 */
	protected AbstractDomain old;
	
	/** 是否已经是托管对象 */
	public boolean managed;
	
	/** 是否经过mark */
	public boolean marked;
	
	/* 
	 * 克隆对象
	 * @see java.lang.Object#clone()
	 */
	public abstract Object clone();
	
	/**
	 * 获取老对象
	 * @return
	 * $Date: 2017年4月18日下午3:08:56
	 */
	public AbstractDomain oldDomain() {
		return old;
	}
	
	/**
	 * 设置老值
	 * $Date: 2017年4月18日下午3:09:47
	 */
	public void markOld() {
		if (null != old) {
			return;
		}
		old = (AbstractDomain) clone();
	}
	
	/**
	 * 重置
	 * $Date: 2017年4月18日下午3:10:48
	 */
	public void reset() {
		if (!managed) {
			return;
		}
		marked = false;
		old = null;
		markOld();
	}
	
	/**
	 * 标记
	 * $Date: 2017年4月18日下午3:18:00
	 */
	public void mark() {
		if (!managed || marked) {
			return;
		}
		marked = true;
		markOld();
	}
}
