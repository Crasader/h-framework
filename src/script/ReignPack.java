package script;

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
		length += 4;
		this.length = getByte(length);
		this.command = extendByte(cByte, 32);
		this.data = b;
		this.orderId = getByte(requestId.incrementAndGet());
	}
	
	/**
	 * 扩充数组到指定长度
	 */
	private byte[] extendByte(byte[] b, int length) {
		byte[] rtn = new byte[length];
		for (int i = 0; i < b.length; i++) {
			rtn[i] = b[i];
		}
		return rtn;
	}

	/**
	 * 获得字节数组
	 */
	private byte[] getByte(int i) {
		byte[] rtn = new byte[4];
		rtn[0] = (byte) (i >> 24);
		rtn[1] = (byte) (i >> 16);
		rtn[2] = (byte) (i >> 8);
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
		rtn = mergeByte(rtn, data);
		rtn = mergeByte(rtn, orderId);
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
}
