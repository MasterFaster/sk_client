package sample.MessageWindow;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

/**
 * Created by Master Faster on 04.01.2018.
 */
public class MessageWindowSingleton {
    private final static MessageWindowSingleton messageWindowSingleton = new MessageWindowSingleton();
    ArrayList<MessageWindowController> messageWindowControllers = new ArrayList<>();

    public static MessageWindowSingleton getMessageWindowSingleton(){
        return messageWindowSingleton;
    }

    public ArrayList<MessageWindowController> getMessageWindowControllers(){
        return messageWindowControllers;
    }

    public void createMessageWindow(String friendLogin){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Creating new Message Window for: " + friendLogin);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageWindow.fxml"));
                try {
                    Parent root = (Parent) fxmlLoader.load();
                    MessageWindowController messageWindowController = fxmlLoader.getController();
                    messageWindowController.setFriendLogin(friendLogin);
                    messageWindowControllers.add(messageWindowController);
                    //readMessageThread.setConversationController(messageWindowController);
                    Stage stage = new Stage();
                    stage.setTitle("Messages");
                    //stage.initModality(Modality.APPLICATION_MODAL);
                    //stage.initStyle(StageStyle.UNDECORATED);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setResizable(true);
                    stage.setScene(new Scene(root, 450, 600));
                    stage.showAndWait();
                    messageWindowControllers.remove(messageWindowController);
                    System.out.println(stage);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }
}
