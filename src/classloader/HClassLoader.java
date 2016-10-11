package classloader;

/**
 * @author huangliy
 * 
 */
public class HClassLoader extends ClassLoader{
	
	/**
	 * 载入类
	 */
	public void loadClass(String className, byte[] bs) {
		super.defineClass(className, bs, 0, bs.length);
	}
}
