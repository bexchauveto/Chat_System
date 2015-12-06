package app.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import messages.Message;
import messages.MessageNormal;
import remoteApp.RemoteApp;
import udp.NewMessageListener;

public class CommunicationTabController implements NewMessageListener {
	private RemoteApp remoteAppCorrespondant;
	@FXML
	private TextArea displayingArea;
	@FXML
	private TextArea writingArea;
	@FXML
	private Button sendButton;

	// Reference to the main application.
	private ChatScreenController chatScreenCtrlr;
	
	/**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CommunicationTabController() {
    	
    }

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setChatScreenCtrlr(ChatScreenController chatScreen) {
		this.chatScreenCtrlr = chatScreen;
	}
	
	public void setRemoteAppCorrespondant(RemoteApp ra) {
		this.remoteAppCorrespondant = ra;
	}
	
	/**
	 * Called when the user clicks on the send button.
	 */
	@FXML
	public void handleMouseClickOnSend(MouseEvent arg0) {
		String text = this.writingArea.getText();
		if (text != null) {
			this.sendMessage(text, this.remoteAppCorrespondant);
			String texteDejaAffiche = this.displayingArea.getText();
			if (texteDejaAffiche == null || texteDejaAffiche.equals("")) {
				this.displayingArea.setText(text);
			}
			else {
				this.displayingArea.setText(texteDejaAffiche + "\n" + text);
			}
			this.writingArea.setText(null);
		}
	}
	
	public void sendMessage(String mess, RemoteApp remote) {
		this.chatScreenCtrlr.sendMessage(mess, remote);
	}

	@Override
	public void aMessageHasBeenReceived() {
		ArrayList<Message> historique = this.chatScreenCtrlr.getAllMessageConversation(this.remoteAppCorrespondant);
		String text = ((MessageNormal)(historique.get(historique.size()))).getMessage();
		String texteDejaAffiche = this.displayingArea.getText();
		if (texteDejaAffiche == null || texteDejaAffiche.equals("")) {
			this.displayingArea.setText(text);
		}
		else {
			this.displayingArea.setText(texteDejaAffiche + "\n" + text);
		}
	}
	
}
