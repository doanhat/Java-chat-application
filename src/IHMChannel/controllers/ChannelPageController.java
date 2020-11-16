package IHMChannel.controllers;

import IHMChannel.ChannelMembersDisplay;
import IHMChannel.ChannelMessagesDisplay;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Contrôleur de la partie Channel de l'interface.
 * TODO ajouter les interfaces
 */
public class ChannelPageController {
    int currentChannel; //channel à afficher dans l'interface
    UserLite connectedUser; //tmp
    ObservableSet<Channel> openedChannels;
    HashMap<Integer, ChannelController > channelMap;
    

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


    /**
     * Setter du channel
     * Met à jour l'attribut channel du contrôleur (= celui qu'affiche la page)
     * Met à jour le nom et la description du channel sur l'interface.
     * @param channel
     */


    public void addOpennedChannel(Channel channel) throws IOException {
        openedChannels.add(channel);
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/Channel.fxml"));
        fxmlLoader.load();
        ChannelController ctrl = fxmlLoader.getController();
        ctrl.setChannel(channel);
        channelMap.put(channel.getId(), ctrl);
    }

    /**
     * Constructeur : Appelé par le FXML Loader.
     * On y fait l'initialisation des données (et non pas de l'affichage)
     */
    public ChannelPageController(){
        //tmp
        // permet d'avoir un utilisateur temporaire pour l'affichage des messages
        connectedUser = new UserLite();
        connectedUser.setNickName("Léa");
    }
    /**
     * Automatically called by FXML Loader
     */
    public void initialize() throws IOException {
        /* Dans cette méthode, on met tout ce qui doit être fait à l'init de la vue.
        Par exemple, le chargement des messages du channel, l'affichage de la photo de profil de l'utilisateur connecté près de la zone de message,...
        Cette méthode contient aussi les LISTENERS
        */

        iconsInit();

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

    /**
     * Méthode déclenchée au clic sur le bouton "voir les membres"
     */
    public void seeMembers(){

        channelMap.get(currentChannel).seeMembers();
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
     * /!\ Bindée au bouton "back" pour test
     */
    public void receiveMessage(){
        channelMap.get(currentChannel).receiveMessage();
    }


}
