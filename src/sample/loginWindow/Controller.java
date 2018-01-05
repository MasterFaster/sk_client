package sample.loginWindow;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sample.ServerConnection.ServerSingleton;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    public TextField portTextField;
    @FXML
    public TextField ipTextField;
    @FXML
    public TextField loginTextField;
    @FXML
    public TextField psswdTextField;

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
            System.out.println("Trying to connect...");
            ServerSingleton serverSingleton = ServerSingleton.getServerSingleton();
            try{
                serverSingleton.createConnection(ipTextField.getText(),Integer.parseInt(portTextField.getText()), loginTextField.getText(), psswdTextField.getText());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../conversationSelectWindow/conversationSelect.fxml"));
                Parent root = (Parent)fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle(loginTextField.getText()+ " select conversation");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setResizable(true);
                stage.setScene(new Scene(root, 450, 600));
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        System.out.println("Stage is closing");
                        serverSingleton.closeSocket();
                        System.out.println("socket closed");
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
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root, 300, 275));
            stage.showAndWait();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

