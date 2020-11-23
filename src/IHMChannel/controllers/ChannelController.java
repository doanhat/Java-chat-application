package IHMChannel.controllers;

import IHMChannel.ChannelMembersDisplay;
import IHMChannel.ChannelMessagesDisplay;
import IHMChannel.IHMChannelController;
import common.IHMTools.IHMTools;
import common.sharedData.Channel;
import common.sharedData.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChannelController {
    private Channel currentChannel; //channel à afficher dans l'interface
    private IHMChannelController ihmChannelController;

    /*
       ** @author : Triet
     */
    private ChannelPageController channelPageController;


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
    @FXML
    AnchorPane channel;

    ChannelMessagesDisplay channelMessagesDisplay;

    ChannelMembersDisplay channelMembersDisplay;

    Boolean seeMessages = true;

    public void initialize() throws IOException {
        iconsInit();
        //Affichage de la partie "messages"
        channelMessagesDisplay = new ChannelMessagesDisplay();
        pageToDisplay.setCenter(channelMessagesDisplay.root);

        //Chargement de la liste des utilisateurs
        channelMembersDisplay = new ChannelMembersDisplay();

    }

    public void configureMessageDisplay(IHMChannelController ihmChannelController){
        channelMessagesDisplay.configureMessageController(ihmChannelController);
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

    public void receiveMessage(Message receivedMessage, Message responseTo) {

        currentChannel.addMessage(receivedMessage);
        channelMessagesDisplay.getController().addMessageToObservableList(receivedMessage);
    }

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

    public void addUserToChannel() {
    }



    public void setChannel(Channel channel) {
//        System.out.println("ChannelController.setChannel : "+channel);
        this.setCurrentChannel(channel);
        channelName.setText(channel.getName());
        channelDescription.setText(channel.getDescription());

        //On transmet aux 2 "sous-vues" le channel à afficher et chacune fait le traitement nécessaire
        channelMessagesDisplay.getController().setCurrentChannel(channel);
        //channelMembersDisplay.getController().setCurrentChannel(channel);


    }



    /**
     * Méthode déclenchée au clic sur le bouton "quitter le channel"
     */
    public void leaveChannel(){
      /*  openedChannels.remove(channelMap.get(currentChannel));
        channelMap.remove(currentChannel)*/
    }


    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }
}
