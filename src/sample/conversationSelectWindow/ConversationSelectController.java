package sample.conversationSelectWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Conversation.Conversation;
import sample.Conversation.ConversationSingleton;
import sample.MessageWindow.MessageWindowSingleton;
import sample.ServerConnection.ReadMessageThread;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Master Faster on 16.11.2017.
 */
public class ConversationSelectController implements Initializable {

    @FXML
    public TableView loginTableView;
    @FXML
    public TextField newFriendTextField;
    private ObservableList<Conversation> conversationList;
    //private ReadMessageThread readMessageThread;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //readMessageThread = new ReadMessageThread();
        //readMessageThread.start();

        TableColumn loginTableColumn = new TableColumn("Friends");
        loginTableColumn.setCellValueFactory(new PropertyValueFactory<Conversation, String>("friendLogin"));


        conversationList = FXCollections.observableList(ConversationSingleton.getConversationSingleton().getConversationList());
        loginTableView.setItems(conversationList);
        loginTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loginTableView.getColumns().addAll(loginTableColumn);
        loginTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(loginTableView.getSelectionModel().getSelectedItem() != null) {
                    MessageWindowSingleton.getMessageWindowSingleton()
                            .createMessageWindow(((Conversation) loginTableView.getSelectionModel().getSelectedItem()).getFriendLogin());
                }
            }
        });
    }

    @FXML
    public void addFriend(){
        if(ConversationSingleton.getConversationSingleton().getConversationList().stream()
                .filter(e -> e.getFriendLogin().equals(newFriendTextField.getText())).count() == 0) {
            ConversationSingleton.getConversationSingleton().addConversation(new Conversation(newFriendTextField.getText()));
            conversationList = FXCollections.observableList(ConversationSingleton.getConversationSingleton().getConversationList());
            loginTableView.setItems(conversationList);
        }
        conversationList = FXCollections.observableList(ConversationSingleton.getConversationSingleton().getConversationList());
        loginTableView.setItems(conversationList);
    }


    public void stopReadingThread(){
        //readMessageThread.stop();
    }

}

/*
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
 */