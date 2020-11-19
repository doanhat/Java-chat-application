package app;

import common.sharedData.UserLite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("LO23-Chat-Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();*/

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        File fichier = new File(System.getProperty("user.dir")+"/projet-lo23a20d1/resource/client2.json");
        // ouverture d'un flux sur un fichier
        ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;

        // création d'un objet à sérializer
        UserLite m =  new UserLite(UUID.randomUUID(),  "Robert","avatar1") ;

        // sérialization de l'objet
        oos.writeObject(m) ;

        // fermeture du flux dans le bloc finally
    }


    public static void main(String[] args) {
        launch(args);
    }
}
