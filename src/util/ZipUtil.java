package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterInputStream;
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
			s = content.getBytes();
			ByteArrayOutputStream bo = new ByteArrayOutputStream(s.length);
			DeflaterOutputStream out = new DeflaterOutputStream(bo);
			out.write(s, 0, s.length);
			out.finish();
			out.close();
			rtn = bo.toByteArray();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * 获得解压缩字节流
	 * @param content
	 * @return
	 */
	public static byte[] getUnzipString(byte[] content) {
		byte[] rtn = null;
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(content);
			DeflaterInputStream din = new DeflaterInputStream(in);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int i = -1;
			byte[] buff = new byte[4096];
			while((i = din.read(buff)) != -1) {
				out.write(buff, 0, i);
			}
			rtn = out.toByteArray();
			in.close();
			din.close();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	public static void main(String[] args) {
		String a = "haha你好";
		
		byte[] b = getZipString(a);
		b = getUnzipString(b);
		System.out.println(new String(b));
	}
}
