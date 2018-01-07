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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

}

