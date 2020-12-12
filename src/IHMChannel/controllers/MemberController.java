package IHMChannel.controllers;

import common.shared_data.UserLite;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import IHMChannel.switchButton.ToggleSwitch;

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
    @FXML
    ToggleSwitch toggleAdminBtn;
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
        toggleAdminBtn.setMemberController(this);
        Image usersImage = new Image("IHMChannel/icons/ban.png");
        ImageView usersIcon = new ImageView(usersImage);
        usersIcon.setFitHeight(15);
        usersIcon.setFitWidth(15);
        banBtn.setGraphic(usersIcon);
    }
    /**
     * Méthode déclenchée au clic sur le bouton toggle de l'admin
     */
    public void toggleAdmin(){
        //TODO
        System.out.println(this.toggleAdminBtn.getCurrentRole());
    }

    public void banUser(){
        System.out.println("ban");
    }



}
