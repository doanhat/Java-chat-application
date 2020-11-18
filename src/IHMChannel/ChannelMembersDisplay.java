package IHMChannel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Classe "modèle" qui gère l'affichage de la liste des membres du channel (vue ChannelMembers) en appelant le FXML Loader
 */


public class ChannelMembersDisplay {
    public Parent root= null;
    public IHMChannelListMemberController ihmChannelListMemberController;

    public ChannelMembersDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("../IHMChannel/IHMChannelListMember.fxml"));
        root = fxmlLoader.load();
        ihmChannelListMemberController = fxmlLoader.getController();
    }
}
