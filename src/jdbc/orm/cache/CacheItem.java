package jdbc.orm.cache;
import java.io.Serializable;

/**
 * 缓存实体
 * @author huangliy
 */
public interface CacheItem<T> extends Serializable{
	/**
	 * 获取实体
	 * @return T
	 */
	T getValue();
	
	/**
	 * 是否可以写入
	 * @return boolean
	 */
	boolean isWritable();
}
