package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{


    public TextField portTextField;
    public TextField ipTextField;
    public TextField loginTextField;
    public TextField psswdTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        ipTextField.setFocusTraversable(false);
        portTextField.setFocusTraversable(false);
        ipTextField.setText("192.168.1.100");
        portTextField.setText("22580");
    }

    public void connectToServer() {
            System.out.println("Trying to connect...");
            ServerSingleton serverSingleton = ServerSingleton.getServerSingleton();
            serverSingleton.createConnection(ipTextField.getText(),Integer.parseInt(portTextField.getText()), loginTextField.getText(), psswdTextField.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageWindow.fxml"));
            try{
                Parent root = (Parent)fxmlLoader.load();
                MessageWindowController messageWindowController = fxmlLoader.getController();
                messageWindowController.setFriendLogin("Serwer");
                Stage stage = new Stage();
                stage.setTitle("Messages");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setResizable(true);
                stage.setScene(new Scene(root, 450, 600));
                stage.showAndWait();
            }catch(Exception ex){
                System.out.println(ex);
            }
            serverSingleton.closeSocket();
            System.out.println("socket closed");
    }
}
