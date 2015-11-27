package messages;

import java.net.InetAddress;

public class MessageWithIP {
	
	private InetAddress ip;
	private Message message;
	
	public MessageWithIP (InetAddress ip, Message message){
		this.ip = ip;
		this.message = message;
	}

	public InetAddress getIp() {
		return ip;
	}

	public Message getMessage() {
		return message;
	}

}
