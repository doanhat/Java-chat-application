package IHMChannel;

import IHMChannel.controllers.ChannelController;
import IHMChannel.controllers.MemberAddController;
import IHMChannel.controllers.MemberController;
import common.sharedData.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MemberAddDisplay {
    public Parent root = null;
    public MemberAddController memberAddController;
    public UserLite userLite;

    /**
     * Constructeur, à appeler pour créer une ligne d'utilisateur.
     * @param userToInvite utilisateur à afficher dans le widget en cours de création
     */
    public MemberAddDisplay(UserLite userToInvite, ChannelController channelController) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/MemberAdd.fxml"));
        root = fxmlLoader.load();
        memberAddController = fxmlLoader.getController();
        memberAddController.setUserToInvite(userToInvite);
        memberAddController.setChannelController(channelController);
        this.userLite = userToInvite;
    }
}
