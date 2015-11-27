
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;

import messages.FileRequest;
import messages.FileRequestReply;
import messages.Message;
import messages.MessageBye;
import messages.MessageHello;
import messages.MessageNormal;
import messages.MessageWithIP;

import org.json.JSONObject;

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
			while (true) {
				this.socket.receive(messageReceived);
				String str = new String(messageReceived.getData(), 0, messageReceived.getLength(), Charset.forName("UTF-8")); 
				JSONObject jsonmes = new JSONObject(str);
				//System.out.println(jsonmes);
				typeMessageRecu = jsonmes.getInt("type");
				
				switch (typeMessageRecu) {
				case HELLOMESSAGERECEIVED:
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
				this.messagewithip[index] = new MessageWithIP(messageReceived.getAddress(), this.messageRecu);
				index = (index++)%10;
				this.setReceptionMessage(true);
				messageRecu.JSONToMessage(jsonmes);
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
