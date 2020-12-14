package IHMChannel;

import IHMChannel.controllers.ConnectedMemberController;
import common.shared_data.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Classe qui va gérer l'affichage d'un contrôle "MemberControl" qui permet d'afficher la ligne dédiée à UN utilisateur dans la liste des membres du canal
 */
public class ConnectedMemberDisplay {
    public Parent root = null;
    public ConnectedMemberController connectedMemberController;

    /**
     * Constructeur, à appeler pour créer une ligne d'utilisateur.
     * @param userToDisplay utilisateur à afficher dans le widget en cours de création
     */
    public ConnectedMemberDisplay(UserLite userToDisplay) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ConnectedMember.fxml"));
        root = fxmlLoader.load();
        connectedMemberController = fxmlLoader.getController();
        connectedMemberController.setUserToDisplay(userToDisplay);
    }
}
