package jdbc.sql;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * sqls模板
 * @author huangliy
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sqls", propOrder = {"sql"})
public class Sqls {
	protected List<Sql> sql;
	
	/**
	 * 获得sql
	 * @return
	 */
	public List<Sql> getSql() {
		if (sql == null) {
			sql = new ArrayList<Sql>();
		}
		return this.sql;
	}
}
