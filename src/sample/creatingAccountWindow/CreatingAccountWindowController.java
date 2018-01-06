package sample.creatingAccountWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ServerConnection.ServerSingleton;

import java.net.URL;
import java.util.ResourceBundle;


public class CreatingAccountWindowController implements Initializable{

    @FXML
    public TextField portTextField;
    @FXML
    public TextField ipTextField;
    @FXML
    public TextField loginTextField;
    @FXML
    public PasswordField psswdTextField;
    @FXML
    public PasswordField psswdSecTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        ipTextField.setFocusTraversable(false);
        portTextField.setFocusTraversable(false);
        ipTextField.setText("192.168.1.117");
        portTextField.setText("22580");
    }

    @FXML
    public void createAccount(){
        System.out.println("Creating account:");
        System.out.println("Login: " + loginTextField.getText());
        System.out.println("Password: " + psswdTextField.getText());

        if(psswdTextField.getText().equals(psswdSecTextField.getText()) && !loginTextField.getText().contains(";")
                && !psswdTextField.getText().contains(";") && !loginTextField.getText().contains("ą")
                && !loginTextField.getText().contains("ę") && !loginTextField.getText().contains("ź")
                && !loginTextField.getText().contains("ż") && !loginTextField.getText().contains("ó")
                && !loginTextField.getText().contains("ć") && !loginTextField.getText().contains("ś")
                && !loginTextField.getText().contains("ń") && !loginTextField.getText().contains("ł")) {
            ServerSingleton.getServerSingleton().createAccount(ipTextField.getText(),
                    Integer.parseInt(portTextField.getText()), loginTextField.getText(), psswdTextField.getText());
            Stage stage = (Stage) loginTextField.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Something went wrong");
            alert.setContentText("Passwords are different");
            alert.showAndWait();
        }
    }
}
