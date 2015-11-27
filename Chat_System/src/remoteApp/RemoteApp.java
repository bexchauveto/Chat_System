package remoteApp;

import java.net.InetAddress;

public class RemoteApp {
	private InetAddress ip;
	private String nickname;
	
	public RemoteApp(InetAddress ip, String nickname) {
		this.ip = ip;
		this.nickname = nickname;
	}

	public InetAddress getIp() {
		return ip;
	}

	public String getNickname() {
		return nickname;
	}
	
}
