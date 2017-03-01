package jdbc.orm.cache;

import java.util.List;

/**
 * Cache
 * @author huangliy
 */
@SuppressWarnings("unchecked")
public interface Cache<K, V> {
	/**
	 * 从缓存读
	 * @param key
	 * @return V
	 */
	V get(K key);
	
	/**
	 * 批量获取
	 * @param keys
	 * @return List<V>
	 */
	List<V> mget(K... keys);
	
	/**
	 * 放入缓存
	 * @param key
	 * @param value void
	 */
	void put(K key, V value);
	
	/**
	 * 放入缓存
	 * @param key
	 * @param item void
	 */
	void put(K key, CacheItem<V> item);
	
	/**
	 * 放入缓存
	 * @param key
	 * @param values void
	 */
	void put(K key, V...values);
	
	/**
	 * 移除缓存
	 * @param key void
	 */
	void remove(K key);
	
	/**
	 *  清空
	 */
	void clear();
	
	/**
	 *  销毁
	 */
	void destory();
	
	/**
	 * 获得大小
	 * @return int
	 */
	int size();
}
