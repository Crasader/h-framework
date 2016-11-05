package jdbc.async;

import log.Logger;

/**
 * AsyncCallback
 * @author huangliy
 */
public interface AsyncCallback {
	/**
	 * 回调
	 */
	void callback();
	
	/**
	 * 记录日志
	 * @param log
	 * @param type 类型 1插入列队, 2成功执行, 3执行失败
	 */
	void doLog(Logger log, int type);
}
