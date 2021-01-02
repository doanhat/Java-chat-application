package IHMChannel;

import IHMChannel.controllers.ChannelMessagesController;
import IHMChannel.controllers.MessageController;
import common.shared_data.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Classe "modèle"
 * Cette classe génère un contrôle pour le message passé en paramètre de son constructeur, via le FXML Loader
 */
public class MessageDisplay {
    public Parent root = null;
    public MessageController messageController;

    /**
     * Constructeur de la classe MessageDisplay. Celui-ci va créer un contrôle message.
     * @param msg objet message pour lequel le contrôle sera créé
     * @throws IOException
     */
    public MessageDisplay(Message msg, ChannelMessagesController channelMessagesController) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/Message.fxml"));
        root = fxmlLoader.load();
        messageController = fxmlLoader.getController();
        messageController.setChannelMessagesController(channelMessagesController);
        messageController.setMessageToDisplay(msg);
        messageController.iconsInit();
        channelMessagesController.getMessagesMap().put(msg.getId(), messageController);
    }

}

