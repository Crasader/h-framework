package util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import log.InternalLoggerFactory;
import log.Logger;

/**
 * 资源扫描帮助类
 * @author huangliy
 * @version 1.0.0.0 2017年3月31日下午2:26:55
 */
public class Scans {
	private static final Logger log = InternalLoggerFactory.getLogger(Scans.class);
	
	/**
	 * 扫描指定路径下的类
	 * @param pack
	 * @return
	 * $Date: 2017年3月31日下午2:29:20
	 */
	public static Set<Class<?>> getClasses(String pack) {
		Set<Class<?>> result = new LinkedHashSet<>();
		// 是否迭代循环
		boolean recursive = true;
		// 将包名替换成路径
		String packageName = pack;
		String packageDirName = pack.replace(".", "/");
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					// 获取文件物理地址
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 扫描文件并添加到集合中
					findAndAddClasses(pack, filePath, recursive, result);
				} else if ("jar".equals(protocol)) {
					JarFile jar = ((JarURLConnection)url.openConnection()).getJarFile();
					Enumeration<JarEntry> entries = jar.entries();
					while(entries.hasMoreElements()) {
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						if (name.charAt(0) == '/') {
							name = name.substring(1);
						}
						if (name.startsWith(packageDirName)) {
							int index = name.lastIndexOf("/");
							if (index != -1) {
								packageName = name.substring(0, index).replace('/', '.');
							}
							if (index != -1 || recursive) {
								if (name.endsWith(".class") && !entry.isDirectory()) {
									String className = name.substring(packageName.length() + 1, name.length() - 6);
									result.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
								}
							}
						}
					}
				} else if ("class".equals(protocol)) {
					result.add(Thread.currentThread().getContextClassLoader().loadClass(url.getFile()));
				}
			}
		} catch (Throwable t) {
			log.error("", t);
		}
		return result;
	}

	/**
	 * 查找并添加类
	 * @param packageName
	 * @param filePath
	 * @param recursive
	 * @param result
	 * $Date: 2017年3月31日下午3:01:09
	 */
	private static void findAndAddClasses(String packageName, String filePath, boolean recursive, Set<Class<?>> result) {
		File dir = new File(filePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		
		File[] dirFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return ((recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
			}
		});
		
		for (File file : dirFiles) {
			if (file.isDirectory()) {
				findAndAddClasses(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, result);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					result.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
				} catch (ClassNotFoundException e) {
					log.error("", e);
				}
			}
		}
	}
}
