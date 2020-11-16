package IHMChannel.controllers;

import IHMChannel.ChannelMembersDisplay;
import IHMChannel.ChannelMessagesDisplay;
import common.sharedData.Channel;
import common.sharedData.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.Iterator;

public class ChannelController {
    Channel currentChannel; //channel à afficher dans l'interface

    @FXML
    BorderPane pageToDisplay;
    @FXML
    Text channelName;
    @FXML
    Text channelDescription;

    ChannelMessagesDisplay channelMessagesDisplay;

    ChannelMembersDisplay channelMembersDisplay;

    Boolean seeMessages = true;
    public void receiveMessage() {
        //Message newMsg = new Message(99,"Salut, je suis un message reçu via le bouton de test",connectedUser);
        //currentChannel.addMessage(newMsg);
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
        System.out.println("ChannelController.setChannel : "+channel);
        this.currentChannel = channel;
        channelName.setText(channel.getName());
        channelDescription.setText(channel.getDescription());

    }

}
