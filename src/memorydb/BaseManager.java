package memorydb;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jdbc.BaseJdbcExtractor;
import jdbc.orm.BaseDao;
import log.InternalLoggerFactory;
import log.Logger;
import memorydb.sequence.ISequenceDao;

/**
 * 内存数据库基础管理器
 * @author huangliy
 * @version 1.0.0.0 2017年4月18日下午3:19:39
 */
@SuppressWarnings("unchecked")
public abstract class BaseManager<T extends AbstractDomain, K extends Serializable> extends BaseDao<T, K> implements InitializingBean{
	/** log */
	protected static final Logger log = InternalLoggerFactory.getLogger(BaseManager.class);
	
	@Autowired
	@Qualifier("commonDao")
	private BaseJdbcExtractor extractor;
	
	@Autowired(required = false)
	private ISequenceDao sequenceDao;
	
	/** 内存表 */
	private MemoryTable<T, K> table;

	/**
	 * 获得内存表
	 * @return
	 * $Date: 2017年4月18日下午3:50:48
	 */
	public MemoryTable<T, K> getMemoryTable() {
		return table;
	}
	
	/**
	 * 构造函数
	 */
	public BaseManager() {
		try {
			clazz = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception e) {
			clazz = (Class<T>) this.getClass().getGenericSuperclass();
		}
	}
	
	/* 
	 * @see jdbc.orm.BaseDao#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		AsyncDBExecutor.getInstance().init(sqlFactory, jdbcFactory.getDataSource());
		entity = jdbcFactory.getJdbcEntity(clazz);
		table = new MemoryTable<>();
		
	}

	/**
	 * 服务器启动配置结束后执行
	 * @throws Exception
	 * $Date: 2017年4月18日下午3:57:06
	 */
	public abstract void doAfterPropertiesSet() throws Exception;
	
	/**
	 * 按playerId清理数据
	 * @param playerId
	 * $Date: 2017年4月18日下午3:57:46
	 */
	public abstract void clearDataByPlayerId(int playerId);
	
	/**
	 * 按playerId载入数据
	 * @param playerId
	 * $Date: 2017年4月18日下午3:58:26
	 */
	public abstract void loadDataByPlayerId(int playerId);
}
