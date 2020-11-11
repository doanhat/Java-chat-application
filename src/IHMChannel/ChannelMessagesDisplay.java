package IHMChannel;

import IHMChannel.controllers.ChannelMessages;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Cette classe gère l'affichage de la partie "ChannelMessages" en appelant le FXML Loader
 */
public class ChannelMessagesDisplay {

    public Parent root= null;
    public ChannelMessages controller;

    public ChannelMessagesDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelMessages.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
    }

}
