package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Contrôleur de la partie Channel de l'interface.
 * TODO ajouter les interfaces
 */
public class ChannelPageController {
    int currentChannel; //channel à afficher dans l'interface
    UserLite connectedUser; //tmp
    ObservableSet<Channel> openedChannels; //channel auxquels l'utilisateur est connecté
    HashMap<Integer, ChannelController > channelMap;
    private IHMChannelController ihmChannelController;

    @FXML
    Button back;
    @FXML
    Text channelName;
    @FXML
    Text channelDescription;
    @FXML
    Button seeMembersBtn;
    @FXML
    Button addMemberBtn;
    @FXML
    Button leaveChannelBtn;

    @FXML
    BorderPane pageToDisplay;

    @FXML
    TabPane tabs;

    /**
     * Constructeur : Appelé par le FXML Loader.
     * On y fait l'initialisation des données (et non pas de l'affichage)
     */
    public ChannelPageController(){
        //tmp
        // permet d'avoir un utilisateur temporaire pour l'affichage des messages
        connectedUser = new UserLite(UUID.randomUUID(), "newUser", null);
        connectedUser.setNickName("Léa");

        //initialisation de oppenedChannel
        openedChannels = FXCollections.observableSet();
        channelMap = new HashMap<Integer, ChannelController>();
    }

    /**
     * Getter du contrôleur principal de IHM-Channel
     * @return le contrôleur principal de IHM-Channel
     */
    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    /**
     * Setter du contrôleur principal de IHM-Channel
     * @param ihmChannelController le contrôleur principal de IHM-Channel
     */
    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    /**
     * Getter du contrôleur de la vue correspondant au channel dont l'id est channelId
     * @param channelId id du channel lié à la vue dont on veut obtenir le contrôleur
     * @return le contrôleur de la vue correspondant au channel dont l'id est channelId
     */
    public ChannelController getChannelController(int channelId){
        ChannelController channelController = channelMap.get(channelId);
        return channelController;
    }

    /**
     * Automatically called by FXML Loader
     */
    public void initialize() throws IOException {
        /* Dans cette méthode, on met tout ce qui doit être fait à l'init de la vue.
        Par exemple, le chargement des messages du channel, l'affichage de la photo de profil de l'utilisateur connecté près de la zone de message,...
        Cette méthode contient aussi les LISTENERS
        */
        //iconsInit();
    }

    /**
     * Ajoute channel à la liste des channels ouverts et l'affiche dans un nouvel onglet.
     * @param channel nouveau channel à afficher
     * @throws IOException
     */
    public void addOpenedChannel(Channel channel) throws IOException {
        openedChannels.add(channel);
        //Création du nouvel onglet pour le channel ajouté

        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/IHMChannel/views/Channel.fxml"));
        Parent root = fxmlLoader.load();

        ChannelController ctrl = fxmlLoader.getController();
        ctrl.setChannel(channel);
        ctrl.setIhmChannelController(ihmChannelController);
        ctrl.getIhmChannelController().setChannelPageController(this);
        ctrl.configureChannelMessageDisplay(ihmChannelController);
        Tab tab = new Tab(channel.getName());
        tabs.getTabs().add(tab);
        tab.setContent((Node) root);

        channelMap.put(channel.getId(), ctrl);
    }


    private void iconsInit(){
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
    }

//    /**
//     * Méthode déclenchée au clic sur le bouton "voir les membres"
//     */
//    public void seeMembers(){
//
//        channelMap.get(currentChannel).seeMembers();
//    }
//
//    /**
//     * Méthode déclenchée au clic sur le bouton "ajouter un membre"
//     */
//    public void addUserToChannel(){
//
//    }
//
//    /**
//     * Méthode déclenchée au clic sur le bouton "quitter le channel"
//     */
//    public void leaveChannel(){
//      /*  openedChannels.remove(channelMap.get(currentChannel));
//        channelMap.remove(currentChannel)*/
//    }
//
//    /**
//     * Méthode de test déclenchée à l'appui sur le bouton "test réception"
//     * Génère l'ajout d'un message dans la liste de messages du channel.
//     */
//    public void receiveMessage(){
//        //channelMap.get(currentChannel).receiveMessage();
//    }



}
