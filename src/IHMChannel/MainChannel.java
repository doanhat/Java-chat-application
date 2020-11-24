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
        primaryStage.setScene(new Scene(channelPageDisplay.root));
        primaryStage.setMaximized(true);
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
        UserLite user1 = new UserLite(UUID.randomUUID(), "Aïda", null);
        UserLite user2 = new UserLite(UUID.randomUUID(), "Lucas", null);
        UserLite user3 = new UserLite(UUID.randomUUID(), "Vladimir", null);
        UserLite user4 = new UserLite(UUID.randomUUID(), "Jérôme", null);
        UserLite user5 = new UserLite(UUID.randomUUID(), "Van-Triet", null);

        channelToDisplay.addUser(user1);
        channelToDisplay.addUser(user2);
        channelToDisplay.addUser(user3);
        channelToDisplay.addUser(user4);
        channelToDisplay.addUser(user5);

        channelToDisplay.addAdmin(user3);
        channelToDisplay.addAdmin(user4);

        List<Message> listMessages = new ArrayList<Message>();
        listMessages.add(new Message(1,"Salut, vous allez bien ?",user1));
        listMessages.add(new Message(2,"Oui super et toi ?",user2));
        listMessages.add(new Message(3,"T'as avancé le projet LO23 ?",user1));

        channelToDisplay.setMessages(listMessages);
    }
}
