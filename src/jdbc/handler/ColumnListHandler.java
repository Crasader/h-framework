package jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author huangliy
 * @version 1.0.0.0 2017年3月28日下午8:52:40
 */
public class ColumnListHandler extends AbstractListHandler<Object>{
	private int columnIndex;
	private String columnName;
	
	/**
	 * 构造函数
	 * @param columnIndex
	 * @param columnName
	 */
	public ColumnListHandler(int columnIndex, String columnName) {
		this.columnIndex = columnIndex;
		this.columnName = columnName;
	}
	
	/* 
	 * @see jdbc.handler.AbstractListHandler#handleRow(java.sql.ResultSet)
	 */
	@Override
	public Object handleRow(ResultSet rs) throws SQLException {
		if (this.columnName == null) {
			return rs.getObject(columnIndex);
		} else {
			return rs.getObject(columnName);
		}
	}
}
