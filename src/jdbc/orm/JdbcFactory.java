package jdbc.orm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;

import jdbc.orm.cache.CacheConfig;
import jdbc.orm.cache.CacheFactory;
import jdbc.orm.extractor.BaseJdbcExtractor;
import jdbc.orm.transaction.TransactionListener;

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
	
	
	
	/* 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

}
