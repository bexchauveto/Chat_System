package tcp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient implements Runnable {
	
	private Socket sock;
	private String fileName;
	private InetAddress ipToSend;

	public TCPClient(String fileName, InetAddress ipToSend) {
		this.sock = new Socket();
		this.fileName = fileName;
		this.ipToSend = ipToSend;
	}

	@Override
	public void run() {
		try {
			this.sock.connect(new InetSocketAddress(this.ipToSend, 8045));
			int count;
			byte[] buffer = new byte[1024];

			OutputStream out = sock.getOutputStream();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(this.fileName)));
			while ((count = in.read(buffer)) >= 0) {
			     out.write(buffer, 0, count);
			     out.flush();
			}
			/*FileOutputStream fos = new FileOutputStream(new File(this.fileName));
			BufferedOutputStream out = new BufferedOutputStream(fos);
			byte[] buffer = new byte[1024];
			int count;
			InputStream in = sock.getInputStream();
			while((count=in.read(buffer)) >=0){
			    fos.write(buffer, 0, count);
			}
			fos.close();*/
			this.sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
