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
    ToggleButton toggleAdminBtn;
    @FXML
    Button banBtn;

    // TO - DO actionHandler: isThatYouText, toggleAdminBtn, banBtn

    UserLite userToDisplay;

    boolean isAdmin;
    boolean isCreator;
    boolean isConnected;


    public void setUserToDisplay(UserLite userToDisplay,boolean isAdmin, boolean isCreator, boolean isConnected) {
    @FXML
    Button banBtn;
    @FXML
    ToggleSwitch toggleAdminBtn;
    public void setUserToDisplay(UserLite userToDisplay) {
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
    public void adminHandler(){
        System.out.println("Envoi message de changement de droit administrateur au serveur... ");
        ihmChannelController.getInterfaceToCommunication().giveAdmin(userToDisplay, channel);
    public void toggleAdmin(){
        //TODO
        System.out.println(this.toggleAdminBtn.getCurrentRole());
    }

    public void banUser(){
        System.out.println("ban");
    }

    public void banHandler() {
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
