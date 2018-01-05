package sample.creatingAccountWindow;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ServerConnection.ServerSingleton;


public class CreatingAccountWindowController {

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


    @FXML
    public void createAccount(){
        System.out.println("Creating account:");
        System.out.println("Login: " + loginTextField.getText());
        System.out.println("Password: " + psswdTextField.getText());

        if(psswdTextField.getText().equals(psswdSecTextField.getText())) {
            ServerSingleton.getServerSingleton().createAccount(portTextField.getText(),
                    Integer.parseInt(ipTextField.getText()), loginTextField.getText(), psswdTextField.getText());
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
