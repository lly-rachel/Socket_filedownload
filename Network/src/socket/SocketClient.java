package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClient {
	
public static final String FILEPATH="F:/ѧϰ�ܼ�/����Ө�������ܼ�.md";
	
	public static void main(String[] args) {
		

		
		Scanner in = new Scanner(System.in);
		String host="127.0.0.1";
		int port = 9992;
		
		Socket socket=null;
		DataInputStream datainputstream=null;     
		DataOutputStream dataoutputstream=null;   
		DataOutputStream localWrite=null;    
		
		
		try {
			socket = new Socket(host,port);
			
			 datainputstream = new DataInputStream(
					 new BufferedInputStream(socket.getInputStream())); 
			 
			 dataoutputstream = new DataOutputStream(
					 new BufferedOutputStream(socket.getOutputStream()));
			
			 System.out.println("���������صĵ�ַ��");
			 System.out.println(FILEPATH);
			 String filepath=in.next();
			 while(!filepath.equals(FILEPATH)) {
				 System.out.println("��������ȷ�ĵ�ַ��");
				 filepath=in.next();
			 }
			 
			 System.out.println(filepath);
			 dataoutputstream.writeUTF(filepath);
	         dataoutputstream.flush();
			 
	         String filename = datainputstream.readUTF();       
	         String length=datainputstream.readUTF();      
	         System.out.println("�ļ�����"+filename+"    �ļ���С��"+length);
	         
	         System.out.println("��������洢���ļ�����");
	         filename=in.next();
	         
	         System.out.println("�ļ�����"+filename+"    �ļ���С��"+length+"�Ѵ洢");
	         
	         localWrite=new DataOutputStream(
	        		 new BufferedOutputStream(
	        				 new FileOutputStream(filename)));
			 
	         byte[] bytes = new byte[1024*10];
			 int len = 0;
			 while((len=datainputstream.read(bytes))!=-1) {
				 localWrite.write(bytes);
				 localWrite.flush();
			 }
			  System.out.println("�ļ�������ϣ�");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(localWrite!=null) {
					localWrite.close();
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
