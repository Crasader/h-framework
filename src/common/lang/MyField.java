package common.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 自定义field类型
 * @author huangliy
 *
 */
public class MyField {
	public Field field;//field
	public String fieldName;// 名称
	public ClassType type;//类型
	public Method getter;//get方法
	public Method setter;//set方法
}
