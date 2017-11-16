package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static java.lang.Thread.sleep;

/**
 * Created by Master Faster on 15.11.2017.
 */
public class ReadMessageThread extends Thread {

    private MessageWindowController messageWindowController;

    public ReadMessageThread(){
    }

    @Override
    public void run() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            this.messageWindowController = fxmlLoader.getController();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        Socket socket = ServerSingleton.getServerSingleton().getSocket();
        InputStream is = null;
        System.out.println("Reading message thread is running");
        String serverMessage="";
        while (true) {
            byte[] buffer = new byte[100];
            int byteNumber = ServerSingleton.getServerSingleton().getMessage(buffer);
            //historyTextField.appendText("\n" + serverMessage);
            //System.out.println(byteNumber);
            if(byteNumber != -1) {
                serverMessage = new String(buffer);
                System.out.println(serverMessage);
                messageWindowController.updateHistoryTextField("Serwer",serverMessage);
            }
        }
    }
}
