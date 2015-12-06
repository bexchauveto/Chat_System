package messages;

import remoteApp.RemoteApp;

public class MessageWithRemoteApp {

	private Message mess;
	private RemoteApp remoteUser;

	public MessageWithRemoteApp(Message mess, RemoteApp remote){
		this.setMess(mess);
		this.setRemoteUser(remote);
	}

	public RemoteApp getRemoteUser() {
		return remoteUser;
	}

	public void setRemoteUser(RemoteApp remoteUser) {
		this.remoteUser = remoteUser;
	}

	public Message getMess() {
		return mess;
	}

	public void setMess(Message mess) {
		this.mess = mess;
	}



}
