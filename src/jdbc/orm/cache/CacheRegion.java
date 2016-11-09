package jdbc.orm.cache;

/**
 * 缓存实体
 * @author huangliy
 */
public interface CacheRegion<K, V> extends Cache<K, V> {
	/**
	 * 获得指定KEY的锁定对象
	 * @param key
	 * @return LockItem<V>
	 */
	LockItem<V> getLockItem(K key);
	
	/**
	 * 移除指定KEY的锁定
	 */
	void removeLockItem(K key);
}
