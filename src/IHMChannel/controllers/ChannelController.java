package IHMChannel.controllers;

import IHMChannel.ChannelMembersDisplay;
import IHMChannel.ChannelMessagesDisplay;
import IHMChannel.IHMChannelController;
import common.IHMTools.IHMTools;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;

import java.io.IOException;
import java.util.List;

public class ChannelController {
    private Channel currentChannel; //channel à afficher dans l'interface
    private IHMChannelController ihmChannelController;
    private List<UserLite> connectedMembersList;
    private ChannelMessagesController channelMessagesController;


    /*
     ** @author : Triet
     */
    private ChannelPageController channelPageController;


    @FXML
    BorderPane pageToDisplay;
    @FXML
    Label channelName;
    @FXML
    Label channelDescription;

    //Boutons barre supérieure
    @FXML
    Button homeBtn;
    @FXML
    Button seeMembersBtn;
    @FXML
    Button addMemberBtn;
    @FXML
    Button leaveChannelBtn;

    //Menu contextuel
    @FXML
    ContextMenu channelContextMenu;
    @FXML
    CheckMenuItem seeMembersMenuCheck;
    @FXML
    Button contextMenuBtn;


    ChannelMessagesDisplay channelMessagesDisplay;

    ChannelMembersDisplay channelMembersDisplay;

