package jdbc.orm.cache;

/**
 * 访问策略
 * @author huangliy
 */
public interface CacheRegionAccessStrategy<K, V> extends Cache<K, V>{
	/**
	 * 获取缓存区域
	 * @return CacheRegion<K,V>
	 */
	CacheRegion<K, V> getCacheRegion();
	
	/**
	 * 锁定相关对象
	 * @param key
	 * @return LockItem<V>
	 */
	LockItem<V> lockItem(K key);
	
	/**
	 * 解除锁定
	 * @param key
	 * @param lock
	 * @return boolean
	 */
	boolean unlockItem(K key, LockItem<V> lock);
}
