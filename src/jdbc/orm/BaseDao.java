package jdbc.orm;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import jdbc.BasicRowProcessor;
import jdbc.Params;
import jdbc.ResultSetHandler;
import jdbc.SqlFactory;
import jdbc.handler.BeanHandler;
import jdbc.handler.BeanListHandler;
import jdbc.handler.ColumnListHandler;

/**
 * BaseDao
 * @author huangliy
 * @version 1.0.0.0 2017年3月28日下午8:27:14
 */
public class BaseDao<T extends JdbcModel, PK extends Serializable> implements IBaseDao<T, PK>, InitializingBean{
	
	protected JdbcEntity entity;
	
	protected ResultSetHandler<T> handler;
	
	protected ResultSetHandler<List<T>> listHandler;
	
	protected ColumnListHandler columnListHandler;
	
	protected Class<T> clazz;
	
	@Autowired
	protected JdbcFactory jdbcFactory;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	protected SqlFactory sqlFactory;
	
	/* 
	 * @see jdbc.orm.IBaseDao#create(jdbc.orm.JdbcModel)
	 */
	@Override
	public void create(T newInstance) {
		jdbcTemplate.insert(newInstance, entity);
	}

	/* 
	 * @see jdbc.orm.IBaseDao#read(java.io.Serializable)
	 */
	@Override
	public T read(PK id) {
		return jdbcTemplate.read(id, entity, handler);
	}

	/* 
	 * @see jdbc.orm.IBaseDao#readByIndex(java.lang.Object[])
	 */
	@Override
	public T readByIndex(Object[] keys) {
		return jdbcTemplate.readByIndex(keys, entity, handler);
	}

	/* 
	 * @see jdbc.orm.IBaseDao#update(jdbc.orm.JdbcModel)
	 */
	@Override
	public void update(T transientObject) {
		jdbcTemplate.update(transientObject, entity);
	}

	/* 
	 * @see jdbc.orm.IBaseDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(PK id) {
		jdbcTemplate.delete(id, entity);
	}

	/* 
	 * @see jdbc.orm.IBaseDao#getModels()
	 */
	@Override
	public List<T> getModels() {
		return jdbcTemplate.query(entity.getSelectAllSQL(), Params.EMPTY, listHandler);
	}

	/* 
	 * @see jdbc.orm.IBaseDao#getResultByHQL(java.lang.String)
	 */
	@Override
	public List<T> getResultByHQL(String sql) {
		return jdbcTemplate.query(sql, Params.EMPTY, listHandler);
	}

	/* 
	 * @see jdbc.orm.IBaseDao#update(java.lang.String)
	 */
	@Override
	public void update(String sql) {
		jdbcTemplate.update(sql, Params.EMPTY);
	}

	/* 
	 * @see jdbc.orm.IBaseDao#count(java.lang.String, jdbc.Params)
	 */
	@Override
	public long count(String sql, Params params) {
		List<Object> resultList = jdbcTemplate.query(sql, params, columnListHandler);
		Long value = 0L;
		if (resultList.size() > 0) {
			value = (Long) resultList.get(0);
		}
		return value;
	}
	
	/* 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception { 
		if (null == jdbcFactory) {
			return;
		}
		entity = jdbcFactory.getJdbcEntity(clazz);
		handler = new BeanHandler<>(clazz, BasicRowProcessor.instance);
		listHandler = new BeanListHandler<>(clazz);
		columnListHandler = new ColumnListHandler(1, null);
	}

}
