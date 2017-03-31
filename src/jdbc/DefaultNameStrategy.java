package jdbc;

/**
 * 默认名称转换策略
 * @author huangliy
 * @version 1.0.0.0 2017年3月29日下午5:55:40
 */
public class DefaultNameStrategy implements NameStrategy{
	
	/* 
	 * @see jdbc.NameStrategy#columnNameToPropertyName(java.lang.String)
	 */
	@Override
	public String columnNameToPropertyName(String columnName) {
		StringBuilder sb = new StringBuilder();
		boolean capital = false;
		
		for (int i = 0; i < columnName.length(); i++) {
			char ch = columnName.charAt(i);
			if (ch == '_') {
				capital = true;
				continue;
			}
			if (capital) {
				sb.append(Character.toUpperCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/* 
	 * @see jdbc.NameStrategy#propertyNameToColunmnName(java.lang.String)
	 */
	@Override
	public String propertyNameToColunmnName(String propertyName) {
		StringBuilder sb = new StringBuilder();
		boolean capital = false;
		boolean first = true;
		
		for (int i = 0; i < propertyName.length(); i++) {
			char ch = propertyName.charAt(i);
			if (ch >= 'A' && ch <= 'Z') {
				capital = true;
			}
			
			if (capital && ! first) {
				sb.append("_").append(Character.toLowerCase(ch));
			} else if (capital) {
				first = false;
				capital = false;
				sb.append(Character.toLowerCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

}
