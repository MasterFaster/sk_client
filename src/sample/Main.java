package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginWindow/sample.fxml"));
        primaryStage.setTitle("Login window");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        ConversationSingleton.getConversationSingleton().addConversation(new Conversation("Serwer"));
        ConversationSingleton.getConversationSingleton().addConversation(new Conversation("jackon"));
        ConversationSingleton.getConversationSingleton().addConversation(new Conversation("kacol"));
        ConversationSingleton.getConversationSingleton().addConversation(new Conversation("elo"));
        launch(args);
        ServerSingleton.getServerSingleton().closeSocket();
    }
}
