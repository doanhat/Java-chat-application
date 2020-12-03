package IHMChannel;

import IHMChannel.controllers.ChannelMembersController;
import common.sharedData.Channel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Classe "modèle" qui gère l'affichage de la liste des membres du channel (vue ChannelMembers) en appelant le FXML Loader
 */
public class ChannelMembersDisplay {
    public Parent root= null;
    public ChannelMembersController membersController;

    public ChannelMembersController getController(){
        return this.membersController;
    }

    public ChannelMembersDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelMembers.fxml"));
        root = fxmlLoader.load();
        membersController = fxmlLoader.getController();
    }

    public void configureMembersController(IHMChannelController ihmChannelController) {
        membersController.setIhmChannelController(ihmChannelController);
    }
}
