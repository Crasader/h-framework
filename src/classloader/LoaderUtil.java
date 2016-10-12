package classloader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


/**
 * @author huangliy
 * 载入工具
 */
public class LoaderUtil {
	/**
	 * 载入类
	 * @throws IOException 
	 **/
	 public static boolean loadClass(String className, String filePath) throws IOException {
		 FileInputStream fis = null;
		 ByteArrayOutputStream bos = null;
		 int len = 0;
		 byte[] buff = new byte[1024];
		 byte[] bs = null;
		 try {
			fis = new FileInputStream(filePath);
			bos = new ByteArrayOutputStream();
			while ((len = fis.read(buff)) != -1) {
				bos.write(buff, 0, len);
			}
			bs = bos.toByteArray();
		 } finally {
			 if (null != fis) {
				 try {
					fis.close();
				 } catch (Exception e) {}
			 }
			 if (null != bos) {
				 try {
					 bos.close();
				 } catch (Exception e) {}
			 }
		 } 
		 if (bs == null) {
			 return false;
		 }
		 // 载入类
		 HClassLoader loader = new HClassLoader();
		 loader.loadClass(className, bs);
		 return true;
	 }

	 /**
	  * 编译&载入java文件
	 * @throws IOException 
	  */
	 public static void compiler(String className, String code, String sourcePath) throws IOException {
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
		options.add("-sourcepath");
		options.add(sourcePath);
		options.add("-d");
		options.add("bin");

		JavaCompiler.CompilationTask task = compiler.getTask(null, classManager, diagnostics, options, null, files);
		boolean succ = task.call();

		classManager.close();

		if (succ) {
			// 载入子类
			List<String> subClass = classManager.getSubJavaClassName();
			if (subClass != null) {
				for (String subClassName : subClass) {
					loadClass(subClassName, "./" + subClassName + Kind.CLASS.extension);
				}
			}
			String mainClassName = classManager.getMainJavaClassName();
			loadClass(mainClassName, "bin/" + mainClassName.replace(".", "/") + Kind.CLASS.extension);
		}
	 }
}
