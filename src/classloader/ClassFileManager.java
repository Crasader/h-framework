package classloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

public class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
	// ����ӵ�е������б�
	private List<String> classNameList;

	/**
     * ���ط���
     */
	protected ClassFileManager(StandardJavaFileManager manager) {
		super(manager);
		this.classNameList = new ArrayList<String>();
	}

	/**
     * �������
     */
	public String getMainJavaClassName() {
		if (this.classNameList.isEmpty()) {
			return null;
		}
		return this.classNameList.get(this.classNameList.size() - 1);
	}

	/**
     * �������
     */
	public List<String> getSubJavaClassName() {
		if (this.classNameList.size() < 2) {
			return null;
		}
		return this.classNameList.subList(0, this.classNameList.size() - 1);
	}

	/* (non-Javadoc)
	 * @see javax.tools.ForwardingJavaFileManager#getJavaFileForOutput(javax.tools.JavaFileManager.Location, java.lang.String, javax.tools.JavaFileObject.Kind, javax.tools.FileObject)
	 */
	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling)
			throws IOException {
		this.classNameList.add(className);
		return super.getJavaFileForOutput(location, className, kind, sibling);
	}

}
