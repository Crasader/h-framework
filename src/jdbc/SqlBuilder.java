package jdbc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * SqlBuilder
 * @author huangliy
 * @version 1.0.0.0 2017年4月1日下午2:59:49
 */
public class SqlBuilder {
	/**
	 * 设置sql参数
	 * @param pstmt
	 * @param params
	 * @throws SQLException
	 * $Date: 2017年4月1日下午3:01:00
	 */
	public void buildParameters(PreparedStatement pstmt, List<Param> params) throws SQLException {
		int index = 1;
		for (Param param : params) {
			if (null == param) {
				pstmt.setObject(index, null);
			} else {
				fillParameter(index, pstmt, param);
			}
			index++;
		}
	}

	/**
	 * 填充参数
	 * @param index
	 * @param pstmt
	 * @param param
	 * $Date: 2017年4月1日下午3:05:24
	 * @throws SQLException 
	 */
	private void fillParameter(int index, PreparedStatement pstmt, Param param) throws SQLException {
		if (param.obj == null) {
			pstmt.setObject(index, param.obj);
			return;
		}
		
		switch (param.type) {
		case Object:
			pstmt.setObject(index, param.obj);
			break;
		case Like:
			pstmt.setString(index, param.obj + "%");
			break;	
		case BigDecimal:
			pstmt.setBigDecimal(index, (BigDecimal) param.obj);
			break;	
		case Blob:
			pstmt.setBlob(index, (Blob) param.obj);
			break;	
		case Byte:
			pstmt.setByte(index, (byte) param.obj);
			break;	
		case Bytes:
			pstmt.setBytes(index, (byte[]) param.obj);
			break;
		case Clob:
			pstmt.setClob(index, (Clob) param.obj);
			break;
		case Date:
			pstmt.setTimestamp(index, new Timestamp(((Date)param.obj).getTime() / 1000 * 1000));
			break;
		case SqlDate:
			pstmt.setDate(index, (java.sql.Date) param.obj);
			break;
		case Time:
			pstmt.setTime(index, (Time) param.obj);
			break;
		case Timestamp:
			pstmt.setTimestamp(index, (Timestamp) param.obj);
			break;
		case Double:
			pstmt.setDouble(index, (double) param.obj);
			break;
		case Float:
			pstmt.setFloat(index, (float) param.obj);
			break;
		case Int:
			pstmt.setInt(index, (int) param.obj);
			break;
		case Long:
			pstmt.setLong(index, (long) param.obj);
			break;
		case NClob:
			pstmt.setNClob(index, (NClob) param.obj);
			break;
		case String:
			pstmt.setString(index, (String) param.obj);
			break;
		case Bool:
			pstmt.setBoolean(index, (boolean) param.obj);
			break;
		case Out:
			break;
		default:
			throw new RuntimeException("unknow type [type: " + param.type + "]");
		}
	}
}
