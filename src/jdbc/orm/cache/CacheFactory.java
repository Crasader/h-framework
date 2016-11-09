package jdbc.orm.cache;

/**
 * CacheFactory
 * @author huangliy
 */
@SuppressWarnings("rawtypes")
public interface CacheFactory {
	/**
	 * 获得Cache
	 * @return Cache
	 */
	Cache getCache();
	
	/**
	 * 获得查询缓存
	 * @return Cache
	 */
	Cache getQueryCache();
}
