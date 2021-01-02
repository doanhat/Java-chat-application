package IHMChannel.controllers;

import IHMChannel.ChannelMembersDisplay;
import IHMChannel.ChannelMessagesDisplay;
import IHMChannel.IHMChannelController;
import common.IHMTools.IHMTools;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.UserLite;
import common.shared_data.Visibility;
import common.shared_data.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import static common.IHMTools.IHMTools.informationPopup;

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
    @FXML
    Button seeKickedMembersBtn;

    //Menu contextuel
    @FXML
    ContextMenu channelContextMenu;
    @FXML
    CheckMenuItem seeMembersMenuCheck;
    @FXML
    Button contextMenuBtn;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    ChannelMessagesDisplay channelMessagesDisplay;

    ChannelMembersDisplay channelMembersDisplay;

    Boolean seeMessages = true;
    Boolean changeRight = true;
    Boolean leavePossible = true;

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel){
        this.currentChannel = currentChannel;
    }

    public ChannelMembersDisplay getChannelMembersDisplay() {
        return channelMembersDisplay;
    }
    public void setLeavePossible(Boolean b) {
        leavePossible = b;
    }

    public void initialize() throws IOException {

        //Affichage de la partie "messages"
        channelMessagesDisplay = new ChannelMessagesDisplay();
        channelMessagesController = channelMessagesDisplay.getController();
        pageToDisplay.setCenter(channelMessagesDisplay.root);

        Platform.runLater(() ->
            IHMTools.fitSizeToParent((Region)ihmChannelController.getRoot(),(Region)channelMessagesDisplay.root)
        );

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

        if (Boolean.TRUE.equals(leavePossible)) {
            //Quitter
            Image exitImage = new Image("IHMChannel/icons/exit.png");
            ImageView exitIcon = new ImageView(exitImage);
            exitIcon.setFitHeight(15);
            exitIcon.setFitWidth(15);
            leaveChannelBtn.setGraphic(exitIcon);
        }else {
            leaveChannelBtn.setVisible(false);
        }

        //Menu Contextuel
        Image contextMenuImage = new Image("IHMChannel/icons/chevron_down.png");
        ImageView contextMenuIcon = new ImageView(contextMenuImage);
        contextMenuIcon.setFitHeight(15);
        contextMenuIcon.setFitWidth(15);
        contextMenuBtn.setGraphic(contextMenuIcon);

        // Liste kickedMembers
        Image kickedMembersImage = new Image("IHMChannel/icons/users-slash-solid.png");
        ImageView kickedMembersIcon = new ImageView(kickedMembersImage);
        kickedMembersIcon.setFitHeight(15);
        kickedMembersIcon.setFitWidth(15);
        seeKickedMembersBtn.setGraphic(kickedMembersIcon);
    }

    public void receiveMessage(Message receivedMessage) {
        currentChannel.addMessage(receivedMessage);
        channelMessagesDisplay.getController().addMessageToObservableList(receivedMessage);
    }

    public void addUser(UserLite user) throws IOException {
        currentChannel.addAuthorizedUser(user);
        channelMembersDisplay.getController().setCurrentChannel(currentChannel);
    }

    /**
     * Appelé pour enlever un utilisateur de la liste globale lorsqu'il est ban.
     * @param user UserLite
     * @throws IOException
     */
    public void removeUser(UserLite user) throws IOException {
        currentChannel.removeUser(user.getId());
        currentChannel.getAuthorizedPersons().removeIf(person ->(person.getId().equals(user.getId())));
        channelMembersDisplay.getController().setCurrentChannel(currentChannel);
    }

    public void removeUserAuthorization(UserLite user) throws IOException {
        currentChannel.removeUserAuthorization(user.getId());
        channelMembersDisplay.getController().setCurrentChannel(currentChannel);
    }

    public void addNewAdmin(UserLite user) throws IOException {
        currentChannel.addAdmin(user);
        channelMembersDisplay.getController().setCurrentChannel(currentChannel);
        //channelMembersDisplay.getController().adminMembersListDisplay.adminMembersController.
    }

    public void removeAdmin(UserLite user) {
        currentChannel.removeAdmin(user.getId());
        channelMembersDisplay.getController().setCurrentChannel(currentChannel);
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

            List<UserLite> usersToInvite = FXCollections.observableArrayList();
            this.getIhmChannelController().getInterfaceToIHMMain().getConnectedUsersList().forEach(userLite -> {
                if(!currentChannel.userIsAuthorized(userLite.getId()) && !currentChannel.userIsBanned(userLite.getId())){
                    usersToInvite.add(userLite);
                }
            });

            addMemberPopUpController.setUsersObservableList(usersToInvite);
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
        UserLite localUser = ihmChannelController.getInterfaceToData().getLocalUser();
        if (channel.getVisibility().equals(Visibility.PUBLIC) || channel.getCreator().getId().equals(localUser.getId())) {
            setLeavePossible(false);
        }
        iconsInit();

        //Restrictions sur l'affichage du bouton d'invitation
        //Visible si channel privé + utilisateur connecté = admin
        if(currentChannel.getVisibility()== Visibility.PRIVATE && currentChannel.userIsAdmin(ihmChannelController.getInterfaceToData().getLocalUser().getId())){
            addMemberBtn.setVisible(true);
        }

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
    public void quitChannel() {

            boolean result = IHMTools.confirmationPopup("Voulez vous quitter le channel ?");

            if (result) {
                ihmChannelController.getInterfaceToCommunication().quitChannel(getCurrentChannel());
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
        ihmChannelController.getInterfaceToIHMMain().redirectToHomePage();
    }

    /**
     * Clic sur "Modifier les infos du channel" depuis le menu contextuel
     */
    public void modifyChannel() {
        /**
         * Createur du channel seulement peut modifier le channel
         */
        Boolean canModify = false;
        UserLite user = ihmChannelController.getInterfaceToData().getLocalUser();
        if (currentChannel.getCreator().getId().equals(user.getId())) canModify = true;

        Parent root;

        if (Boolean.TRUE.equals(canModify)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ModificationInfoChannelPopup.fxml"));
                root = fxmlLoader.load();

                ModificationInfoChannelPopup creationController = fxmlLoader.getController();
                creationController.setParentController(this);

                creationController.setChannel(currentChannel);
                creationController.setUI();

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setTitle("Modifier les infos du channel");
                stage.setScene(new Scene(root, 600, 400));
                stage.setResizable(false);

                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clic sur "Changer les droits" depuis le menu contextuel
     */
    public void changeRights() {
        pageToDisplay.setCenter(channelMembersDisplay.root);
        channelMembersDisplay.getController().viewMode.selectToggle(channelMembersDisplay.getController().adminBtn);
        channelMembersDisplay.getController().adminSort();
    }

    /**
     * Clic sur "Kicker un utilisateur" depuis le menu contextuel
     */
    public void kickMember() {
        seeMembers();
    }

    /**
     * Clic sur "Liste des utilisateurs kickés" depuis le menu contextuel
     */
    public void seeKickedMembers() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/KickedMembersListPopUp.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        KickedMembersListPopUpController kickedMembersListPopUpController = fxmlLoader.getController();

        ObservableList<Kick> kickedObservableList = FXCollections.observableArrayList();
        for(Kick k : currentChannel.getKicked()){
            kickedObservableList.add(k);
        }
        kickedMembersListPopUpController.setKickedMembers(kickedObservableList);

        Stage popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Liste des utilisateurs kickés du channel");
        popUpWindow.setScene(new Scene(root));
        popUpWindow.setResizable(false);
        popUpWindow.show();

        kickedMembersListPopUpController.getSaveBtn().setOnAction(e -> {
            if(!kickedMembersListPopUpController.getUnKickedList().isEmpty()){
                AtomicBoolean isLocalUserAdmin = new AtomicBoolean(false);
                currentChannel.getAdministrators().forEach(userLite -> {
                    if(ihmChannelController.getInterfaceToData().getLocalUser().getId().equals(userLite.getId())){
                        isLocalUserAdmin.set(true);
                    }
                });
                if(isLocalUserAdmin.get()){
                    kickedMembersListPopUpController.getUnKickedList().forEach(userUnKicked ->{
                        getIhmChannelController().getInterfaceToCommunication().cancelBanOfUserFromChannel(userUnKicked, currentChannel.getId());
                    });
                }else{
                    IHMTools.informationPopup("Vous n'avez pas les droits pour réaliser cette action.");
                }
            }
            popUpWindow.close();
        });
    }

    /**
     * Clic sur "supprimer channel" depuis le menu contextuel. Si l'utilisateur est bien le créteur, alors une pop-up de confirmation s'ouvre.
     * Après confirmation, le traitement de suppression est effectué.
     */
    public void deleteChannel() {
        if(this.getIhmChannelController().getInterfaceToData().getLocalUser().getId().equals(currentChannel.getCreator().getId())){
            boolean result = IHMTools.confirmationPopup("Voulez vous supprimer le channel ?");
            if (result){
                this.getIhmChannelController().getInterfaceToCommunication().deleteChannel(currentChannel.getId());
            }
        }else{
            informationPopup("Vous n'avez pas les droits nécessaires pour effectuer cette action. Seul le créateur peut supprimer le channel.");
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
        String nickname = getCurrentChannel().getNickNames().get(user.getId().toString());
        if(nickname != null){
            user.setNickName(nickname);
        }
        this.connectedMembersList.add(user);
        this.channelMessagesDisplay.getController().addMemberToObservableList(user);
        this.channelMembersDisplay.getController().addMemberToObservableList(user);

        try{
            this.addUser(user);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void removeConnectedUser(UserLite user) {
        this.connectedMembersList.remove(user);
        this.channelMessagesDisplay.getController().removeMemberFromObservableList(user);
        this.channelMembersDisplay.getController().removeMemberFromObservableList(user);
    }

    public void likeMessage(Message message, UserLite user) {
        channelMessagesController.likeMessage(message, user);
    }

    public void deleteMessage(Message message, boolean deletedByCreator) {
        channelMessagesDisplay.getController().getMessagesMap().get(message.getId()).replaceDeletedMessage(deletedByCreator);
    }

    public ChannelPageController getChannelPageController() {
        return channelPageController;
    }

    public void setChannelPageController(ChannelPageController channelPageController) {
        this.channelPageController = channelPageController;
    }



    public void editMessage(Message message, Message newMessage) {
        //màj copie locale
        for(Message m : currentChannel.getMessages()){
            if(m.getId().equals(message.getId())){
                m.setMessage(newMessage.getMessage());
                m.setEdited(true);
            }
        }
        //màj interface
        channelMessagesController.editMessage(message);
    }

    public void changeNickname(UserLite user, String newNickname) {
        currentChannel.getNickNames().replace(user.getId().toString(), newNickname);
        try {
            //Transfert vue messages
            channelMessagesController.changeNickname();
            //Transfert vue members
            channelMembersDisplay.getController().changeNickname();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUI(String name, String description) {
        channelName.setText(name);
        channelDescription.setText(description);

        iconsInit();
    }
    public void removeKick(Kick kick) {
        List<Kick> kickList = currentChannel.getKicked();
        kickList.remove(kick);
        currentChannel.setKicked(kickList);
        
        if(currentChannel.getVisibility().equals(Visibility.PRIVATE)){
            getIhmChannelController().getInterfaceToCommunication().sendInvite(kick.getUser(),currentChannel,"Votre bannissement a été annulé.");
        }
    }
}
