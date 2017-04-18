package memorydb.sequence;

import jdbc.orm.annotation.Id;
import jdbc.orm.annotation.JdbcEntity;
import memorydb.AbstractDomain;
import memorydb.annotation.AutoId;
import memorydb.annotation.BTreeIndex;

/**
 * Sequence 表的pojo
 * @author huangliy
 * @version 1.0.0.0 2017年4月18日下午3:25:21
 */
@AutoId
@JdbcEntity
@BTreeIndex(name = "tablename", value = {"tableName"}, unique = true)
public class Sequence extends AbstractDomain {

	/** serialVersionUID */
	private static final long serialVersionUID = 2447034670115416607L;
	
	/** 主键 */
	@Id
	private int id;
	/** 表名 */
	private String tableName;
	/** 表sequence */
	private int sequence;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		super.mark();
		this.tableName = tableName;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/* 
	 * @see memorydb.AbstractDomain#clone()
	 */
	@Override
	public Object clone() {
		Sequence obj = new Sequence();
		
		obj.id = this.id;
		obj.tableName = this.tableName;
		obj.sequence = this.sequence;
		
		return obj;
	}
}
