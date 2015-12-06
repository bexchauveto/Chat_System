package user;

import java.util.ArrayList;

import messages.Message;

import messages.MessageWithRemoteApp;
import remoteApp.RemoteApp;

public class User {
	private String nickname;
	private ArrayList<MessageWithRemoteApp> messageList;

	public User(String nickname) {
		this.nickname = nickname;
		this.setMessageList(new ArrayList<MessageWithRemoteApp>());
	}

	public String getNickname() {
		return nickname;
	}

	public ArrayList<MessageWithRemoteApp> getMessageList() {
		return messageList;
	}

	public void setMessageList(ArrayList<MessageWithRemoteApp> messageList) {
		this.messageList = messageList;
	}

	public ArrayList<Message> getAllMessageFromRemoteUser (RemoteApp remote){
		ArrayList<Message> messages = new ArrayList<Message>();
		for(int i = 0; i < this.messageList.size(); i++) {
			if(this.messageList.get(i).getRemoteUser().equals(remote)){
				messages.add(this.messageList.get(i).getMess());
			}
		}
		return messages;
	}

}
