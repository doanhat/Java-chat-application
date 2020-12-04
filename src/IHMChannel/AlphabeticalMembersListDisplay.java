package IHMChannel;

import IHMChannel.controllers.AlphabeticalMembersListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;


/**
 * Classe "modèle" qui gère l'affichage en ordre alphabétique de la liste des membres du channel en appelant le FXML Loader
 */

public class AlphabeticalMembersListDisplay {
    public Parent root = null;
    public AlphabeticalMembersListController AlphabeticalMembersController;

    public AlphabeticalMembersListController getController(){return this.AlphabeticalMembersController;}

    public AlphabeticalMembersListDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/AlphabeticalMembersList.fxml"));
        root = fxmlLoader.load();
        AlphabeticalMembersController = fxmlLoader.getController();
    }

    public void configureController(IHMChannelController ihmChannelController){
        AlphabeticalMembersController.setIhmChannelController(ihmChannelController);
    }
}
