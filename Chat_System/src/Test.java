import user.User;
import chatNI.ChatNI;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//byte [] bc = {(byte)255,(byte)255,(byte)255,(byte)255};//Broadcast
		//byte [] ip = {(byte)10,(byte)1,(byte)5,(byte)89};//Ip To Send
		/*UDPSender c = new UDPSender();
		new Thread((new UDPReceiver())).start();
		try {
			Thread.currentThread().sleep(100);
			c.sendHello(InetAddress.getByName("localhost"), "Georges", true);
			//c.sendHello(InetAddress.getByAddress(ip), "Georges");
			
			c.sendMessageNormal(InetAddress.getByName("localhost"), "Coucou ! Tu veux voir mon fichier ?");
			Thread.currentThread().sleep(1000);
			
			c.sendFileRequest(InetAddress.getByName("localhost"), "penis.png");
			Thread.currentThread().sleep(1000);
			
			c.sendFileRequestReply(InetAddress.getByName("localhost"), true);
			
			
			Thread.currentThread().sleep(2000);
			c.sendBye(InetAddress.getByName("localhost"));
			//c.sendBye(InetAddress.getByAddress(ip), "Georges");
		} catch (UnknownHostException e) {
			System.out.println("Destination not found.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Could not sleep.");
			e.printStackTrace();
		}*/
		
		User user = new User("Régis");
		ChatNI chat = new ChatNI(user);
	}

}
