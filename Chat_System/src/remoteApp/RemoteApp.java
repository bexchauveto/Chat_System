package remoteApp;

import java.net.InetAddress;
import java.util.ArrayList;

import messages.*;

public class RemoteApp {
	private InetAddress ip;
	private String nickname;
	private ArrayList<Message> listeMessage;

	public RemoteApp(InetAddress ip, String nickname) {
		this.ip = ip;
		this.nickname = nickname;
		this.setListeMessage(new ArrayList<Message>());
	}

	public InetAddress getIp() {
		return ip;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickName(String nickName){
		this.nickname = nickName;
	}

	public ArrayList<Message> getListeMessage() {
		return listeMessage;
	}

	public void setListeMessage(ArrayList<Message> listeMessage) {
		this.listeMessage = listeMessage;
	}

	@Override
	public String toString() {
		return "RemoteApp [ip=" + ip + ", nickname=" + nickname + ", listeMessage=" + listeMessage + "]";
	}



}
