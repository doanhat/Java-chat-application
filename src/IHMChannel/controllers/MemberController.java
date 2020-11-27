package IHMChannel.controllers;

import common.sharedData.UserLite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MemberController {

    @FXML
    ImageView profilePicture;
    @FXML
    ImageView connectedIcon;
    @FXML
    Text username;
    @FXML
    ImageView creatorIcon;
    @FXML
    Text isThatYouText;
    @FXML
    ToggleButton toggleAdminBtn;
    @FXML
    Button banBtn;

    // TO - DO actionHandler: profilePicture, isThatYouText, toggleAdminBtn, banBtn
    UserLite userToDisplay;

    boolean isAdmin;
    boolean isCreator;
    boolean isConnected;


    public void setUserToDisplay(UserLite userToDisplay,boolean isAdmin, boolean isCreator, boolean isConnected) {
        this.userToDisplay = userToDisplay;
        this.username.setText(userToDisplay.getNickName());

        this.isAdmin = isAdmin;
        this.isCreator = isCreator;
        this.isConnected = isConnected;
        iconsInit();

        if(isAdmin) toggleAdminBtn.setSelected(true);
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
        // Profile Icon
        Image profileImage = new Image("IHMChannel/icons/user-circle-solid.png");
        profilePicture.setImage(profileImage);
        profilePicture.setFitHeight(30);
        profilePicture.setFitWidth(30);

        if(isCreator){
            Image creatorImage = new Image("IHMChannel/icons/crown-solid.png");
            creatorIcon.setImage(creatorImage);
            creatorIcon.setFitHeight(15);
            creatorIcon.setFitWidth(15);
        }

        if(isConnected){
            Image connectedImage = new Image("IHMChannel/icons/circle-solid.png");
            connectedIcon.setImage(connectedImage);
            connectedIcon.setFitHeight(10);
            connectedIcon.setFitWidth(10);
        }

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
    public void adminHandler(){
        //TODO
    }


    public void banHandler() {
    }
}
