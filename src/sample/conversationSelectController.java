package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Master Faster on 16.11.2017.
 */
public class conversationSelectController implements Initializable {


    public TableView loginTableView;
    private ObservableList<Conversation> conversationList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn loginTableColumn = new TableColumn("Login");
        loginTableColumn.setCellValueFactory(new PropertyValueFactory<Conversation, String>("friendLogin"));
        conversationList = FXCollections.observableList(ConversationSingleton.getConversationSingleton().getConversationList());
        loginTableView.setItems(conversationList);
        loginTableView.getColumns().addAll(loginTableColumn);
        loginTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println(((Conversation)loginTableView.getSelectionModel().getSelectedItem()).getFriendLogin());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageWindow.fxml"));
                try{
                    Parent root = (Parent)fxmlLoader.load();
                    MessageWindowController messageWindowController = fxmlLoader.getController();
                    messageWindowController.setFriendLogin(((Conversation)loginTableView.getSelectionModel().getSelectedItem()).getFriendLogin());
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
            }
        });
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