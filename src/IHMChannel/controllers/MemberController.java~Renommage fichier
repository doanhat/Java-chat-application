package IHMChannel.controllers;

import common.sharedData.UserLite;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MemberController {
    UserLite userToDisplay;
    @FXML
    ImageView profilePic;
    @FXML
    ImageView connectedIcon;
    @FXML
    Text username;
    @FXML
    ImageView authorizationIcon;
    @FXML
    Text isThatYouText;

    public void setUserToDisplay(UserLite userToDisplay) {
        this.userToDisplay = userToDisplay;
        this.username.setText(userToDisplay.getNickName());
    }

    /**
     * Appelée automatiquement par le FXML Loader.
     * Gère l'initialisation de l'affichage.
     */
    public void initialize(){
        iconsInit();
    }

    /**
     * Méthode d'initialisation des icônes du contrôle, appelée à l'initialisation
     */
    private void iconsInit(){
        //TODO initialisation des icônes:
        // - utilisateur en ligne
        // - bouton bloquer
    }
    /**
     * Méthode déclenchée au clic sur le bouton toggle de l'admin
     */
    public void toggleAdmin(){
        //TODO
    }




}
