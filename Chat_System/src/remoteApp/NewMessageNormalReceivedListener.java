package remoteApp;

import java.util.EventListener;

public interface NewMessageNormalReceivedListener extends EventListener {
	void thisNewNormalMessageHasBeenReceived(RemoteApp ra, String message);
}
