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
        ReignSendPack pack = new ReignSendPack("reconnect", "sessionId=heihei", true);
        bos.write(pack.getSendData());
        bos.flush();  

        // 接收服务器端数据  
        InputStream in = socket.getInputStream();  
        ReignBackPack rbp = new ReignBackPack(in, true);
        String content = rbp.getContent();
        System.out.println(content);
        
        bos.close();
        in.close();
        socket.close();
	}
}
