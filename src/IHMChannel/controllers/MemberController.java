package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import common.interfaces.client.IIHMChannelToCommunication;
import common.sharedData.Channel;
import IHMChannel.MemberDisplay;
import common.sharedData.UserLite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import IHMChannel.switchButton.ToggleSwitch;

public class MemberController {

    private IHMChannelController ihmChannelController;

    Channel channel;

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
    ToggleSwitch toggleAdminBtn;
    @FXML
    Button banBtn;

    // TODO actionHandler: isThatYouText, toggleAdminBtn, banBtn

    UserLite userToDisplay;

    boolean isAdmin;
    boolean isCreator;
    boolean isConnected;

    public void setUserToDisplay(UserLite userToDisplay,boolean isAdmin, boolean isCreator, boolean isConnected, boolean toogleDisplay) {
        this.userToDisplay = userToDisplay;
        this.username.setText(userToDisplay.getNickName());

        this.isAdmin = isAdmin;
        this.isCreator = isCreator;
        this.isConnected = isConnected;

        // Desactivation du toogleAdmin
        if(!toogleDisplay){toggleAdminBtn.setDisable(true);}

        if(ihmChannelController.getInterfaceToData().getLocalUser().getId().equals(userToDisplay.getId())){
            isThatYouText.setText(" (vous)");
        }

        iconsInit();
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
     * Méthode déclenchée au clic sur le bouton toggle de l'admin permettant de faire basculer le statut d'un membre entre administrateur et simple membre.
     */
    public void toggleAdmin(){
        if(isAdmin){
            isAdmin= false;
            // TODO Avoir une fonction removeAdmin();
            System.out.println("Retrait d'un  admin. ");
        }else{
            isAdmin = true;
            System.out.println("Ajout d'un user en admin. ");
            ihmChannelController.getInterfaceToCommunication().giveAdmin(userToDisplay, channel);
        }

    }

    public void banHandler() {
        //TODO
        System.out.println("ban");
    }

    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
