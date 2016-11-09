package jdbc.orm.cache;

/**
 * 配置信息
 * @author huangliy
 */
public class CacheConfig {
	public String name;// 名称	
	public int maxLiveTime;// 最长存活时间（s）
	public AccessType accessType; // 访问类型
	
	/**
	 * 构造函数
	 * @param name
	 * @param maxLiveTime
	 */
	public CacheConfig (String name, int maxLiveTime) {
		this.name = name;
		this.maxLiveTime = maxLiveTime;
	}
}
