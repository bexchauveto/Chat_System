package chatNI;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import messages.MessageHello;
import messages.MessageNormal;
import messages.MessageWithIP;
import remoteApp.RemoteApp;
import tcp.TCPServer;
import udp.NewMessageListener;
import udp.UDPReceiver;
import udp.UDPSender;
import user.User;

public class ChatNI implements Runnable, NewMessageListener {
	private boolean messageReceived;
	private UDPSender udpSender;
	private UDPReceiver udpReceiver;
	private TCPServer tcpServer;
	private User user;
	private ArrayList<RemoteApp> remoteUsers;
	private static final int HELLOMESSAGERECEIVED = 0;
	private static final int BYEMESSAGERECEIVED = 1;
	private static final int NORMALMESSAGERECEIVED = 2;
	private static final int FILEREQUESTMESSAGERECEIVED = 3;
	private static final int FILEREQUESTREPLYMESSAGERECEIVED = 4;
	// un seul objet pour tous les types d'écouteurs
    private final EventListenerList listeners = new EventListenerList();
    
    // Pour la simulation d'affichagede la liste d'utilisateurs, à enlever par la suite
    /*public void setRemoteUsers(ArrayList<RemoteApp> remoteUsers) {
		this.remoteUsers = remoteUsers;
		this.oneMoreUser(remoteUsers.get(0));
	}
    public void removeRemoteUsers() {
		this.oneLessUser(remoteUsers.get(0));
	}*/
    //FIn simu

	public void addRemoteAppsListener(RemoteAppsListener listener) {
        this.listeners.add(RemoteAppsListener.class, listener);
    }
 
    public void removeRemoteAppsListener(RemoteAppsListener listener) {
        this.listeners.remove(RemoteAppsListener.class, listener);
    }
    
    public RemoteAppsListener[] getRemoteAppsListeners() {
        return listeners.getListeners(RemoteAppsListener.class);
    }
    
    protected void oneMoreUser(RemoteApp user) {
		for(RemoteAppsListener listener : getRemoteAppsListeners()) {
            listener.aUserHasConnected(user);
        }
	}
    
    protected void oneLessUser(RemoteApp user) {
		for(RemoteAppsListener listener : getRemoteAppsListeners()) {
            listener.aUserHasDisconnected(user);
        }
	}
	
	public ChatNI(User user) {
		this.user = user;
		this.remoteUsers = new ArrayList<RemoteApp>();
		this.messageReceived = false;
		this.connect();
		new Thread(this).start();
	}
			
	public void connect() {
		this.udpSender = new UDPSender();
		this.udpReceiver = new UDPReceiver();
		this.udpReceiver.addNewMessageListener(this);
		this.tcpServer = new TCPServer();
		new Thread(this.udpReceiver).start();
		new Thread(this.tcpServer).start();
		byte [] bc = {(byte)255,(byte)255,(byte)255,(byte)255};
		//byte [] ip = {(byte)192,(byte)168,(byte)43,(byte)177};
		try {
			this.udpSender.sendHello(InetAddress.getByAddress(bc), this.user.getNickname(), true);
			//this.udpSender.sendHello(InetAddress.getByName("localhost"), this.user.getNickname(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		for(int i = 0; i < this.remoteUsers.size(); i++) {
			this.udpSender.sendBye(this.remoteUsers.get(i).getIp());
		}
	}

	public int getIndexRemoteAppByIP(InetAddress ip) {
		int index = -1;
		for(int i = 0; i < this.remoteUsers.size(); i++) {
			if (this.remoteUsers.get(i).getIp().equals(ip)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	@Override
	public void run() {
		
		while(true) {
			//System.out.println(this.udpReceiver.isReceptionMessage());
			System.out.println("");
		}
	}
	
	
	MessageWithIP messageCourantWithIP;
	int indexMessageWithIP = 0;
	
	@Override
	public void aMessageHasBeenReceived() {
		//System.out.println(this.udpReceiver.isReceptionMessage());
		System.out.println("On lit le message à l'index : "+indexMessageWithIP);
		messageCourantWithIP = this.udpReceiver.getMessagewithip(indexMessageWithIP);
		this.udpReceiver.setReceptionMessage(false);
		//System.out.println(messageCourantWithIP.getIp());
		indexMessageWithIP = (indexMessageWithIP+1)%10;
		switch (messageCourantWithIP.getMessage().getType()) {
		case HELLOMESSAGERECEIVED:
			MessageHello messageHello = (MessageHello) messageCourantWithIP.getMessage();
			RemoteApp ra = new RemoteApp(messageCourantWithIP.getIp(), messageHello.getNickName());
			this.remoteUsers.add(ra);
			this.oneMoreUser(ra);
			System.out.println(this.remoteUsers);
			if (!messageHello.isReqReply()) {
				System.out.println(messageHello.getNickName() + " répond à ma connexion");
			}
			if (messageHello.isReqReply()) {
				System.out.println(messageHello.getNickName() + " vient de se connecter");
				this.udpSender.sendHello(messageCourantWithIP.getIp(), this.user.getNickname(), false);
			}
			break;
		case BYEMESSAGERECEIVED:
			int indice = this.getIndexRemoteAppByIP(messageCourantWithIP.getIp());
			if (indice != -1) {
				this.oneLessUser(this.remoteUsers.get(indice));
				this.remoteUsers.remove(indice);
			}
			else {
				//TODO Soit exception, soit message erreur sur le GUI
			}
			break;
		case NORMALMESSAGERECEIVED:
				//TODO L'afficher dans le GUI. On retrouvera le nickname de l'utilisateur sur lequel afficher le message à l'aide de remoteUsers.
				MessageNormal messageNormal = (MessageNormal) messageCourantWithIP.getMessage();
				System.out.println(messageNormal.getMessage());
			break;
		case FILEREQUESTMESSAGERECEIVED:
			// TODO Afficher la popup avec le nom du fichier demandant s'il accepte ou non le fichier à télécharger.
			break;
		case FILEREQUESTREPLYMESSAGERECEIVED:
			/* TODO
			 * if (message.acceptFile) {
			 * 	envoyer le fichier;
			 * }
			 */
			break;
		default:
			break;
		}
	}
}
