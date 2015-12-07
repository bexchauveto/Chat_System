package remoteApp;

import java.util.EventListener;

import messages.Message;

public interface NewMessageNormalReceivedListener extends EventListener {
	void thisNewNormalMessageHasBeenReceived(RemoteApp ra, Message message);
}
