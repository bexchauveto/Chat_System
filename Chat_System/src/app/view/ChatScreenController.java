package app.view;

import java.io.IOException;
import java.util.ArrayList;

import app.MainApp;
import chatNI.RemoteAppsListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import messages.Message;
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
			if (((Label)tab.getGraphic()).getText().equals(name)) {
				ret = true;
			}
		}
		return ret;
	}
	
	public Tab getTabWithThisName(String name) {
		Tab ret = null;
		for (Tab tab : this.tabsList) {
			if (((Label)tab.getGraphic()).getText().equals(name)) {
				ret = tab;
			}
		}
		return ret;
	}
	
	public void ongletEnOrange(Tab t) {
		t.getStyleClass().add("tabstyle");
		this.tabPane.getStylesheets().add(getClass().getResource("NewTab.css").toExternalForm());
		System.out.println("Je passe l'onglet "+t.getText()+" en orange.");
	}
	
	public void ongletEnGris(Tab t) {
		t.getStyleClass().clear();
		t.getStyleClass().addAll("tab");
	}
	
	//Gère le clique sur la liste
	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		String name = this.usersList.getSelectionModel().getSelectedItem();
		if (name!=null && !name.equals("") && !aTabWithThisNameAlreadyExists(name)) {
			try {
				Tab t = new Tab();
				Label tabLabel = new Label(this.usersList.getSelectionModel().getSelectedItem());
				t.setGraphic(tabLabel);				
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
				
				/**
				 * On lui rajouter le fait que si on clique sur l'onglet il doit repaser en gris
				 */
				tabLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
				    @Override
				    public void handle(MouseEvent mouseEvent) {
				        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				        	ongletEnGris(t);
				       }
				    }
				});
				
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
		this.ongletEnGris(getTabWithThisName(name));
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
				Tab t = new Tab();
				System.out.println("J'ai reçu un message");
				//t.setText(ra.getNickname());
				Label tabLabel = new Label(ra.getNickname());
				t.setGraphic(tabLabel);
				
				this.ongletEnOrange(t);
				
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
				
				/**
				 * On lui rajouter le fait que si on clique sur l'onglet il doit repaser en gris
				 */
				tabLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
				    @Override
				    public void handle(MouseEvent mouseEvent) {
				        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				        	ongletEnGris(t);
				       }
				    }
				});
				
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
		else {
			Tab t = getTabWithThisName(ra.getNickname());
			// On ne passe en orange que si ce n'est pas l'onglet sélectionné
			if (!this.tabPane.getSelectionModel().getSelectedItem().equals(t)) {
				this.ongletEnOrange(t);
			}
		}
		
	}
	
	public String getUserNickName(){
		return this.mainApp.getUser().getNickname();
	}
}
