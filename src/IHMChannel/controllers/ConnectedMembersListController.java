package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import IHMChannel.MemberDisplay;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class ConnectedMembersListController {

    private IHMChannelController ihmChannelController;
    Channel channel;

    @FXML
    ListView<HBox> connectedMembers;
    @FXML
    ListView<HBox> disconnectedMembers;

    ObservableList<UserLite> channelMembers ;
    ObservableList<UserLite> adminMembers;

    List<UserLite> connectedMembersList;
    UserLite creator;
    UserLite localUser;

    boolean isLocalUserAdmin = false;

    ObservableList<HBox> connectedMembersToDisplay;
    ObservableList<HBox> disconnectedMembersToDisplay;

    /**
     * Initialise la liste des membres contenus dans l'attribut channel de la classe
     */
    private void initMembersList() {
        channelMembers.clear();
        adminMembers.clear();
        for (UserLite usr : this.channel.getAuthorizedPersons()){
            channelMembers.add(usr);
        }
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

    private void displayMembers() {
        connectedMembersToDisplay.clear();
        disconnectedMembersToDisplay.clear();

        for (UserLite usr : channelMembers){
            if(connectedMembersList!=null && connectedMembersList.contains(usr)){
                if(usr.getId().equals(creator.getId())){
                    connectedMembersToDisplay.add((HBox) new MemberDisplay(usr,true,true,true,false, channel, ihmChannelController).root);
                }else if(adminMembers.contains(usr)){
                    connectedMembersToDisplay.add((HBox) new MemberDisplay(usr, true,false,true,isLocalUserAdmin,  channel, ihmChannelController).root);
                }else{
                    connectedMembersToDisplay.add((HBox) new MemberDisplay(usr, false, false,true,isLocalUserAdmin, channel, ihmChannelController).root);
                }
            }else{
                if(usr.getId().equals(creator.getId())){
                    disconnectedMembersToDisplay.add((HBox) new MemberDisplay(usr,true,true,false,false, channel, ihmChannelController).root);
                }else if(adminMembers.contains(usr)){
                    disconnectedMembersToDisplay.add((HBox) new MemberDisplay(usr, true,false,false,isLocalUserAdmin,  channel, ihmChannelController).root);
                }else{
                    disconnectedMembersToDisplay.add((HBox) new MemberDisplay(usr, false, false,false,isLocalUserAdmin, channel, ihmChannelController).root);
                }
            }
        }
        connectedMembers.setItems(connectedMembersToDisplay);
        disconnectedMembers.setItems(disconnectedMembersToDisplay);
    }

    /**
     * Setter du channel
     * Met à jour la liste des membres en conséquence
     * @param channel
     */
    public void setCurrentChannel(Channel channel)  {
        this.channel = channel;
        initMembersList();
        displayMembers();
    }

    public ConnectedMembersListController(){

    };

    public void initialize() {
        channelMembers = FXCollections.observableArrayList();
        adminMembers = FXCollections.observableArrayList();
        connectedMembersToDisplay = FXCollections.observableArrayList();
        disconnectedMembersToDisplay = FXCollections.observableArrayList();
        connectedMembersList = FXCollections.observableArrayList();
    }

    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
        localUser = ihmChannelController.getInterfaceToData().getLocalUser().getUserLite();
    }

    public List<UserLite> getConnectedMembersList() {
        return connectedMembersList;
    }
    public void setConnectedMembersList(List<UserLite> updatedConnectedMembersList) {
        if(this.connectedMembersList != null){
            this.connectedMembersList.clear();
        }
        this.connectedMembersList = updatedConnectedMembersList;
        displayMembers();
    }

    public void addMemberToList(UserLite user) {
        connectedMembersList.add(user);
        displayMembers();
    }

    public void removeMemberFromList(UserLite user) {
        connectedMembersList.remove(user);
        displayMembers();
    }
}
