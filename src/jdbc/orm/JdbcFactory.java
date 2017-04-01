package jdbc.orm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;

import common.Lang;
import jdbc.NameStrategy;
import jdbc.orm.cache.CacheConfig;
import jdbc.orm.cache.CacheFactory;
import jdbc.orm.extractor.BaseJdbcExtractor;
import jdbc.orm.transaction.TransactionListener;
import util.Scans;

public class JdbcFactory implements InitializingBean{
	/** 扫描的包 */
	private String scanPackage;
	
	private Map<Class<?>, JdbcEntity> entityMap = new HashMap<>();
	
	private Map<String, CacheConfig> cacheConfigMap = new HashMap<>();
	
	private CacheFactory cacheFactory;
	
	private DataSource dataSource;
	
	private BaseJdbcExtractor baseJdbcExtractor;
	
	private List<TransactionListener> listeners;
	
	/** 缓存配置文件 */
	private String cacheConfigFile;
	
	/**
	 * @param scanPackage
	 * $Date: 2017年3月1日下午8:51:26
	 */
	public void setScanPackage(String scanPackage) {
		this.scanPackage = scanPackage;
	}
	
	/**
	 * @return
	 * $Date: 2017年3月31日下午3:13:38
	 */
	public String getScanPackage() {
		return scanPackage;
	}
	
	/**
	 * @param cacheFactory
	 * $Date: 2017年3月1日下午8:52:39
	 */
	public void setCacheFactory(CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}
	
	/**
	 * @return
	 * $Date: 2017年3月1日下午8:52:48
	 */
	public BaseJdbcExtractor getBaseJdbcExtractor() {
		return baseJdbcExtractor;
	}
	
	/**
	 * @param baseJdbcExtractor
	 * $Date: 2017年3月1日下午8:56:17
	 */
	public void setBaseJdbcExtractor(BaseJdbcExtractor baseJdbcExtractor) {
		this.baseJdbcExtractor = baseJdbcExtractor;
	}
	
	/**
	 * @return
	 * $Date: 2017年3月1日下午8:56:44
	 */
	public CacheFactory getCacheFactory() {
		return cacheFactory;
	}
	
	/**
	 * @return
	 * $Date: 2017年3月1日下午8:56:53
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * @param dataSource
	 * $Date: 2017年3月1日下午8:57:07
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * @param cacheConfigFile
	 * $Date: 2017年3月1日下午8:57:20
	 */
	public void setCacheConfigFile(String cacheConfigFile) {
		this.cacheConfigFile = cacheConfigFile;
	}
	
	/**
	 * 初始化
	 * @throws Exception
	 * $Date: 2017年3月2日下午4:57:48
	 */
	public void init() throws Exception {
		parseCacheConfig();
		
		// 扫描JDBC组建
		Set<Class<?>> set = Scans.getClasses(getScanPackage());
		for (Class<?> clazz : set) {
			jdbc.orm.annotation.JdbcEntity annotation = Lang.getAnnotation(clazz, jdbc.orm.annotation.JdbcEntity.class);
			if (annotation != null) {
				JdbcEntity entity = JdbcEntity.resolve(clazz, NameStrategy.defaultStrategy);
				entityMap.put(clazz, entity);
			}
		}
	}
	
	/**
	 * 解析缓存配置
	 * $Date: 2017年3月2日下午4:58:27
	 */
	private void parseCacheConfig() {
		if (null != cacheConfigFile) {
			
		}
		
	}
	
	/**
	 * 获得JDBC实体
	 * @param clazz
	 * @return
	 * $Date: 2017年3月28日下午9:00:44
	 */
	public JdbcEntity getJdbcEntity(Class<?> clazz) {
		return entityMap.get(clazz);
	}

	/* 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

}
