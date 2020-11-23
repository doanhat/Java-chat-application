package IHMChannel;

import IHMChannel.controllers.ChannelPageController;
import common.sharedData.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import common.sharedData.Visibility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cette classe est utilisée pour lancer uniquement la partie "channel" de l'interface, notamment pendant le développement.
 * Elle ne sera pas utilisée telle qu'elle dans la version complète de l'application
 */
public class MainChannel extends Application {
    Channel channelToDisplay;
    IHMChannelController ihmChannelController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("views/ChannelPage.fxml"));
        //Data init
        initTestData();
        // Pour la suite, c'est dans IHMChannelController que se fera les lignes suivantes
        ihmChannelController = new IHMChannelController();
        ChannelPageDisplay channelPageDisplay = new ChannelPageDisplay(channelToDisplay, ihmChannelController);
        primaryStage.setTitle("Channel");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(channelPageDisplay.root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Méthode utilisée pour initialiser les données de test
     * Crée un channel et lui ajoute une liste de messages
     */
    private void initTestData(){
        channelToDisplay = new OwnedChannel(1,"LO23",new UserLite(UUID.randomUUID(), "Léa", null),"channel pour l'UV LO23", Visibility.PUBLIC);
        UserLite usr1 = new UserLite(UUID.randomUUID(), "Aïda", null);
        usr1.setNickName("toto");
        UserLite usr2 = new UserLite(UUID.randomUUID(), "Vlad", null);
        usr2.setNickName("titi");

        List<Message> listMessages = new ArrayList<Message>();
        listMessages.add(new Message(1,"Salut, vous allez bien ?",usr1));
        listMessages.add(new Message(2,"Oui super et toi ?",usr2));
        listMessages.add(new Message(3,"T'as avancé le projet LO23 ?",usr1));

        channelToDisplay.setMessages(listMessages);
    }
}
