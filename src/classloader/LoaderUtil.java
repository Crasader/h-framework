package classloader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 载入类工具
 */
public class LoaderUtil {
	/**
	 * 动态加载类
	 **/
	 public static boolean loadClass(String classPath) {
	 	//TODO ClassLoader.defineClass(className, byte[], start, length)
		 return false;
	 }

	 /**
	  * 编译代码
	 * @throws IOException 
	  */
	 public static void compiler(String className, String code, String classPath) throws IOException {
	 	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		ClassFileManager classManager = new ClassFileManager((StandardJavaFileManager) fileManager);

		JavaFileObject javaFileObject = new JavaSourceFromString(className, code);
		List<JavaFileObject> files = new ArrayList<JavaFileObject>();
		files.add(javaFileObject);

		List<String> options = new ArrayList<String>();
		options.add("-encoding");
		options.add("UTF-8");
		options.add("-classpath");
		options.add(classPath);
		options.add("-d");
		options.add("bin");

		JavaCompiler.CompilationTask task = compiler.getTask(null, classManager, diagnostics, options, null, files);
		boolean succ = task.call();

		classManager.close();

		if (succ) {
			// 编译成功，执行载入
			List<String> subClass = classManager.getSubJavaClassName();
			if (subClass != null) {
				for (String subClassName : subClass) {
					loadClass(subClassName);
				}
			}
			String mainClassName = classManager.getMainJavaClassName();
			loadClass(mainClassName);
		}
	 }
}
