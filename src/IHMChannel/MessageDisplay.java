package IHMChannel;

import IHMChannel.controllers.MessageController;
import common.sharedData.Message;
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
    public MessageDisplay(Message msg) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/MessageControl.fxml"));
        root = fxmlLoader.load();
        messageController = fxmlLoader.getController();
        messageController.setMessageToDisplay(msg);
    }

}

