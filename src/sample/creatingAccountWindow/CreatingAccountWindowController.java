package sample.creatingAccountWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.AlertWindows.AlertWindow;
import sample.ServerConnection.ServerSingleton;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
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
        ipTextField.setText("25.85.97.88");
        portTextField.setText("22580");
    }

    public boolean checkCorrectness(String text){
        if(!text.contains(";") && !text.contains("ą")
                && !text.contains("ę") && !text.contains("ź")
                && !text.contains("ż") && !text.contains("ó")
                && !text.contains("ć") && !text.contains("ś")
                && !text.contains("ń") && !text.contains("ł")){
            return true;
        }
        return false;
    }

    @FXML
    public void createAccount(){
        //System.out.println("Creating account:");
        //System.out.println("Login: " + loginTextField.getText());
        //System.out.println("Password: " + psswdTextField.getText());
        try {
            if (psswdTextField.getText().equals(psswdSecTextField.getText()) && checkCorrectness(psswdSecTextField.getText())
                    && checkCorrectness(loginTextField.getText())) {
                ServerSingleton.getServerSingleton().createAccount(ipTextField.getText(),
                        Integer.parseInt(portTextField.getText()), loginTextField.getText(), psswdTextField.getText());
                Stage stage = (Stage) loginTextField.getScene().getWindow();
                stage.close();
            } else {
                AlertWindow.showWarningWindow("Something went wrong", "Passwords are different or login/password contains not only english letters or digits");
            }
        }catch(NumberFormatException ex){
            AlertWindow.showWarningWindow("Wrong port", "Check if port is an integer");
        }catch(IllegalArgumentException ex){
            AlertWindow.showWarningWindow("Wrong port", "Port out of range");
        }catch(Exception ex) {
            AlertWindow.showWarningWindow("WARNING", "Something went wrong, try again later");
            //System.out.println(ex);
        }
    }
}
