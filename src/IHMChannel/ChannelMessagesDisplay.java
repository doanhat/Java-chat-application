package IHMChannel;

import IHMChannel.controllers.ChannelController;
import IHMChannel.controllers.ChannelMessagesController;
import IHMChannel.controllers.ChannelPageController;
import common.IHMTools.IHMTools;
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

    public ChannelMessagesDisplay(ChannelController channelController) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelMessages.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        IHMTools.fitSizeToParent(channelController.get);
    }

    public void configureMessageController(IHMChannelController ihmChannelController){
        controller.setIhmChannelController(ihmChannelController);
    }

}
