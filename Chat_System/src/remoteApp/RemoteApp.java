package remoteApp;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import messages.Message;

public class RemoteApp {
	private InetAddress ip;
	private String nickname;
	private ArrayList<Message> listeMessage;
	// un seul objet pour tous les types d'écouteurs
    private final EventListenerList listeners = new EventListenerList();

	public void addNewMessageNormalReceivedListener(NewMessageNormalReceivedListener listener) {
        this.listeners.add(NewMessageNormalReceivedListener.class, listener);
    }

    public void removeNewMessageNormalReceivedListener(NewMessageNormalReceivedListener listener) {
        this.listeners.remove(NewMessageNormalReceivedListener.class, listener);
    }

    public NewMessageNormalReceivedListener[] getNewMessageNormalReceivedListeners() {
        return listeners.getListeners(NewMessageNormalReceivedListener.class);
    }

    public void nouveauMessageAjoute(Message message) {
		for(NewMessageNormalReceivedListener listener : getNewMessageNormalReceivedListeners()) {
            listener.thisNewNormalMessageHasBeenReceived(this, message);
        }
	}

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
