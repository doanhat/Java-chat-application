package IHMChannel;

import IHMChannel.controllers.ConnectedMembersListController;
import common.shared_data.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

/**
 *  Classe "modèle" qui gère l'affichage  de la liste des membres du channel en fonction de s'ils sont connectés en appelant le FXML Loader
 */
public class ConnectedMembersListDisplay {
    public Parent root = null;
    public ConnectedMembersListController connectedMembersController;

    public ConnectedMembersListController getController(){return this.connectedMembersController;}

    public ConnectedMembersListDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/IHMChannel/views/ConnectedMembersList.fxml"));
        root = fxmlLoader.load();
        connectedMembersController = fxmlLoader.getController();
    }

    public void configureController(IHMChannelController ihmChannelController){
        connectedMembersController.setIhmChannelController(ihmChannelController);
    }

    public void setConnectedMembersList(List<UserLite> connectedMembersList){
        connectedMembersController.setConnectedMembersList(connectedMembersList);
    }
    public void addMemberToConnectedMembersList(UserLite user) {
        connectedMembersController.addMemberToConnectedMembersList(user);
    }
    public void removeMemberFromConnectedMembersList(UserLite user) {
        connectedMembersController.removeMemberConnectedMembersFromList(user);
    }
}
