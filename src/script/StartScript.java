package script;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author huangliy
 * 启动脚本
 */
public class StartScript {
	public static void main(String[] args) throws Throwable {
		Socket socket = new Socket("106.75.6.79", 8210);
		
		 // 向服务器端发送数据  
        OutputStream os =  socket.getOutputStream();  
        DataOutputStream bos = new DataOutputStream(os);  
        // 创建一个数据包
        ReignPack pack = new ReignPack("reconnect", "sessionId=heihei", true);
        bos.write(pack.getSendData());
        bos.flush();  

        // 接收服务器端数据  
        InputStream in = socket.getInputStream();  
        byte[] back = new byte[1000];
        in.read(back);
        System.out.println(ReignPack.parseRecv(back));  
        
        bos.close();
        in.close();
        socket.close();
	}
}
