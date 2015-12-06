package app;
import java.io.IOException;
import java.util.ArrayList;

import app.view.ChatScreenController;
import app.view.ConnectionScreenController;
import chatNI.ChatNI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import messages.Message;
import remoteApp.RemoteApp;
import user.User;


public class MainApp extends Application {
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    private User user;
    private ChatNI chat;
    
	public void setUser(User user) {
		this.user = user;
	}

	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showConnectionScreen() {
        try {
            // Load connection overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ConnectionScreen.fxml"));
            GridPane connectionScreen = (GridPane) loader.load();

			 // Give the controller access to the main app.
		    ConnectionScreenController controller = loader.getController();
		    controller.setMainApp(this);
			    
			 // Set person overview into the center of root layout.
			rootLayout.setCenter(connectionScreen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showChatScreen() {
        try {
            // Load connection overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ChatScreen.fxml"));
            SplitPane chatScreen = (SplitPane) loader.load();
            
			 // Give the controller access to the main app.
		    ChatScreenController controller = loader.getController();
		    controller.setMainApp(this);
			    
			 // Set person overview into the center of root layout.
			rootLayout.setCenter(chatScreen);

			this.chat.addRemoteAppsListener(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chat System - Olivier et Dorian");

        this.initRootLayout();
        this.showConnectionScreen();
    }
    
    @Override
    public void stop() {
    	this.chat.disconnect();
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//byte [] bc = {(byte)255,(byte)255,(byte)255,(byte)255};//Broadcast
		//byte [] ip = {(byte)10,(byte)1,(byte)5,(byte)89};//Ip To Send
		/*UDPSender c = new UDPSender();
		new Thread((new UDPReceiver())).start();
		try {
			Thread.currentThread().sleep(100);
			c.sendHello(InetAddress.getByName("localhost"), "Georges", true);
			//c.sendHello(InetAddress.getByAddress(ip), "Georges");
			
			c.sendMessageNormal(InetAddress.getByName("localhost"), "Coucou ! Tu veux voir mon fichier ?");
			Thread.currentThread().sleep(1000);
			
			c.sendFileRequest(InetAddress.getByName("localhost"), "penis.png");
			Thread.currentThread().sleep(1000);
			
			c.sendFileRequestReply(InetAddress.getByName("localhost"), true);
			
			
			Thread.currentThread().sleep(2000);
			c.sendBye(InetAddress.getByName("localhost"));
			//c.sendBye(InetAddress.getByAddress(ip), "Georges");
		} catch (UnknownHostException e) {
			System.out.println("Destination not found.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Could not sleep.");
			e.printStackTrace();
		}*/
		
		
		
		launch(args);
	}
	
	// Appelé par handleConnection() dans ConnectionScreenController quand on appuie sur le bouton de connexion
	public void connect() {
		this.chat = new ChatNI(this.user);
		this.showChatScreen();
		
		//Simulation de connection et déconnection pour l'affichage de la liste
		/*ArrayList<RemoteApp> lol = new ArrayList<RemoteApp>();
		try {
			lol.add(new RemoteApp(InetAddress.getByName("localhost"), "Georges"));
			this.chat.setRemoteUsers(lol);
			
			/*
			 * Permet de voir que ça n'apparait pas dans la liste (bon meêm thread donc on le vois pas en direct mais osef
			 * Thread.sleep(3000);
			this.chat.removeRemoteUsers();*/
		/*} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void sendMessage(String mess, RemoteApp remote) {
		this.chat.sendMessage(mess, remote);
	}
	
	public ArrayList<Message> getAllMessageConversation(RemoteApp remote) {
		return this.user.getAllMessageConversation(remote);
	}
}
