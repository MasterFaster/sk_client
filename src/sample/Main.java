package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import sample.Conversation.Conversation;
import sample.Conversation.ConversationSingleton;
import sample.ServerConnection.ServerSingleton;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/loginWindow/sample.fxml"));
        primaryStage.setTitle("Login window");
        Scene scene =new Scene(root, 300, 275);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("sample/loginWindow/login.css").toExternalForm());
        Image icon = new Image("sample/loginIcon.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        //Platform.setImplicitExit(false);
        launch(args);
        //ServerSingleton.getServerSingleton().closeSocket();
    }
}
