package IHMChannel;

import IHMChannel.controllers.MemberController;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Classe qui va gérer l'affichage d'un contrôle "MemberControl" qui permet d'afficher la ligne dédiée à UN utilisateur dans la liste des membres du canal
 */
public class MemberDisplay {
    public Parent root = null;
    public MemberController memberController;
    public MemberController getController(){return this.memberController;}
    /**
     * Constructeur, à appeler pour créer une ligne d'utilisateur.
     * @param userToDisplay utilisateur à afficher dans le widget en cours de création
     */
    public MemberDisplay(UserLite userToDisplay, boolean isAdmin, boolean isCreator,boolean isConnected,boolean toggleDisplay, Channel channel, IHMChannelController ihmChannelController)  {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/Member.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        memberController = fxmlLoader.getController();
        memberController.setIhmChannelController(ihmChannelController);
        memberController.setUserToDisplay(userToDisplay,isAdmin, isCreator,isConnected,toggleDisplay);
        memberController.setChannel(channel);
    }

}
