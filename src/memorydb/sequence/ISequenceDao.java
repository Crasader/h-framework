package memorydb.sequence;

import jdbc.orm.IBaseDao;

/**
 * ISequenceDao
 * @author huangliy
 * @version 1.0.0.0 2017年4月18日下午3:45:38
 */
public interface ISequenceDao extends IBaseDao<Sequence, Integer>{
	/**
	 * 获得下一个ID
	 * @param tableName
	 * @return
	 * $Date: 2017年4月18日下午3:46:16
	 */
	int nextId(String tableName);
	
	/**
	 * 获取Sequence
	 * @param tableName
	 * @return
	 * $Date: 2017年4月18日下午3:46:59
	 */
	Sequence getSequence(String tableName);
	
	/**
	 * 获取maxId
	 * @param tableName
	 * @return
	 * $Date: 2017年4月18日下午3:47:30
	 */
	int maxId(String tableName);
}
