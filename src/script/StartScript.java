package script;

import java.io.DataInputStream;
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
        bos.writeUTF("player@getPlayerInfo");  
        bos.flush();  

        // 接收服务器端数据  
        InputStream is = socket.getInputStream();  
        DataInputStream dis = new DataInputStream(is);  
        System.out.println(dis.readUTF());  
        
        bos.close();
        is.close();
        socket.close();
        
	}
}
