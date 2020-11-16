package IHMChannel;

import IHMTools.IHMTools;
import common.sharedData.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.io.IOException;

/**
 * Classe qui va gérer l'affichage d'un contrôle "MemberControl" qui permet d'afficher la ligne dédiée à UN utilisateur dans la liste des membres du canal
 */
public class MemberDisplay {
    public Parent root = null;
    public IHMChannelMemberController  ihmChannelMemberController;

    /**
     * Constructeur, à appeler pour créer une ligne d'utilisateur.
     * @param name utilisateur à afficher dans le widget en cours de création
     */
    public MemberDisplay(String name) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("../IHMChannel/IHMChannelMember.fxml"));
        root = fxmlLoader.load();
        ihmChannelMemberController = fxmlLoader.getController();
        System.out.println(ihmChannelMemberController.toString());
        ihmChannelMemberController.setUser(name);
    }
}
