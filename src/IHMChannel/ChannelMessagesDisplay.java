package IHMChannel;

import IHMChannel.controllers.ChannelMessagesController;
import common.sharedData.Channel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Cette classe gère l'affichage de la partie "ChannelMessages" en appelant le FXML Loader
 */
public class ChannelMessagesDisplay {

    public Parent root= null;
    public ChannelMessagesController controller;
    private Channel channel;

    public ChannelMessagesController getController(){
        return this.controller;
    }

    /**
     * Constructeur
     * @throws IOException
     */
    public ChannelMessagesDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelMessages.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
    }

    /**
     * Permet de transmettre la référence au contrôleur principal de IHM-Channel au contrôleur de la vue views/ChannelMessages.fxml
     * @param ihmChannelController
     */
    public void configureChannelMessageController(IHMChannelController ihmChannelController){
        controller.setIhmChannelController(ihmChannelController);
    }

}
