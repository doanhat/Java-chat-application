package IHMChannel.controllers;

import IHMChannel.ChannelMembersDisplay;
import IHMChannel.ChannelMessagesDisplay;
import IHMChannel.IHMChannelController;
import common.sharedData.Channel;
import common.sharedData.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Contrôleur de la vue views/Channel.fxml
 */
public class ChannelController {
    private Channel currentChannel; //channel à afficher dans l'interface
    private IHMChannelController ihmChannelController;

<<<<<<< HEAD

    /*
       ** @author : Triet
     */
    private ChannelPageController channelPageController;

=======
    ChannelMessagesDisplay channelMessagesDisplay;
    ChannelMembersDisplay channelMembersDisplay;

    Boolean seeMessages = true;
>>>>>>> integration_POC_et_CSS

    @FXML
    BorderPane pageToDisplay;
    @FXML
    Text channelName;
    @FXML
    Text channelDescription;

    @FXML
    Button back;
    @FXML
    Button seeMembersBtn;
    @FXML
    Button addMemberBtn;
    @FXML
    Button leaveChannelBtn;

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
     * Getter de l'objet Channel lié à l'interface gérée par ce contrôleur
     * @return objet Channel lié à l'interface gérée par ce contrôleur
     */
    public Channel getCurrentChannel() {
        return currentChannel;
    }

    /**
     * Setter de l'objet Channel lié à l'interface gérée par ce contrôleur
     * @param currentChannel objet Channel lié à l'interface gérée par ce contrôleur
     */
    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }

    public void initialize() throws IOException {
        iconsInit();
        //Affichage de la partie "messages"
        channelMessagesDisplay = new ChannelMessagesDisplay();
        pageToDisplay.setCenter(channelMessagesDisplay.root);

        //Chargement de la liste des utilisateurs
        channelMembersDisplay = new ChannelMembersDisplay();

    }

    /**
     * Permet de configurer l'instance de MessageDisplay une fois son constructeur appelé.
     * En pratique : faire descendre la référence à IHMChannelController vers le contrôleur de la vue views/ChannelMessages.fxml
     * @param ihmChannelController
     */
    public void configureChannelMessageDisplay(IHMChannelController ihmChannelController){
        channelMessagesDisplay.configureChannelMessageController(ihmChannelController);
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
     * Traitement de la réception d'un nouveau message signalé par Data.
     * Est appelé dans interfaces/DataToIHMChannel.
     * @param receivedMessage
     * @param responseTo
     */
    public void receiveMessage(Message receivedMessage, Message responseTo) {
        currentChannel.addMessage(receivedMessage);
        channelMessagesDisplay.getController().addMessageToObservableList(receivedMessage);
    }

    /**
     * Appelée lorsque l'on clique sur le bouton seeMembersBtn.
     * Active/Désactive l'affichage de la liste des membres.
     */
    public void seeMembers() {
        if(seeMessages){
            pageToDisplay.setCenter(channelMembersDisplay.root);
            seeMessages=false;
        }
        else{
            pageToDisplay.setCenter(channelMessagesDisplay.root);
            seeMessages=true;
        }
    }

    /**
     * Appelée lorsque l'on clique sur le bouton addMemberBtn.
     */
    public void addUserToChannel() {
    }

    /**
     * Méthode déclenchée au clic sur le bouton "quitter le channel".
     * TODO: la déclencher aussi lorsque l'on ferme l'onglet de ce channel.
     */
    public void leaveChannel(){
      /*  openedChannels.remove(channelMap.get(currentChannel));
        channelMap.remove(currentChannel)*/
    }

    /**
     * Permet de configurer le channel relié à la vue contrôlée par cette classe et de faire les affichages nécessaires.
     * @param channel
     */
    public void setChannel(Channel channel) {
//        System.out.println("ChannelController.setChannel : "+channel);
        this.setCurrentChannel(channel);
        channelName.setText(channel.getName());
        channelDescription.setText(channel.getDescription());

        //On transmet aux 2 "sous-vues" le channel à afficher et chacune fait le traitement nécessaire
        channelMessagesDisplay.getController().setCurrentChannel(channel);
        //channelMembersDisplay.getController().setCurrentChannel(channel);


    }





}
