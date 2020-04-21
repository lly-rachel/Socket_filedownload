package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import socket.ThreadHander;

public class SocketServer {

	public static void main(String[] args){
		
		try {
			int port=9992;
			int i =0;
			ServerSocket serversocket = new ServerSocket(port);
			while(true) {
				Socket socket=serversocket.accept();
				System.out.println("waiting for request...");
				Thread thread = new Thread(new ThreadHander(socket), "Thread->"+i++);
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
