package util;

import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;

/**
 * @author huangliy
 * 压缩工具
 */
public class ZipUtil {
	/**
	 * 获得标准压缩字节流
	 */
	public static byte[] getZipString(String content) {
		byte[] s;
		byte[] rtn = null;
		try {
			s = content.getBytes("utf-8");
			ByteArrayOutputStream bo = new ByteArrayOutputStream(s.length);
			DeflaterOutputStream out = new DeflaterOutputStream(bo);
			out.write(s, 0, s.length);
			out.flush();
			rtn = bo.toByteArray();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return rtn;
	}
}
