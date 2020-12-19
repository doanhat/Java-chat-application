package IHMChannel.controllers;

import common.shared_data.UserLite;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class KickedMemberController {
    @FXML
    ImageView profilePic;
    @FXML
    Text username;
    @FXML
    Button unKickBtn;
    @FXML
    Button infoBtn;

    Boolean isUnKicked;

    UserLite userToDisplay;

    public void setUserToDisplay(UserLite userToDisplay) {
        this.userToDisplay = userToDisplay;
        this.username.setText(userToDisplay.getNickName());
    }

    /**
     * Appelée automatiquement par le FXML Loader.
     * Gère l'initialisation de l'affichage.
     */
    public void initialize(){
        isUnKicked = false;
        iconsInit();
    }

    /**
     * Méthode d'initialisation des icônes du contrôle, appelée à l'initialisation
     */
    private void iconsInit(){

        Image infoImage = new Image("IHMChannel/icons/info-circle-solid.png");
        ImageView infoIcon = new ImageView(infoImage);
        infoIcon.setFitHeight(15);
        infoIcon.setFitWidth(15);
        infoBtn.setGraphic(infoIcon);

        iconLock();
    }
    private void iconLock(){
        Image kickImage = new Image("IHMChannel/icons/lock-solid.png");
        ImageView kickIcon = new ImageView(kickImage);
        kickIcon.setFitHeight(15);
        kickIcon.setFitWidth(15);
        unKickBtn.setGraphic(kickIcon);
    }

    private void iconUnLock(){
        Image kickImage = new Image("IHMChannel/icons/lock-open-solid.png");
        ImageView kickIcon = new ImageView(kickImage);
        kickIcon.setFitHeight(15);
        kickIcon.setFitWidth(15);
        unKickBtn.setGraphic(kickIcon);
    }
    public UserLite getUserToDisplay() {
        return userToDisplay;
    }
    public boolean isUnKicked() {
        return isUnKicked;
    }

    public void unKickBtnHandler() {
        if(isUnKicked){
            isUnKicked =false;
            iconLock();
        }else{
            isUnKicked =true;
            iconUnLock();
        }
    }

    public void infoBtnHandler() {
        //TODO
    }


}
