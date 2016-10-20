package script;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import util.ZipUtil;

/**
 * tcp包
 * @author huangliy
 * 
 */
public class ReignPack {
	/** 包序号 */
	private static AtomicInteger requestId = new AtomicInteger(1); 
	
	byte[] length;// 数据长度
	byte[] command; // 命令
	byte[] data;// 传输数据
	byte[] orderId;//序号
	
	/**
	 * 构造函数
	 * @param command
	 * @param content
	 * @param compress
	 */
	public ReignPack(String command, String content, boolean compress) {
		int length = 4 + 32;
		byte[] cByte = command.getBytes();
		byte[] b;
		if (compress) {
			b =ZipUtil.getZipString(content);
		} else {
			b = content.getBytes();
		}
		length = length + b.length;
		this.length = getByte(length);
		this.command = Arrays.copyOf(cByte, 32);
		this.data = b;
		this.orderId = getByte(requestId.incrementAndGet());
	}

	/**
	 * 获得字节数组
	 */
	private byte[] getByte(int i) {
		byte[] rtn = new byte[4];
		rtn[0] = (byte) (i >>> 24);
		rtn[1] = (byte) (i >>> 16);
		rtn[2] = (byte) (i >>> 8);
		rtn[3] = (byte) (i);
		return rtn;
	}
	
	/**
	 * 获得发送数据
	 */
	public byte[] getSendData() {
		byte[] rtn = new byte[0];
		rtn = mergeByte(rtn, length);
		rtn = mergeByte(rtn, command);
		rtn = mergeByte(rtn, orderId);
		rtn = mergeByte(rtn, data);
		return rtn;
	}

	/**
	 * 合并字节数组
	 */
	private byte[] mergeByte(byte[] array1, byte[] array2) {
		byte[] rtn = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, rtn, 0, array1.length);
		System.arraycopy(array2, 0, rtn, array1.length, array2.length);
		return rtn;
	}
	
	/**
	 * 翻译包返回
	 * @param rtn
	 * @return
	 */
	public static String parseRecv(byte[] rtn) {
		// 获取包长度
		System.out.println(Arrays.toString(rtn));
		int index = 0;
		byte[] b = Arrays.copyOfRange(rtn, index, index + 4);
		index += 4;
		int length = getInt(b);
		// 获取命令
		b = Arrays.copyOfRange(rtn, index, index + 32);
		index += 32;
		String command = new String(b).trim();
		b = Arrays.copyOfRange(rtn, index, index + 4);
		index += 4;
		int orderId = getInt(b);
		
		b = Arrays.copyOfRange(rtn, index, length + 4);
		System.out.println(Arrays.toString(b));
		b = ZipUtil.getUnzipString(b);
		System.out.println(Arrays.toString(b));
		String content = new String(b).trim();
		return length + command + orderId + content;
	}

	/**
	 * 获得int
	 * @param b
	 * @return
	 */
	private static int getInt(byte[] b) {
		return (b[0] << 24 | b[1] << 16 | b[2] << 8 | b[3]);
	}
}
