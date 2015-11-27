package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

import messages.*;

import org.json.JSONObject;

public class UDPSender {
	
	private DatagramSocket socket;
	
	public UDPSender(){
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			System.err.println("Socket couldn't be created.");
			e.printStackTrace();
		}
	}
	
	private void sendMess(Message mes, InetAddress iptosend) {
		int port = 8045;
		byte[] buf = new byte[2048];
		JSONObject mesjson = mes.messageToJSON();
		buf = mesjson.toString().getBytes(Charset.forName("UTF-8"));
		DatagramPacket mestosend = new DatagramPacket(buf, buf.length, iptosend, port);
		//System.out.println(iptosend);
		try {
			this.socket.send(mestosend);
		} catch (IOException e) {
			System.err.println("Message failed to send");
			e.printStackTrace();
		}
	}
	
	public void sendHello(InetAddress iptosend, String nickName, boolean reqReply){
		Message mes = new MessageHello(nickName, reqReply);
		this.sendMess(mes, iptosend);
	}
	
	public void sendBye(InetAddress iptosend){
		Message mes = new MessageBye();
		this.sendMess(mes, iptosend);
	}
	
	public void sendMessageNormal(InetAddress iptosend, String message){
		Message mes = new MessageNormal(message);
		this.sendMess(mes, iptosend);
	}
	
	public void sendFileRequest(InetAddress iptosend, String name){
		Message mes = new FileRequest(name);
		this.sendMess(mes, iptosend);
	}
	
	public void sendFileRequestReply(InetAddress iptosend, boolean accept){
		Message mes = new FileRequestReply(accept);
		this.sendMess(mes, iptosend);
	}

}
