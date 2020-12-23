package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import common.IHMTools.IHMTools;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import IHMChannel.switchButton.ToggleSwitch;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

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
            ihmChannelController.getInterfaceToCommunication().removeAdmin(userToDisplay, channel);
        }else{
            isAdmin = true;
            ihmChannelController.getInterfaceToCommunication().giveAdmin(userToDisplay, channel);
        }

    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void banHandler() {

        AtomicBoolean isLocalUserAdmin = new AtomicBoolean(false);
        channel.getAdministrators().forEach(userLite -> {
            if(userLite.getId().equals(this.ihmChannelController.getInterfaceToData().getLocalUser().getId())){
                isLocalUserAdmin.set(true);
            }
        });

        if(isLocalUserAdmin.get()){
            if(!channel.getCreator().getId().equals(userToDisplay.getId())){

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/KickPopUp.fxml"));
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                KickPopUpController kickPopUpController = fxmlLoader.getController();
                kickPopUpController.setPopupText(userToDisplay.getNickName(), channel.getName());

                Stage popUpWindow = new Stage();
                popUpWindow.initModality(Modality.APPLICATION_MODAL);
                popUpWindow.setTitle("Kicker un utilisateur");
                popUpWindow.setScene(new Scene(root));
                popUpWindow.setResizable(false);
                popUpWindow.show();

                kickPopUpController.getCancelBtn().setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e){
                        popUpWindow.close();
                    }
                });

                kickPopUpController.getConfirmBtn().setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        String explanation = kickPopUpController.getExplanationMessage();
                        LocalDate kickDate = kickPopUpController.getDatePick();
                        Boolean isPermanent = kickPopUpController.getIsPermanent();
                        System.out.print("Ban de :" + userToDisplay.getNickName() + " Pour " + explanation + " jusqu'au " + kickDate + " , Ispermanent = " +isPermanent + "\n");
                        // Interface à ajouter
                        getIhmChannelController().getInterfaceToCommunication().banUserFromChannel(userToDisplay,kickDate,isPermanent,explanation,channel.getId());
                        popUpWindow.close();
                    }
                });
            }else{
                IHMTools.informationPopup("Vous ne pouvez pas kicker le créateur.");
            }
        }else{
            IHMTools.informationPopup("Vous n'avez pas les droits pour réaliser cette action. Vous devez être administrateur.");
        }
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
        username.requestFocus();

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

                    //TODO à enlever pour l'intégration, ne sert qu'aux tests
                    userToDisplay.setNickName(username.getText());
                    ihmChannelController.getInterfaceForCommunication().changeNickname(userToDisplay,channel.getId());

                }
            }
        });
    }

    public void changeNickname(String nickname) {
        username.setText(nickname);
    }
}
