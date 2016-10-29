package jdbc.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * sql 生成工厂
 * @author huangliy
 *
 */
@XmlRegistry
public class ObjectFactory {
	private final static QName sqls_QNAME = new QName("h-framework/sql", "sqls");
	
	/**
	 * 构造函数
	 */
	public ObjectFactory() {}
	
	/**
	 * 创建sql
	 * @return
	 */
	public Sql createSql() {
		return new Sql();
	}
	
	/**
	 * 创建sqls
	 * @return
	 */
	public Sqls createSqls() {
		return new Sqls();
	}
	
	@XmlElementDecl(namespace = "h-framework/sql", name = "sqls")
	public JAXBElement<Sqls> createSqls(Sqls value) {
		return new JAXBElement<Sqls>(sqls_QNAME, Sqls.class, null, value);
	}
	
	/**
	 * 解析
	 * @param input
	 * @param classPath
	 * @param schemaResPach
	 * @param loard
	 * @return
	 * @throws JAXBException 
	 * @throws SAXException 
	 */
	public static Object unmarshal(InputStream input, String classPath, String schemaResPach, ClassLoader loard) throws JAXBException, SAXException {
		JAXBContext context = JAXBContext.newInstance(classPath, loard);
		// 创建表结构
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Unmarshaller us = context.createUnmarshaller();
		if (schemaResPach != null && !schemaResPach.isEmpty()) {
			URL schemaURL = loard.getResource(schemaResPach);
			Schema schema = schemaFactory.newSchema(schemaURL);
			// 设置结构检查
			us.setSchema(schema);
		}
		// 解析
		Object obj = us.unmarshal(input);
		return obj;
	}
	
	/**
	 * 转化
	 * @param fi
	 * @return
	 * @throws IOException 
	 * @throws Throwable 
	 * @throws JAXBException 
	 */
	public static Sqls convertor(String fi) throws Throwable {
		ClassLoader classLoader = ObjectFactory.class.getClassLoader();
		String contextPath = ObjectFactory.class.getPackage().getName();
		File file = new File(fi);
		
		if (!file.isDirectory()) {
			InputStream input = new FileInputStream(file);
			@SuppressWarnings("unchecked")
			JAXBElement<Sqls> element = (JAXBElement<Sqls>) unmarshal(input, contextPath, null, classLoader);
			return element.getValue();
		}
		return null;
	}
	
	public static void main(String[] args) throws Throwable {
		Sqls sqls = ObjectFactory.convertor("D:\\github\\h-framework\\src\\jdbc\\sql\\sql.xml");
		System.out.println("haha");
	}
}
