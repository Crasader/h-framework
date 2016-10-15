package util;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
			ZipOutputStream zop = new ZipOutputStream(bo);
			ZipEntry entry = new ZipEntry(String.valueOf(s.length));
			entry.setSize(s.length);
			entry.setMethod(ZipEntry.DEFLATED);
			zop.putNextEntry(entry);
			zop.write(s, 0, s.length);
			zop.flush();
			rtn = bo.toByteArray();
			zop.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return rtn;
	}
}
