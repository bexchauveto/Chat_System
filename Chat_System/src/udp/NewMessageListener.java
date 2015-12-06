package udp;

import java.util.EventListener;

public interface NewMessageListener extends EventListener {
	void aMessageHasBeenReceived();
}
