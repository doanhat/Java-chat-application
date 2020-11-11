package IHMChannel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Cette classe est utilisée pour lancer uniquement la partie "channel" de l'interface, notamment pendant le développement.
 * Elle ne sera pas utilisée telle qu'elle dans la version complète de l'application
 */
public class MainChannel extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/ChannelPage.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
