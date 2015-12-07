
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;

import javax.swing.event.EventListenerList;

import org.json.JSONObject;

import messages.FileRequest;
import messages.FileRequestReply;
import messages.Message;
import messages.MessageBye;
import messages.MessageHello;
import messages.MessageNormal;
import messages.MessageWithIP;

public class UDPReceiver implements Runnable {

	private DatagramSocket socket;
	private boolean receptionMessage = false;
	private Message messageRecu;
	private MessageWithIP[] messagewithip;
	private static final int HELLOMESSAGERECEIVED = 0;
	private static final int BYEMESSAGERECEIVED = 1;
	private static final int NORMALMESSAGERECEIVED = 2;
	private static final int FILEREQUESTMESSAGERECEIVED = 3;
	private static final int FILEREQUESTREPLYMESSAGERECEIVED = 4;


	// un seul objet pour tous les types d'écouteurs
    private final EventListenerList listeners = new EventListenerList();

	public void addNewMessageListener(NewMessageListener listener) {
        this.listeners.add(NewMessageListener.class, listener);
    }

    public void removeNewMessageListener(NewMessageListener listener) {
        this.listeners.remove(NewMessageListener.class, listener);
    }

    public NewMessageListener[] getNewMessageListeners() {
        return listeners.getListeners(NewMessageListener.class);
    }

    protected void newMessageReceived() {
		for(NewMessageListener listener : getNewMessageListeners()) {
            listener.aMessageHasBeenReceived();
        }
	}

	public UDPReceiver() {
		try {
			this.socket = new DatagramSocket(8045);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.messagewithip = new MessageWithIP[10];
	}

	public boolean isReceptionMessage() {
		return receptionMessage;
	}

	public void setReceptionMessage(boolean receptionMessage) {
		this.receptionMessage = receptionMessage;
	}

	public Message getMessageRecu() {
		this.receptionMessage = false;
		return this.messageRecu;
	}

	public MessageWithIP getMessagewithip(int index) {
		return messagewithip[index];
	}


	@Override
	public void run() {
		int typeMessageRecu = -1;
		byte[] buf = new byte[2048];
		int index = 0;
		try {
			DatagramPacket messageReceived = new DatagramPacket(buf, buf.length);
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.socket.receive(messageReceived);
				String str = new String(messageReceived.getData(), 0, messageReceived.getLength(), Charset.forName("UTF-8"));
				JSONObject jsonmes = new JSONObject(str);
				//System.out.println(jsonmes);
				typeMessageRecu = jsonmes.getInt("type");

				switch (typeMessageRecu) {
				case HELLOMESSAGERECEIVED:
					System.out.println("J'ai reçu un hello message dans UDPReceiver qui ressemble à ça : " + jsonmes);
					System.out.println("index du message dans le tableau : " + index);
					this.messageRecu = new MessageHello();
					break;
				case BYEMESSAGERECEIVED:
					this.messageRecu = new MessageBye();
					break;
				case NORMALMESSAGERECEIVED:
					this.messageRecu = new MessageNormal();
					break;
				case FILEREQUESTMESSAGERECEIVED:
					this.messageRecu = new FileRequest();
					break;
				case FILEREQUESTREPLYMESSAGERECEIVED:
					this.messageRecu = new FileRequestReply();
					break;
				default:
					this.messageRecu = null;
					break;
				}
				//System.out.println(this.socket.getInetAddress());
				messageRecu.JSONToMessage(jsonmes);
				this.messagewithip[index] = new MessageWithIP(messageReceived.getAddress(), this.messageRecu);
				index = (index+1)%10;
				this.setReceptionMessage(true);
				this.newMessageReceived();
				//System.out.println(messageRecu);
			}
		} catch (IOException e) {
			System.err.println("Erreur serveur UDP");
			e.printStackTrace();
		}
		finally {
			this.socket.close();
		}

	}







}
