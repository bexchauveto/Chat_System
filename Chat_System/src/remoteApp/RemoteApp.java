package remoteApp;

import java.net.InetAddress;
import messages.*;

public class RemoteApp {
	private InetAddress ip;
	private String nickname;
	private ArrayList<Message> listeMessage;

	public RemoteApp(InetAddress ip, String nickname) {
		this.ip = ip;
		this.nickname = nickname;
		this.listeMessage =  new ArrayList<Message>();
	}

	public InetAddress getIp() {
		return ip;
	}

	public String getNickname() {
		return nickname;
	}

}
