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

	private Message olderMessage(Message m1, Message m2){
		if(m1.getDateReception().isEqual(m2.getDateReception())){
			if(m1.getTimeReception().isBefore(m2.getTimeReception())){
				return m1;
			}
			else {
				return m2;
			}
		}
		else {
			if(m1.getDateReception().isBefore(m2.getDateReception())){
				return m1;
			}
			else {
				return m2;
			}
		}
	}

	public ArrayList<Message> getAllMessageConversation(RemoteApp remote){
		ArrayList<Message> conversation = new ArrayList<Message>();
		ArrayList<Message> messagesToRemote = this.getAllMessageFromRemoteUser(remote);
		int indexToRemote = 0;
		int indexFromRemote = 0;
		Message olderMess;
		while(!(indexToRemote > messagesToRemote.size()) && !(indexFromRemote > remote.getListeMessage().size())){
			olderMess = this.olderMessage(messagesToRemote.get(indexToRemote), remote.getListeMessage().get(indexFromRemote));
			if(olderMess.equals(messagesToRemote.get(indexToRemote))){
				indexToRemote++;
			}
			else {
				indexFromRemote++;
			}
			conversation.add(olderMess);
		}
		return conversation;
	}

}
