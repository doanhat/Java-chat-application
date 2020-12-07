package IHMChannel;

import IHMChannel.controllers.ChannelMembersController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

/**
 * Classe "modèle" qui gère l'affichage de la liste des membres du channel (vue ChannelMembers) en appelant le FXML Loader
 */
public class ChannelMembersDisplay {
    public Parent root= null;
    public ChannelMembersController channelMembersController;

    public ChannelMembersController getController(){
        return this.channelMembersController;
    }

    public ChannelMembersDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelMembers.fxml"));
        root = fxmlLoader.load();
        channelMembersController = fxmlLoader.getController();
    }

    public void configureMembersController(IHMChannelController ihmChannelController) {
        channelMembersController.setIhmChannelController(ihmChannelController);
    }

    public void setConnectedMembersList(List<UserLite> connectedMembersList){
        channelMembersController.setConnectedMembersList(connectedMembersList);
    }
}
