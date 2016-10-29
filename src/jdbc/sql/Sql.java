package jdbc.sql;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * sql 模板
 * @author huangliy
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sql", propOrder = {"value"})
public class Sql {
	@XmlValue
	protected String value;
	@XmlAttribute
	protected String id;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
