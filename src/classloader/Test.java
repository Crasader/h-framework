package classloader;

import java.io.FileReader;
import java.lang.reflect.Method;

class Test {
	public static void main(String[] args) throws Throwable {
		String rootPath = Test.class.getResource(".").getFile().toString().replace("bin", "src");
		System.out.println(rootPath);
		@SuppressWarnings("resource")
		FileReader reader = new FileReader(rootPath + "/ATest.java");
		String code = reader.toString();
		// 编译&载入类
		LoaderUtil.compiler("ATest", code, "com.ATest");
		
		// 获得类
		Class<?> clazz = Class.forName("com.ATest");
		String mainMethod = "main";
		Method method = clazz.getMethod(mainMethod, new Class<?>[]{String[].class});
		// 运行方法
		method.invoke(null, new Object[]{null});
	}
}