    Boolean seeMessages = true;

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }


    public void initialize() throws IOException {
        iconsInit();
        //Affichage de la partie "messages"
        channelMessagesDisplay = new ChannelMessagesDisplay();
        channelMessagesController = channelMessagesDisplay.getController();
        //channelMessagesDisplay.setConnectedMembersList(connectedMembersList);
        pageToDisplay.setCenter(channelMessagesDisplay.root);

        //Chargement de la liste des utilisateurs
        channelMembersDisplay = new ChannelMembersDisplay();

    }

    public void configureMessageDisplay(IHMChannelController ihmChannelController) {
        channelMessagesDisplay.configureMessageController(ihmChannelController);
    }

    public void configureMemberDisplay(IHMChannelController ihmChannelController) {
        channelMembersDisplay.configureMembersController(ihmChannelController);
    }

    private void iconsInit() {
        // Home
        /*Image homeImage = new Image("IHMChannel/icons/home-solid.png");
        ImageView homeIcon = new ImageView(homeImage);
        homeIcon.setFitHeight(15);
        homeIcon.setFitWidth(15);
        homeBtn.setGraphic(homeIcon);*/

        //Liste membres
        Image usersImage = new Image("IHMChannel/icons/users-solid.png");
        ImageView usersIcon = new ImageView(usersImage);
        usersIcon.setFitHeight(15);
        usersIcon.setFitWidth(15);
        seeMembersBtn.setGraphic(usersIcon);

        //Ajout membre
        Image addUserImage = new Image("IHMChannel/icons/user-plus-solid.png");
        ImageView addUserIcon = new ImageView(addUserImage);
        addUserIcon.setFitHeight(15);
        addUserIcon.setFitWidth(15);
        addMemberBtn.setGraphic(addUserIcon);

        //Quitter
        Image exitImage = new Image("IHMChannel/icons/exit.png");
        ImageView exitIcon = new ImageView(exitImage);
        exitIcon.setFitHeight(15);
        exitIcon.setFitWidth(15);
        leaveChannelBtn.setGraphic(exitIcon);

        //Menu Contextuel
        Image contextMenuImage = new Image("IHMChannel/icons/chevron_down.png");
        ImageView contextMenuIcon = new ImageView(contextMenuImage);
        contextMenuIcon.setFitHeight(15);
        contextMenuIcon.setFitWidth(15);
        contextMenuBtn.setGraphic(contextMenuIcon);
    }

    public void receiveMessage(Message receivedMessage, Message responseTo) {

        currentChannel.addMessage(receivedMessage);
        channelMessagesDisplay.getController().addMessageToObservableList(receivedMessage);
    }


    public void addNewAdmin(UserLite user) throws IOException {
        currentChannel.addAdmin(user);
        channelMembersDisplay.getController().setCurrentChannel(currentChannel);
        // Faut-il mettre a jour nous même le channel dans les composants fils?
    }

    /**
     * Méthode déclenchée au clic sur le bouton "voir les membres"
     */
    public void seeMembers() {
        if (Boolean.TRUE.equals(seeMessages)) {
            pageToDisplay.setCenter(channelMembersDisplay.root);
            seeMessages = false;
            seeMembersMenuCheck.setSelected(true);
        } else {
            pageToDisplay.setCenter(channelMessagesDisplay.root);
            seeMessages = true;
            seeMembersMenuCheck.setSelected(false);
        }
    }

    /**
     * Méthode déclenchée au clic sur le bouton "ajouter un membre"
     * Affiche la pop-up de sélection d'utilisateur
     */
    public void addUserToChannel() {
        //Affiche le FXML "AddMemberPopUp" dans une pop-up
        //En JavaFX, pop-up = fenêtre transparente sans aucun style.

        Popup popup = new Popup();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/AddMemberPopUp.fxml"));
            popup.getContent().add(fxmlLoader.load());
            AddMemberPopUpController addMemberPopUpController = fxmlLoader.getController();
            addMemberPopUpController.setChannelController(this);
            addMemberPopUpController.setUsersObservableList(this.getIhmChannelController().getInterfaceToIHMMain().getConnectedUsersList());
            popup.setAutoHide(true); //disparaît si on clique ailleurs
            Bounds screenBounds = addMemberBtn.localToScreen(addMemberBtn.getBoundsInLocal()); //alignement pop up et bouton
            popup.show(addMemberBtn.getScene().getWindow(), screenBounds.getMinX(), screenBounds.getMaxY());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setChannel(Channel channel) {
        this.setCurrentChannel(channel);
        channelName.setText(channel.getName());
        channelDescription.setText(channel.getDescription());

        try {
            //On transmet aux 2 "sous-vues" le channel à afficher et chacune fait le traitement nécessaire
            channelMessagesDisplay.getController().setCurrentChannel(channel);
            channelMembersDisplay.getController().setCurrentChannel(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Méthode déclenchée au clic sur le bouton "quitter le channel"
     */
    public void leaveChannel() {

        boolean result = IHMTools.confirmationPopup("Voulez vous quitter le channel ?");

        if (result) {
            /*  openedChannels.remove(channelMap.get(currentChannel));
        channelMap.remove(currentChannel)*/
        }
    }
    

    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }


    /**
     * Clic sur le bouton "v" pour faire apparaître le menu contextuel du channel
     * Récupère la position du bouton par rapport à l'écran pour positionner correctement le menu
     */
    public void openChannelContextMenu() {
        //Pour obtenir la posiiton du bouton par rapport à l'écran.
        //On veut que le menu contextuel soit correctement positionné par rapport au bouton : haut gauche du menu au bas gauche du bouton
        Bounds screenBounds = contextMenuBtn.localToScreen(contextMenuBtn.getBoundsInLocal());
        channelContextMenu.show(contextMenuBtn, screenBounds.getMinX(), screenBounds.getMaxY());
    }

    /***
     * Redirige sur la page "Home" (IhmMain) lorsqu'on clic sur le bouton home.
     *
     */
    public void homeBtnHandler() {
        // A voir ce que fait redirectHomePage()
        ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
    }

    /**
     * Clic sur "Modifier les infos du channel" depuis le menu contextuel
     */
    public void modifyChannel() {
        //TODO implémenter la méthode
    }

    /**
     * Clic sur "Changer les droits" depuis le menu contextuel
     */
    public void changeRights() {
        //TODO implémenter la méthode
    }

    /**
     * Clic sur "Kicker un utilisateur" depuis le menu contextuel
     */
    public void kickMember() {
        //TODO implémenter la méthode
    }

    /**
     * Clic sur "Liste des utilisateurs kickés" depuis le menu contextuel
     */
    public void seeKickedMembers() {
        //TODO implémenter la méthode
    }

    /**
     * Clic sur "supprimer channel" depuis le menu contextuel
     */
    public void deleteChannel() {

        boolean result = IHMTools.confirmationPopup("Voulez vous supprimer le channel ?");

        if (result){
            //appel interface /!\ UnsupportedOperationException : not implemented yet pour le moment (sera réglé avec integ)
            this.getIhmChannelController().getInterfaceForData().openChannelDeleted(this.currentChannel);
        }
    }

    public List<UserLite> getConnectedMembersList() {
        return connectedMembersList;
    }

    public void setConnectedMembersList(List<UserLite> connectedMembersList) {
        this.connectedMembersList = connectedMembersList;
        this.channelMessagesDisplay.setConnectedMembersList(connectedMembersList);
        this.channelMembersDisplay.setConnectedMembersList(connectedMembersList);

    }

    public void addConnectedUser(UserLite user) {
        this.connectedMembersList.add(user);
        this.channelMessagesDisplay.getController().addMemberToObservableList(user);
        //this.channelMessagesDisplay.setConnectedMembersList(this.connectedMembersList);
        this.channelMembersDisplay.getController().addMemberToObservableList(user);
    }

    public void removeConnectedUser(UserLite user) {
        this.connectedMembersList.remove(user);
        this.channelMessagesDisplay.getController().removeMemberFromObservableList(user);
        this.channelMembersDisplay.getController().removeMemberFromObservableList(user);
    }

    public void likeMessage(Message message, UserLite user) {
        channelMessagesController.likeMessage(message, user);
    }



}
