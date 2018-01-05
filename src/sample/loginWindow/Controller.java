package sample.loginWindow;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sample.Conversation.Conversation;
import sample.Conversation.ConversationSingleton;
import sample.MessageWindow.MessageWindowSingleton;
import sample.ServerConnection.ServerSingleton;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    public TextField portTextField;
    @FXML
    public TextField ipTextField;
    @FXML
    public TextField loginTextField;
    @FXML
    public PasswordField psswdTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        ipTextField.setFocusTraversable(false);
        portTextField.setFocusTraversable(false);
        ipTextField.setText("192.168.1.117");
        portTextField.setText("22580");
    }

    /**
     * action when "Connect" button is pressed
     */
    @FXML
    public void connectToServer() {
            System.out.println("Trying to connect...");
            ServerSingleton serverSingleton = ServerSingleton.getServerSingleton();
            ConversationSingleton.getConversationSingleton().addConversation(new Conversation("Serwer"));
            ConversationSingleton.getConversationSingleton().addConversation(new Conversation("jackon"));
            ConversationSingleton.getConversationSingleton().addConversation(new Conversation("kacol"));
            ConversationSingleton.getConversationSingleton().addConversation(new Conversation("elo"));
            try{
                serverSingleton.createConnection(ipTextField.getText(),Integer.parseInt(portTextField.getText()), loginTextField.getText(), psswdTextField.getText());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../conversationSelectWindow/conversationSelect.fxml"));
                Parent root = (Parent)fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle(loginTextField.getText()+ " select conversation");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setResizable(true);
                Scene scene = new Scene(root, 400, 400);
                scene.getStylesheets().add(getClass().getResource("../conversationSelectWindow/conversationSelect.css").toExternalForm());
                stage.setScene(scene);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        System.out.println("Stage is closing");
                        serverSingleton.closeSocket();
                        System.out.println("socket closed");
                        ConversationSingleton.getConversationSingleton().setConversationList(new ArrayList<Conversation>());
                        MessageWindowSingleton.getMessageWindowSingleton().closeMessageWindows();
                    }
                });
                stage.showAndWait();
            }catch(Exception ex) {
                System.out.println(ex);
            }
    }

    /**
     * action when "Create Account" button is pressed
     */
    @FXML
    public void createAccount(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../creatingAccountWindow/creatingAccountWindow.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Creating new account");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            Scene scene =new Scene(root, 300, 275);
            scene.getStylesheets().add(getClass().getResource("../creatingAccountWindow/creatingAccount.css").toExternalForm());
            stage.setScene(scene);
            stage.showAndWait();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

