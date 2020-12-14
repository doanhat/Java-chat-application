package IHMChannel;

import IHMChannel.controllers.AlphabeticalMembersListController;
import common.sharedData.UserLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;


/**
 * Classe "modèle" qui gère l'affichage en ordre alphabétique de la liste des membres du channel en appelant le FXML Loader
 */

public class AlphabeticalMembersListDisplay {
    public Parent root = null;
    public AlphabeticalMembersListController alphabeticalMembersController;

    public AlphabeticalMembersListController getController(){return this.alphabeticalMembersController;}

    public AlphabeticalMembersListDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/AlphabeticalMembersList.fxml"));
        root = fxmlLoader.load();
        alphabeticalMembersController = fxmlLoader.getController();
    }

    public void configureController(IHMChannelController ihmChannelController){
        alphabeticalMembersController.setIhmChannelController(ihmChannelController);
    }
    public void setConnectedMembersList(List<UserLite> connectedMembersList){
        alphabeticalMembersController.setConnectedMembersList(connectedMembersList);
    }
    public void addMemberToList(UserLite user) {
        alphabeticalMembersController.addMemberToList(user);
    }
    public void removeMemberFromList(UserLite user) {
        alphabeticalMembersController.removeMemberFromList(user);
    }
}
