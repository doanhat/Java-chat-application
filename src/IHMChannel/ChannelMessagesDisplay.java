package IHMChannel;

import IHMChannel.controllers.ChannelMessagesController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

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

    public ChannelMessagesDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelMessages.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
    }

    public void setConnectedMembersList(List<UserLite> connectedMembersList){
        controller.setConnectedMembersList(connectedMembersList);
    }

    public void configureMessageController(IHMChannelController ihmChannelController){
        controller.setIhmChannelController(ihmChannelController);
    }

}
