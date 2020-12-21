package IHMChannel;

import IHMChannel.controllers.AdminMembersListController;
import common.shared_data.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

/***
 *  Classe "modèle" qui gère l'affichage  de la liste des membres du channel en fonction du statut (admin, créateur, simple membre) en appelant le FXML Loader
 */
public class AdminMembersListDisplay {
    public Parent getRoot() {
        return root;
    }

    private Parent root = null;
    private AdminMembersListController adminMembersController;

    public AdminMembersListController getController(){return this.adminMembersController;}

    public AdminMembersListDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/AdminMembersList.fxml"));
        root = fxmlLoader.load();
        adminMembersController = fxmlLoader.getController();
    }

    public void configureController(IHMChannelController ihmChannelController){
        adminMembersController.setIhmChannelController(ihmChannelController);
    }
    public void setConnectedMembersList(List<UserLite> connectedMembersList){
        adminMembersController.setConnectedMembersList(connectedMembersList);
    }
    public void addMemberToConnectedMembersList(UserLite user) {
        adminMembersController.addMemberToConnectedMembersList(user);
    }

    public void removeMemberFromConnectedMembersList(UserLite user) {
        adminMembersController.removeMemberFromConnectedMembersList(user);
    }
}
