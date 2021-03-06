package app;

import communication.client.CommunicationClientController;
import data.client.DataClientController;
import IHMChannel.IHMChannelController;
import IHMMain.IHMMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private IHMMainController ihmMainController;
    private IHMChannelController ihmChannelController;
    private CommunicationClientController communicationClientController;
    private DataClientController dataClientController;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();

        /**
         * Init des modules et des interfaces avant le charger la UI
         */


        ihmMainController = new IHMMainController();
        ihmChannelController = new IHMChannelController();
        communicationClientController = new CommunicationClientController();
        /**
         * A modifier quand Comm change son singleton
         */
        dataClientController = new DataClientController(communicationClientController.getDataToCommunication(), ihmChannelController.getInterfaceForData(), ihmMainController.getDataToIHMMain());

        ihmChannelController.setInterfaceToCommunication(communicationClientController.getIHMChannelToCommunication());
        ihmChannelController.setInterfaceToData(dataClientController.getIhmChannelToData());
        ihmChannelController.setInterfaceToIHMMain(ihmMainController.getIhmChannelToIHMMain());

        ihmMainController.setIhmMainToCommunication(communicationClientController.getIHMMainToCommunication());
        ihmMainController.setIhmMainToData(dataClientController.getIhmMainToData());
        ihmMainController.setIhmMainToIHMChannel(ihmChannelController.getInterfaceForIHMMain());

        communicationClientController.setICommunicationToData(dataClientController.getCommToData());
        communicationClientController.setICommunicationToIHMChannel(ihmChannelController.getInterfaceForCommunication());
        communicationClientController.setICommunicationToIHMMain(ihmMainController.getCommunicationToIHMMain());


        MainWindowController mainWindowController = loader.getController();
        mainWindowController.setPrimaryStage(primaryStage);
        mainWindowController.setIhmMainController(ihmMainController);
        ihmMainController.setMainWindowController(mainWindowController);

        mainWindowController.loadConnectionWindow();

        primaryStage.setTitle("LO23-Chat-Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
