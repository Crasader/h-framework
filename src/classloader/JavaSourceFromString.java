package classloader;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * @author huangliy
 *
 */
public class JavaSourceFromString extends SimpleJavaFileObject{
	private String code;
	
	public JavaSourceFromString(String className, String code) {
		super(URI.create("string:///" + className.replace(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
	}

	/* (non-Javadoc)
	 * @see javax.tools.SimpleJavaFileObject#getCharContent(boolean)
	 */
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return code;
	}

	
}
