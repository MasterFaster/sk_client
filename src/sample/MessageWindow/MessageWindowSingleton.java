package sample.MessageWindow;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    public void createMessageWindow(String friendLogin) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Creating new Message Window for: " + friendLogin);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample/MessageWindow/MessageWindow.fxml"));
                try {
                    Parent root = (Parent) fxmlLoader.load();
                    MessageWindowController messageWindowController = fxmlLoader.getController();
                    messageWindowController.setFriendLogin(friendLogin);
                    messageWindowController.friendNameLabel.setText(friendLogin);
                    messageWindowControllers.add(messageWindowController);
                    //readMessageThread.setConversationController(messageWindowController);
                    Stage stage = new Stage();
                    stage.setTitle("Messages");
                    Image icon = new Image("sample/MessageWindow/conversationIcon.png");
                    stage.getIcons().add(icon);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setResizable(true);
                    Scene scene = new Scene(root, 300, 350);
                    scene.getStylesheets().add(getClass().getClassLoader().getResource("sample/MessageWindow/messageWindow.css").toExternalForm());
                    stage.setScene(scene);
                    stage.showAndWait();
                    messageWindowControllers.remove(messageWindowController);
                    System.out.println(stage);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }
    public void closeMessageWindows() {
        for(MessageWindowController controller : messageWindowControllers){
            controller.closeWindow();
        }
    }

}
