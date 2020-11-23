package app;

import IHMMain.controllers.IHMMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private IHMMainController ihmMainController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("LO23-Chat-Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ihmMainController = new IHMMainController();
        MainWindowController mainWindowController = loader.getController();
        mainWindowController.setIhmMainController(ihmMainController);
        ihmMainController.setMainWindowController(loader.getController());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
