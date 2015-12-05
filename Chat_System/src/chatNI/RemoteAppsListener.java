package chatNI;

import java.util.EventListener;

import remoteApp.RemoteApp;

public interface RemoteAppsListener extends EventListener {
	void aUserHasConnected(RemoteApp user);
	void aUserHasDisconnected(RemoteApp user);
}
