package sample.MessageWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    public TextArea historyTextField;
    public TextField msgTextField;
    private Socket socket;
    //private ReadMessageThread readMessageThread;
    private String friendLogin;
    private Conversation conversation;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        historyTextField.setEditable(false);
        socket = ServerSingleton.getServerSingleton().getSocket();
        try {
            socket.setSoTimeout(200);
            os = socket.getOutputStream();
            is = socket.getInputStream();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        readMsgs(); //???
//        ObservableList<Message> history = FXCollections.observableArrayList(conversation.getHistory());
//        history.addListener((ListChangeListener.Change<? extends Message> c) ->{
//            readMsgs();
//        });
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
            historyTextField.appendText("\n" +msg.getAuthor()+" " + msg.getMessage());
        }
    }

    public String getFriendLogin(){
        return friendLogin;
    }

    public void updateHistoryTextField(String author, String message){
        historyTextField.appendText("\n" + author + " " +message);
        //historyTextField.
        System.out.println("aktualizacja textFielda");
    }

    public void sendMsg() {
        ServerSingleton.getServerSingleton().sendMessage(msgTextField.getText(),friendLogin);
        historyTextField.appendText("\n"+"me " +msgTextField.getText());
        conversation.getHistory().add(new Message("me: ", msgTextField.getText()));
    }

    public void clearMsgs() {
        historyTextField.setText("");
    }

    public void addMsg(Message msg){
        historyTextField.appendText("\n" + msg.getAuthor() + ": " + msg.getMessage());
    }

    public void readMsgs() {
        try {
            //byte[] buffer = new byte[100];
            //int byteNumber = is.read(buffer);
            //String serverMessage = new String(buffer);
            for(Conversation conversation : ConversationSingleton.getConversationSingleton().getConversationList()){
                if(conversation.getFriendLogin().equals(friendLogin)){
                    for(Message msg : conversation.getHistory()){
                        historyTextField.appendText("\n" + msg.getAuthor() + ": " + msg.getMessage());
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void closeWindow(){
        Stage stage = (Stage) historyTextField.getScene().getWindow();
        //readMessageThread.stop();
        stage.close();
    }
}
