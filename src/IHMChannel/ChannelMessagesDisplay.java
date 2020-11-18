package IHMChannel;

import IHMChannel.IHMChannelWindowController;
import common.sharedData.Channel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Cette classe gère l'affichage de la partie "ChannelMessages" en appelant le FXML Loader
 */
public class ChannelMessagesDisplay {

    public Parent root= null;
    public IHMChannelWindowController controller;

    public IHMChannelWindowController getController(){
        return this.controller;
    }

    public ChannelMessagesDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("../IHMChannel/IHMChannelWindow.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
    }

}
