package socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadHander implements Runnable {

private Socket socket;
	
	public ThreadHander(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		DataOutputStream dataoutputstream = null;
		DataInputStream datainputstream = null;
		DataInputStream localRead = null;
		
		try {
			dataoutputstream= new DataOutputStream(socket.getOutputStream());
			datainputstream= new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			String filepath;
		
		
			filepath = datainputstream.readUTF();
			File file = new File(filepath);
			
			if(file.exists()) {
				
				String filename = file.getName();
				dataoutputstream.writeUTF(filename);
				dataoutputstream.flush();
				
				long length = file.length();
				dataoutputstream.writeUTF(String.valueOf(length));
				dataoutputstream.flush();
				
				 System.out.println("开始向 "+Thread.currentThread().getName()+

	                        " 发送文件，文件名："+filename+"  文件大小"+length);
				 
				 localRead = new DataInputStream(
						 		new BufferedInputStream(
						 				new FileInputStream(file)));
				 byte[] bytes = new byte[1024*10];
				 int len = 0;
				 while((len=localRead.read(bytes))!=-1) {
					 dataoutputstream.write(bytes);
					 dataoutputstream.flush();
				 }
				 System.out.println("向 "+Thread.currentThread().getName()+" 发送文件完毕！");
			
			}else {
				return ;
			}
		
		
		
		} catch (IOException e) {

			e.printStackTrace();
		}finally {
			try {
				
				if(localRead!=null) {
					localRead.close();
				}
				
				if(dataoutputstream!=null) {
					dataoutputstream.close();
				}
				
				if(datainputstream!=null) {
					datainputstream.close();
				}

				if(socket!=null) {
					socket.close();
				}
							
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		


			

	}
}
