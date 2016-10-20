package script;

import java.io.InputStream;
import java.util.Arrays;

import log.InternalLoggerFactory;
import log.Logger;
import util.ZipUtil;

/**
 * tcp包
 * @author huangliy
 * 
 */
public class ReignBackPack {
	public static Logger log = InternalLoggerFactory.getLogger(ReignBackPack.class);
	
	private int length; // 包长度
	private String command; // 命令
	private int orderId; // 序号
	private String content; //内容
	
	/**
	 * 构造函数
	 * @param command
	 * @param content
	 * @param compress
	 */
	public ReignBackPack(InputStream in, boolean compress) {
		// 首先读取4字节解析包长度
		byte[] b = new byte[4];
		try {
			in.read(b, 0, 4);
			this.length = getInt(b);
			b = new byte[this.length];
			in.read(b, 0, this.length);
		} catch (Throwable t) {
			log.error("read byte error", t);
		}
		int index = 0;
		byte[] t;
		// 获取命令
		t = Arrays.copyOfRange(b, index, index + 32);
		index += 32;
		this.command = new String(t).trim();
		t = Arrays.copyOfRange(b, index, index + 4);
		index += 4;
		this.orderId = getInt(t);
		
		t = Arrays.copyOfRange(b, index, b.length);
		if (compress) {
			t = ZipUtil.getUnzipString(t);
		}
		this.content = new String(t).trim();
	}
	

	/**
	 * 活的命令
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * 获得包序号
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * 获得返回内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 获得int
	 * @param b
	 * @return
	 */
	private int getInt(byte[] b) {
		return (b[0] << 24 | b[1] << 16 | b[2] << 8 | b[3]);
	}
}
