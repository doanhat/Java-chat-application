package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import IHMChannel.MemberDisplay;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class AlphabeticalMembersListController {

    private IHMChannelController ihmChannelController;

    @FXML
    ListView<HBox> membersList;

    Channel channel;

    ObservableList<UserLite> channelMembers ;
    ObservableList<UserLite> adminMembers;
    UserLite creator;

    UserLite localUser;
    boolean isLocalUserAdmin = false;

    ObservableList<HBox> membersToDisplay;

    /**
     * Initialise la liste des membres contenus dans l'attribut channel de la classe
     */
    private void initMembersList() {
        channelMembers.removeAll(channelMembers);
        for (UserLite usr : this.channel.getAcceptedPersons()){
            channelMembers.add(usr);
        }
        adminMembers.removeAll(adminMembers);
        for (UserLite usr : this.channel.getAdministrators()){
            adminMembers.add(usr);
            if(usr.getId().equals(localUser.getId())) {
                isLocalUserAdmin = true;
            }
        }
        creator = this.channel.getCreator();
    }

    /**
     * Permet l'affichage de la liste des membres en faisant une conversion en Hbox.
     * @throws IOException
     */

    private void displayMembers() throws IOException {
        membersToDisplay.removeAll(membersToDisplay);
        for (UserLite usr : channelMembers){
            if(usr.getId().equals(creator.getId())){
                membersToDisplay.add((HBox) new MemberDisplay(usr,true,true,true,false, channel, ihmChannelController).root);
            }else if(adminMembers.contains(usr)){
                membersToDisplay.add((HBox) new MemberDisplay(usr, true,false,true,isLocalUserAdmin,  channel, ihmChannelController).root);
            }else{
                membersToDisplay.add((HBox) new MemberDisplay(usr, false, false,true,isLocalUserAdmin, channel, ihmChannelController).root);
            }
        }
        membersList.setItems(membersToDisplay);
    }

    /**
     * Setter du channel
     * Met à jour la liste des membres en conséquence
     * @param channel
     */
    public void setCurrentChannel(Channel channel) throws IOException {
        this.channel = channel;
        initMembersList();
        displayMembers();
    }

    public AlphabeticalMembersListController(){

    };

    public void initialize() throws IOException {
        channelMembers = FXCollections.observableArrayList();
        adminMembers = FXCollections.observableArrayList();
        membersToDisplay = FXCollections.observableArrayList();
    }

    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
        localUser = ihmChannelController.getInterfaceToData().getLocalUser().getUserLite();
    }
}
