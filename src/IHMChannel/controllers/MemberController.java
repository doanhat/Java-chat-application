package IHMChannel.controllers;

import common.sharedData.UserLite;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    @FXML
    Button banBtn;

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
        // Utilisateur en ligne
        Image connectedImage = new Image("IHMChannel/icons/circle-solid.png");
        connectedIcon.setImage(connectedImage);
        connectedIcon.setFitHeight(10);
        connectedIcon.setFitWidth(10);

        // Image Admin
        Image adminImage = new Image("IHMChannel/icons/crown-solid.png");
        authorizationIcon.setImage(adminImage);
        authorizationIcon.setFitHeight(15);
        authorizationIcon.setFitWidth(15);

        // Ban Icon
        Image banImage = new Image("IHMChannel/icons/ban-solid.png");
        ImageView banIcon = new ImageView(banImage);
        banIcon.setFitHeight(15);
        banIcon.setFitWidth(15);
        banBtn.setGraphic(banIcon);
    }
    /**
     * Méthode déclenchée au clic sur le bouton toggle de l'admin
     */
    public void toggleAdmin(){
        //TODO
    }




}
