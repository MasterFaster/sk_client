package sample.AlertWindows;

import javafx.scene.control.Alert;

/**
 * Created by Master Faster on 07.01.2018.
 */
public class AlertWindow {

    public static void showWarningWindow(String header, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
