package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import common.IHMTools.IHMTools;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import IHMChannel.switchButton.ToggleSwitch;

import java.io.IOException;

public class MemberController {

    private IHMChannelController ihmChannelController;

    Channel channel;

    @FXML
    ImageView profilePic;
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

        if(isAdmin){toggleAdminBtn.setMemberController(this); }

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
            ihmChannelController.getInterfaceToCommunication().giveAdmin(userToDisplay, channel);
            // Pour tester le retour serveur
            /*
            try {
                getIhmChannelController().getInterfaceForData().addNewAdmin(userToDisplay,channel);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
        }

    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void banHandler() {
        // Check localUser =admin
        if(channel.getAdministrators().contains(this.ihmChannelController.getInterfaceToData().getLocalUser())){
            //check user kick pas le createur. Peut-on kick un autre admin?
            if(!channel.getCreator().getId().equals(userToDisplay.getId())){
                if(IHMTools.confirmationPopup("Voulez vous kicker cet utilisateur ?")){
                    // Kicker l'utilisateur
                    //this.getIhmChannelController().getInterfaceToCommunication().banUserFromChannel();
                }
            }else{
                IHMTools.informationPopup("Vous ne pouvez pas kick le créateur.");
            }
        }else{
            IHMTools.informationPopup("Vous n'avez pas les droits pour réaliser cette action. Vous devez être administrateur.");
        }

       /*TODO
           1. Vérifier les droits admins de la personne qui appuie sur le bouton + de celle qui est kicker
           2. Si ok :
            - Pop up de confirmation avec la durée de ban
            -> confirmation : this.getIhmChannelController().getInterfaceToCommunication().banUserFromChannel();

            Retour Serveur :
                - Pour l'admin : confirmation du kick.
                - Pour la personne kické : notification qu'elle a été kick
                - Pour tout le monde : notification du ban

             Autre :
                - vérifier que l'accès à un channel n'est pas possible pour un utilisateur kické pour la durée mentionner.
                - Vérifier que quand la date est passé, il a reacces au channel
        */


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
