package app.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class CommunicationTabController {
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
		// Add observable list data to the table
		//personTable.setItems(mainApp.getPersonData());
	}
	
	/**
	 * Called when the user clicks on the send button.
	 */
	@FXML
	public void handleMouseClickOnSend(MouseEvent arg0) {
		String text = this.writingArea.getText();
		if (text != null) {
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
}
