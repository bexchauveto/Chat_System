package app.view;

import java.io.IOException;
import java.util.ArrayList;

import app.MainApp;
import chatNI.RemoteAppsListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import messages.Message;
import messages.MessageNormal;
import remoteApp.NewMessageNormalReceivedListener;
import remoteApp.RemoteApp;

public class ChatScreenController implements RemoteAppsListener, NewMessageNormalReceivedListener {
	private ObservableList<String> listePseudos = FXCollections.observableArrayList();
	private ArrayList<RemoteApp> remoteApps;
	private ArrayList<Tab> tabsList;
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
    	this.remoteApps = new ArrayList<RemoteApp>();
    	this.tabsList = new ArrayList<Tab>();
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
		user.addNewMessageNormalReceivedListener(this);
		this.remoteApps.add(user);
		System.err.println(user.getNickname() + " s'est connecté");
		
		
		ObservableList<String> tempObservableList = this.listePseudos;
		ListView<String> tempListView = this.usersList;
		Platform.runLater(new Runnable() {
            @Override public void run() {
            	tempObservableList.add(user.getNickname());
            	tempListView.setItems(tempObservableList);
            }
        });
		this.listePseudos = tempObservableList;
		this.usersList = tempListView;
	}

	@Override
	public void aUserHasDisconnected(RemoteApp user) {
		user.removeNewMessageNormalReceivedListener(this);
		this.remoteApps.remove(user);
		System.err.println(user.getNickname() + " s'est déconnecté");
		
		ObservableList<String> tempObservableList = this.listePseudos;
		ListView<String> tempListView = this.usersList;
		Platform.runLater(new Runnable() {
            @Override public void run() {
            	tempObservableList.remove(user.getNickname());
            	tempListView.setItems(tempObservableList);
            }
        });
		this.listePseudos = tempObservableList;
		this.usersList = tempListView;
	}
	
	public boolean aTabWithThisNameAlreadyExists(String name) {
		boolean ret = false;
		for (Tab tab : this.tabsList) {
			if (tab.getText().equals(name)) {
				ret = true;
			}
		}
		return ret;
	}
	
	public Tab getTabWithThisName(String name) {
		Tab ret = null;
		for (Tab tab : this.tabsList) {
			if (tab.getText().equals(name)) {
				ret = tab;
			}
		}
		return ret;
	}
	
	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		String name = this.usersList.getSelectionModel().getSelectedItem();
		if (name!=null && !name.equals("") && !aTabWithThisNameAlreadyExists(name)) {
			try {
				Tab t = new Tab();
				t.setText(this.usersList.getSelectionModel().getSelectedItem());
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/CommunicationTab.fxml"));
				AnchorPane anchorPane;
				anchorPane = (AnchorPane) loader.load();
				
				// Give the controller access to the chat screen controller.
				CommunicationTabController controller = loader.getController();
				
				//On utilise le fait que les deux array list aient la même indexation pseudo/remoteApp
				for (int i = 0; i<this.listePseudos.size();i++) {
					if (this.listePseudos.get(i).equals(name)) {
						controller.setRemoteAppCorrespondant(this.remoteApps.get(i));
					}
				}
			    
			    controller.setChatScreenCtrlr(this);
				
				t.setContent(anchorPane);
				this.tabsList.add(t);
				
				TabPane tempTabPane = this.tabPane;
				
				Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                	 tempTabPane.getTabs().add(t);
	                 }
	             });
				//t.setContent(new Rectangle(200,200, Color.LIGHTSTEELBLUE));
				this.tabPane = tempTabPane;
			    System.out.println("clicked on " + this.usersList.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				System.err.println("Could not load the communication tab FXML file !");
				e.printStackTrace();
			}
		}
		this.tabPane.getSelectionModel().select(getTabWithThisName(name));
	}
	
	public void sendMessage(String mess, RemoteApp remote) {
		this.mainApp.sendMessage(mess, remote);
	}
	
	public ArrayList<Message> getAllMessageConversation(RemoteApp remote) {
		return this.mainApp.getAllMessageConversation(remote);
	}

	@Override
	public void thisNewNormalMessageHasBeenReceived(RemoteApp ra, Message message) {
		if (!this.aTabWithThisNameAlreadyExists(ra.getNickname())) {
			try {
				System.out.println("J'ai reçu un message");
				Tab t = new Tab();
				t.setText(ra.getNickname());
				
				FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(MainApp.class.getResource("view/CommunicationTab.fxml"));
		        AnchorPane anchorPane;
		        anchorPane = (AnchorPane) loader.load();
				
				// Give the controller access to the chat screen controller.
			    CommunicationTabController controller = loader.getController();
			    
			    controller.setRemoteAppCorrespondant(ra);
			    controller.setChatScreenCtrlr(this);
			    controller.afficherPremierMessage(message);
				
				t.setContent(anchorPane);
				this.tabsList.add(t);
				
				
				TabPane tempTabPane = this.tabPane;
				
				Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                	 tempTabPane.getTabs().add(t);
	                 }
	             });
				//t.setContent(new Rectangle(200,200, Color.LIGHTSTEELBLUE));
				this.tabPane = tempTabPane;
			    System.out.println("clicked on " + this.usersList.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				System.err.println("Could not load the communication tab FXML file !");
				e.printStackTrace();
			}
		}
		
	}
	
	public String getUserNickName(){
		return this.mainApp.getUser().getNickname();
	}
}
