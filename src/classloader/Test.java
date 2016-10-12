package classloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;

class Test {
	public static void main(String[] args) throws Throwable {
		String rootPath = Test.class.getResource(".").getFile().toString().replace("bin", "src");
		FileReader reader = new FileReader(rootPath + "ATest.java");
		BufferedReader br = new BufferedReader(reader);
		StringBuilder codeSb = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			codeSb.append(line);
		}
		String code = codeSb.toString();
		br.close();
		// 编译类
		LoaderUtil.compiler("ATest", code, rootPath);
		
		// 获取类
		Class<?> clazz = Class.forName("classloader.ATest");
		String mainMethod = "main";
		Method method = clazz.getMethod(mainMethod, new Class<?>[]{String[].class});
		// 执行方法
		method.invoke(null, new Object[]{null});
	}
}
