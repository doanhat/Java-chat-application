package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    TextField username;

    @FXML
    ImageView creatorIcon;
    @FXML
    Text isThatYouText;
    @FXML
    ToggleSwitch toggleAdminBtn;
    @FXML
    Button banBtn;

    @FXML
    Button editNicknameBtn;


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
            editNicknameBtn.setVisible(true);

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

    public void editNickname() {
        username.setEditable(true);

        //Handler pour valider la modification à l'appui sur entrée
        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {

                    ihmChannelController.getInterfaceToCommunication().changeNickname(
                            ihmChannelController.getInterfaceToData().getLocalUser(),
                            channel,
                            username.getText()
                    );
                    
                    username.setEditable(false);

                    //TODO appel interface test
                    /*
                    //TODO à enlever pour l'intégration, ne sert qu'aux tests
                    channelMessagesController.getIhmChannelController().getInterfaceForData().editMessage(
                            messageToDisplay,
                            newMsg,
                            channelMessagesController.channel
                    );
                     */
                }
            }
        });
    }
}
