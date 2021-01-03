package IHMChannel;

import IHMChannel.controllers.KickedMemberController;
import common.shared_data.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class KickedMemberDisplay {
    public Parent root = null;
    public KickedMemberController kickedMemberController;
    public KickedMemberController getController(){return this.kickedMemberController;}
    /**
     * Constructeur, à appeler pour créer une ligne d'utilisateur.
     * @param userToDisplay utilisateur à afficher dans le widget en cours de création
     */
    public KickedMemberDisplay(UserLite userToDisplay)  {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/IHMChannel/views/KickedMember.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        kickedMemberController = fxmlLoader.getController();
        kickedMemberController.setUserToDisplay(userToDisplay);
    }
}
