package sample.loginWindow;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sample.AlertWindows.AlertWindow;
import sample.Conversation.Conversation;
import sample.Conversation.ConversationSingleton;
import sample.MessageWindow.MessageWindowSingleton;
import sample.ServerConnection.ServerSingleton;
import sample.conversationSelectWindow.ConversationSelectController;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
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
        ipTextField.setText("25.85.97.88");
        portTextField.setText("22580");
    }

    /**
     * action when "Connect" button is pressed
     */
    @FXML
    public void connectToServer() {
            //System.out.println("Trying to connect...");
            ServerSingleton serverSingleton = ServerSingleton.getServerSingleton();
            try{
                serverSingleton.createConnection(ipTextField.getText(),Integer.parseInt(portTextField.getText()), loginTextField.getText(), psswdTextField.getText());
                if(serverSingleton.getIsLogged()) {
                    ConversationSingleton.getConversationSingleton().addConversation(new Conversation("jackon"));
                    ConversationSingleton.getConversationSingleton().addConversation(new Conversation("kacol"));
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                            .getResource("sample/conversationSelectWindow/conversationSelect.fxml"));
                    ConversationSelectController conversationSelectController = fxmlLoader.getController();
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    Image icon = new Image("sample/conversationSelectWindow/selectFriendIcon.png");
                    stage.getIcons().add(icon);
                    stage.setTitle("Logged as: " + loginTextField.getText());
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setResizable(true);
                    Scene scene = new Scene(root, 400, 400);
                    scene.getStylesheets().add(getClass().getClassLoader().getResource("sample/conversationSelectWindow/conversationSelect.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            //System.out.println("Stage is closing");
                            serverSingleton.stopReadMessageThread();
                            serverSingleton.closeSocket();
                            //System.out.println("socket closed");
                            ConversationSingleton.getConversationSingleton().setConversationList(new ArrayList<Conversation>());
                            MessageWindowSingleton.getMessageWindowSingleton().closeMessageWindows();
                        }
                    });
                    serverSingleton.startReadMessageThread();
                    stage.showAndWait();
                }
            }catch(SocketTimeoutException ex){
                AlertWindow.showWarningWindow("Wrong port", "There is no host with such port and ip");
            }catch(NumberFormatException ex){
                AlertWindow.showWarningWindow("Wrong port", "Check if port is an integer");
            }catch(IllegalArgumentException ex){
                AlertWindow.showWarningWindow("Wrong port", "Port out of range");
            }catch(UnknownHostException ex){
                AlertWindow.showWarningWindow("Unknown Host", "This host is unreachable");
            }catch(Exception ex) {
                AlertWindow.showWarningWindow("WARNING", "Something went wrong, try again later");
                //System.out.println(ex);
            }
    }

    /**
     * action when "Create Account" button is pressed
     */
    @FXML
    public void createAccount(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample/creatingAccountWindow/creatingAccountWindow.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Creating new account");
            Image icon = new Image("sample/creatingAccountWindow/creatingAccountIcon.png");
            stage.getIcons().add(icon);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            Scene scene =new Scene(root, 300, 275);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("sample/creatingAccountWindow/creatingAccount.css").toExternalForm());
            stage.setScene(scene);
            stage.showAndWait();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

