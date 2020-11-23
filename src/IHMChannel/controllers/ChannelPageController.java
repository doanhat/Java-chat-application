package IHMChannel.controllers;

import IHMChannel.ChannelMembersDisplay;
import IHMChannel.ChannelMessagesDisplay;
import IHMChannel.IHMChannelController;
import common.IHMTools.IHMTools;
import common.sharedData.*;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
    private TextField canalText;

    @FXML
    private Button createCanalBtn;

    @FXML
    BorderPane pageToDisplay;

    @FXML
    TabPane tabs;

    @FXML
    AnchorPane channelPageAnchor;


    /**
     * Setter du channel
     * Met à jour l'attribut channel du contrôleur (= celui qu'affiche la page)
     * Met à jour le nom et la description du channel sur l'interface.
     * @param channel
     */


    public void addOpenedChannel(Channel channel) throws IOException {
        iconsInit();
        openedChannels.add(channel);

        //Création du nouvel onglet pour le channel ajouté

        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/IHMChannel/views/Channel.fxml"));
        Parent root = fxmlLoader.load();

        ChannelController ctrl = fxmlLoader.getController();
        ctrl.setChannel(channel);
        ctrl.setIhmChannelController(ihmChannelController);
        ctrl.getIhmChannelController().setChannelPageController(this);
        ctrl.configureMessageDisplay(ihmChannelController);
        Tab tab = new Tab(channel.getName());
        tab.setId(channel.getName());
        tab.setOnClosed((event->{
            this.openedChannels.remove(channel);
        }));
        tabs.getTabs().add(tab);
        tab.setContent((Node) root);
        tabs.getSelectionModel().select(tab);
        //IHMTools.fitSizeToParent((Region) ihmChannelController.getRoot(), (Region) root);
        //tab1.setGraphic(root);


        channelMap.put(channel.getId(), ctrl);
    }

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
     * Automatically called by FXML Loader
     */
    public void initialize() throws IOException {
        /* Dans cette méthode, on met tout ce qui doit être fait à l'init de la vue.
        Par exemple, le chargement des messages du channel, l'affichage de la photo de profil de l'utilisateur connecté près de la zone de message,...
        Cette méthode contient aussi les LISTENERS
        */
        //iconsInit();


    }



    private void iconsInit(){
        //Accueil
        Image usersImage = new Image("IHMChannel/icons/home.png");
        ImageView usersIcon = new ImageView(usersImage);
        usersIcon.setFitHeight(15);
        usersIcon.setFitWidth(15);
        back.setGraphic(usersIcon);

//        //Liste membres
//        Image usersImage = new Image("IHMChannel/icons/users-solid.png");
//        ImageView usersIcon = new ImageView(usersImage);
//        usersIcon.setFitHeight(15);
//        usersIcon.setFitWidth(15);
//        seeMembersBtn.setGraphic(usersIcon);
//
//        //Ajout membre
//        Image addUserImage = new Image("IHMChannel/icons/user-plus-solid.png");
//        ImageView addUserIcon = new ImageView(addUserImage);
//        addUserIcon.setFitHeight(15);
//        addUserIcon.setFitWidth(15);
//        addMemberBtn.setGraphic(addUserIcon);
//
//        //Quitter
//        Image exitImage = new Image("IHMChannel/icons/exit.png");
//        ImageView exitIcon = new ImageView(exitImage);
//        exitIcon.setFitHeight(15);
//        exitIcon.setFitWidth(15);
//        leaveChannelBtn.setGraphic(exitIcon);
    }

    /**
     * Méthode déclenchée au clic sur le bouton "voir les membres"
     */
    public void seeMembers(){
//        channelMap.get(currentChannel).seeMembers();
    }

    /**
     * Méthode déclenchée au clic sur le bouton "ajouter un membre"
     */
    public void addUserToChannel(){

    }

    /**
     * Méthode déclenchée au clic sur le bouton "quitter le channel"
     */
    public void leaveChannel(){
      /*  openedChannels.remove(channelMap.get(currentChannel));
        channelMap.remove(currentChannel)*/
    }

    /**
     * Méthode de test déclenchée à l'appui sur le bouton "test réception"
     * Génère l'ajout d'un message dans la liste de messages du channel.
     */
    public void receiveMessage(){
        //channelMap.get(currentChannel).receiveMessage();
    }


    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    public ChannelController getChannelController(int channelId){
        ChannelController channelController = channelMap.get(channelId);
        return channelController;
    }

    @FXML
    void creatCanal() throws IOException {
        String channelName = canalText.getText();
        int count = 0;
        int maxid = 0;
        Channel selectChannel;
        for (Channel c : openedChannels) {
            if (c.getName().equals(channelName)) {
                count = 1;
                selectChannel = c;
                break;
            }
        }

        for (Channel c : openedChannels) {
            if (c.getId() > maxid) {
                maxid = c.getId();
            }
        }
//        System.out.println(count);
        if (count == 0) {
            Channel c = new OwnedChannel(maxid + 1, channelName, new UserLite(UUID.randomUUID(), "Léa", null), "channel pour l'UV " + channelName, Visibility.PUBLIC);
            this.addOpenedChannel(c);
        }
        else {
            for (Tab c : tabs.getTabs()) {
                if (c.getId().equals(channelName)) {
                    tabs.getSelectionModel().select(c);
                }
            }
        }
    }

}
