package sample.MessageWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Conversation.Conversation;
import sample.Conversation.ConversationSingleton;
import sample.Conversation.Message;
import sample.ServerConnection.ServerSingleton;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Master Faster on 09.11.2017.
 */
public class MessageWindowController implements Initializable{

    private OutputStream os;
    private InputStream is;
    @FXML
    public Label friendNameLabel;
    @FXML
    public TextArea historyTextField;
    @FXML
    public TextField msgTextField;
    private Socket socket;
    //private ReadMessageThread readMessageThread;
    private String friendLogin;
    private Conversation conversation;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        historyTextField.setEditable(false);
        historyTextField.setFocusTraversable(false);
        socket = ServerSingleton.getServerSingleton().getSocket();
        try {
            socket.setSoTimeout(600);
            os = socket.getOutputStream();
            is = socket.getInputStream();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void setFriendLogin(String friendLogin){
        this.friendLogin = friendLogin;
        for(Conversation con : ConversationSingleton.getConversationSingleton().getConversationList()){
            if(con.getFriendLogin().equals(friendLogin)){
                conversation = con;
                break;
            }
        }
        for(Message msg : conversation.getHistory()){
            historyTextField.appendText(msg.getAuthor()+": " + msg.getMessage() + "\n");
        }
    }

    public String getFriendLogin(){
        return friendLogin;
    }


    public void sendMsg() {
        if(!msgTextField.getText().equals("")) {
            ServerSingleton.getServerSingleton().sendMessage(msgTextField.getText(), friendLogin);
            historyTextField.appendText("me: " + msgTextField.getText() + "\n");
            conversation.getHistory().add(new Message("me", msgTextField.getText()));
            msgTextField.setText("");
        }
    }

    public void clearMsgs() {
        historyTextField.setText("");
    }

    public void addMsg(Message msg){
        historyTextField.appendText(msg.getAuthor() + ": " + msg.getMessage() + "\n");
    }

    public void closeWindow(){
        Stage stage = (Stage) historyTextField.getScene().getWindow();
        stage.close();
    }

}
