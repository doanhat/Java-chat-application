package IHMChannel;

import IHMChannel.controllers.MemberControl;
import common.sharedData.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Classe qui va gérer l'affichage d'un contrôle "MemberControl" qui permet d'afficher la ligne dédiée à UN utilisateur dans la liste des membres du canal
 */
public class MemberDisplay {
    public Parent root = null;
    public MemberControl memberController;

    /**
     * Constructeur, à appeler pour créer une ligne d'utilisateur.
     * @param userToDisplay utilisateur à afficher dans le widget en cours de création
     */
    public MemberDisplay(UserLite userToDisplay) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/MemberControl.fxml"));
        root = fxmlLoader.load();
        memberController = fxmlLoader.getController();
        memberController.setUserToDisplay(userToDisplay);
    }
}
