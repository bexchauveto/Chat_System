package app.view;

import java.io.IOException;

import app.MainApp;
import chatNI.RemoteAppsListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import remoteApp.RemoteApp;

public class ChatScreenController implements RemoteAppsListener {
	private ObservableList<String> listePseudos = FXCollections.observableArrayList();
	@FXML
	private ListView<String> usersList;
	@FXML
	private TabPane tabPane;

	// Reference to the main application.
	private MainApp mainApp;

	/**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ChatScreenController() {
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
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		//personTable.setItems(mainApp.getPersonData());
	}

	@Override
	public void aUserHasConnected(RemoteApp user) {
		System.err.println(user.getNickname() + " s'est connecté");
		this.listePseudos.add(user.getNickname());
		this.usersList.setItems(listePseudos);
	}

	@Override
	public void aUserHasDisconnected(RemoteApp user) {
		this.listePseudos.remove(user.getNickname());
		this.usersList.setItems(listePseudos);
	}
	
	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		String name = this.usersList.getSelectionModel().getSelectedItem();
		if (name!=null && !name.equals("")) {
			try {
				Tab t = new Tab();
				t.setText(this.usersList.getSelectionModel().getSelectedItem());
				
				 FXMLLoader loader = new FXMLLoader();
		         loader.setLocation(MainApp.class.getResource("view/CommunicationTab.fxml"));
		         AnchorPane tabPane;
				tabPane = (AnchorPane) loader.load();
				
				t.setContent(tabPane);
				//t.setContent(new Rectangle(200,200, Color.LIGHTSTEELBLUE));
				this.tabPane.getTabs().add(t);
			    System.out.println("clicked on " + this.usersList.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				System.err.println("Could not load the communication tab FXML file !");
				e.printStackTrace();
			}
		}
	}
}
