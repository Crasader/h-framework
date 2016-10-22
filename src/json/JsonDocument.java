package json;

import static string.SymbolConstants.C_COLON;
import static string.SymbolConstants.C_COMMA;
import static string.SymbolConstants.C_L_BRACE;
import static string.SymbolConstants.C_L_BRACIKET;
import static string.SymbolConstants.C_QUOT;
import static string.SymbolConstants.C_R_BRACE;
import static string.SymbolConstants.C_R_BRACIKET;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * json 文本编辑器
 * @author huangliy
 *
 */
public class JsonDocument {
	private SerializeWriter writer;// 输入
	private JSONSerializer serializer; // 序列化
	private boolean first = true;
	
	/**
	 * 构造函数
	 */
	public JsonDocument() {
		writer = new SerializeWriter();
		serializer = new JSONSerializer(writer);
	}
	
	/**
	 * 重置数据
	 */
	public void reset() {
		writer = new SerializeWriter();
		serializer = new JSONSerializer(writer);
		first = true;
	}
	
	/**
	 * 开始创建对象节点
	 * @param elementName
	 */
	public void startObject(String elementName) {
		if (!first) {
			append(C_COMMA);
		}
		append(C_QUOT).append(elementName.toCharArray()).append(C_QUOT).append(C_COLON).append(C_L_BRACE);
		first = true;
	}
	
	/**
	 * 开始创建对象节点
	 */
	public void startObject() {
		if(!first) {
			append(C_COMMA);
		}
		append(C_L_BRACE);
		first = true;
	}
	
	/**
	 * 结束对象节点
	 */
	public void endObject() {
		append(C_R_BRACE);
		first = false;
	}
	
	/**
	 * 开始创建数组节点
	 * @param elementName
	 */
	public void startArray(String elementName) {
		if (!first) {
			append(C_COMMA);
		}
		append(C_QUOT).append(elementName.toCharArray()).append(C_QUOT).append(C_COLON).append(C_L_BRACIKET);
		first = true;
	}
	
	/**
	 * 开始数组节点
	 */
	public void startArray() {
		if (!first) {
			append(C_COMMA);
		}
		append(C_L_BRACIKET);
		first = true;
	}
	
	/**
	 * 结束数组节点
	 */
	public void endArray() {
		append(C_R_BRACIKET);
		first = false;
	}
	
	/**
	 * 创建元素
	 * @param elementName
	 * @param o
	 */
	public void createElement(String elementName, Object o) {
		if (!first) {
			append(C_COMMA);
		}
		append(C_QUOT).append(elementName.toCharArray()).append(C_QUOT).append(C_COLON);
		createValue(o);
		first = false;
	}
	
	/**
	 * 创建元素
	 * @param o
	 */
	public void createElement(Object o) {
		if (!first) {
			append(C_COMMA);
		}
		createValue(o);
		first = false;
	}
	
	/**
	 * 写入对象
	 * @param o
	 */
	private void createValue(Object o) {
		serializer.write(o);
	}
	
	/**
	 * 写入json数据
	 * @param json
	 */
	public void appendJson(byte[] json) {
		if (!first) {
			append(C_COMMA);
		}
		append(json);
		first = false;
	}
	
	/**
	 * 写入json数据
	 * @param elementName
	 * @param json
	 */
	public void appendJson(String elementName, byte[] json) {
		if (!first) {
			append(C_COMMA);
		}
		append(C_QUOT).append(elementName.toCharArray()).append(C_QUOT).append(C_COLON);
		append(json);
		first = false;
	}

	/**
	 * 往输入流中写入数据
	 * @param cs
	 * @return
	 */
	private JsonDocument append(final char[] cs) {
		try {
			writer.write(cs);
			return this;
		} catch (Throwable t) {
			throw new RuntimeException("", t);
		}
	}
	
	/**
	 * 往输入流中写入数据
	 * @param bs
	 * @return
	 */
	private JsonDocument append(final byte[] bs) {
		try {
			writer.writeByteArray(bs);
			return this;
		} catch (Throwable t) {
			throw new RuntimeException("", t);
		}
	}
	
	/* 
	 * 获得字符串返回
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new String(toByte());
	}
	
	/**
	 * 获得字节返回
	 * @return
	 */
	public byte[] toByte() {
		return writer.toBytes("utf-8");
	}
	
	
	public static void main(String[] args) {
		JsonDocument doc = new JsonDocument();
		doc.startObject("haha");
		doc.createElement("a");
		doc.endObject();
		
		System.out.println(doc.toString());
	}
}
