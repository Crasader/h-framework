package classloader;

public class HClassLoader extends ClassLoader{
	
	/**
	 * װ����
	 * @param className
	 * @param bs
	 */
	public void loadClass(String className, byte[] bs) {
		super.defineClass(className, bs, 0, bs.length);
	}
}
