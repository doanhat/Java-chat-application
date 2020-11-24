package app;

import Communication.client.CommunicationClientController;
import Data.client.DataClientController;
import IHMChannel.IHMChannelController;
import IHMChannel.interfaces.IHMMainToIHMChannel;
import IHMMain.IHMMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Communication.client.CommunicationClientInterface;

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
        communicationClientController = CommunicationClientController.instance();
        /**
         * A modifier quand Comm change son singleton
         */
        dataClientController = new DataClientController(CommunicationClientInterface.instance(), ihmChannelController.getInterfaceForData(), ihmMainController.getDataToIHMMain());

        ihmChannelController.setInterfaceToCommunication(CommunicationClientInterface.instance());
        ihmChannelController.setInterfaceToData(dataClientController.getIhmChannelToData());
        ihmChannelController.setInterfaceToIHMMain(ihmMainController.getIhmChannelToIHMMain());

        ihmMainController.setIhmMainToCommunication(CommunicationClientInterface.instance());
        ihmMainController.setIhmMainToData(dataClientController.getIhmMainToData());
        ihmMainController.setIhmMainToIHMChannel(ihmChannelController.getInterfaceForIHMMain());

        communicationClientController.setICommunicationData(dataClientController.getCommToData());
        communicationClientController.setICommunicationToIHMChannel(ihmChannelController.getInterfaceForCommunication());
        communicationClientController.setICommunicationToIHMMain(ihmMainController.getCommunicationToIHMMain());


        MainWindowController mainWindowController = loader.getController();
        mainWindowController.getConnectionController().setIhmMainController(ihmMainController);
        ihmMainController.setMainWindowController(mainWindowController);



        primaryStage.setTitle("LO23-Chat-Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
