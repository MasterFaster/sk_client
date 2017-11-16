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
        ipTextField.setText("192.168.43.43");
        portTextField.setText("22580");
    }

    public void connectToServer() {
            System.out.println("Trying to connect...");
            ServerSingleton serverSingleton = ServerSingleton.getServerSingleton();
            try{
                serverSingleton.createConnection(ipTextField.getText(),Integer.parseInt(portTextField.getText()), loginTextField.getText(), psswdTextField.getText());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("conversationSelect.fxml"));
                Parent root = (Parent)fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Select conversation");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setResizable(true);
                stage.setScene(new Scene(root, 450, 600));
                stage.showAndWait();
                serverSingleton.closeSocket();
                System.out.println("socket closed");
            }catch(Exception ex) {
                System.out.println(ex);
            }
    }
}

