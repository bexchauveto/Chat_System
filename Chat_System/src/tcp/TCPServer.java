package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class TCPServer implements Runnable {
	
	ServerSocket sockserv;
	ArrayList<Socket> listSock;

	public TCPServer() {
		try {
			this.sockserv = new ServerSocket(8045);
		} catch (IOException e) {
			System.err.println("ServerSocket couldn't be created");
			e.printStackTrace();
		}
		this.listSock = new ArrayList<Socket>();
		
	}

	@Override
	public void run() {
		while(true){
			try {
				this.listSock.add(this.sockserv.accept());
			} catch (IOException e) {
				System.err.println("Client socket couldn't be created");
				e.printStackTrace();
			}
		}
		
	}
	

}
