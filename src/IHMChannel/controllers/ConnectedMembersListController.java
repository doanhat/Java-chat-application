package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import IHMChannel.MemberDisplay;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    //Link Member's UUID and MemberController of the user's HBox
    private HashMap<UUID, MemberController> mapMemberController = new HashMap<UUID, MemberController>();

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

    private boolean containsUser(List<UserLite> list, UserLite user){
        for(UserLite u : list){
            if(u.getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Permet l'affichage de la liste des membres en faisant une conversion en Hbox.
     * @throws IOException
     */

    private void displayMembers() {
        connectedMembersToDisplay.clear();
        disconnectedMembersToDisplay.clear();
        mapMemberController.clear();

        for (UserLite usr : channelMembers){
            if(connectedMembersList!=null && containsUser(connectedMembersList, usr)){
                if(usr.getId().equals(creator.getId())){
                    MemberDisplay memberDisplay = new MemberDisplay(usr,true,true,true,false, channel, ihmChannelController);
                    connectedMembersToDisplay.add((HBox) memberDisplay.root);
                    mapMemberController.put(usr.getId(),memberDisplay.getController());
                }else if(adminMembers.contains(usr)){
                    MemberDisplay memberDisplay = new MemberDisplay(usr, true,false,true,isLocalUserAdmin,  channel, ihmChannelController);
                    connectedMembersToDisplay.add((HBox) memberDisplay.root);
                    mapMemberController.put(usr.getId(),memberDisplay.getController());
                }else{
                    MemberDisplay memberDisplay = new MemberDisplay(usr, false, false,true,isLocalUserAdmin, channel, ihmChannelController);
                    connectedMembersToDisplay.add((HBox) memberDisplay.root);
                    mapMemberController.put(usr.getId(),memberDisplay.getController());
                }
            }else{
                if(usr.getId().equals(creator.getId())){
                    MemberDisplay memberDisplay = new MemberDisplay(usr,true,true,false,false, channel, ihmChannelController);
                    disconnectedMembersToDisplay.add((HBox) memberDisplay.root);
                    mapMemberController.put(usr.getId(),memberDisplay.getController());
                }else if(adminMembers.contains(usr)){
                    MemberDisplay memberDisplay = new MemberDisplay(usr, true,false,false,isLocalUserAdmin,  channel, ihmChannelController);
                    disconnectedMembersToDisplay.add((HBox) memberDisplay.root);
                    mapMemberController.put(usr.getId(),memberDisplay.getController());
                }else{
                    MemberDisplay memberDisplay = new MemberDisplay(usr, false, false,false,isLocalUserAdmin, channel, ihmChannelController);
                    disconnectedMembersToDisplay.add((HBox) memberDisplay.root);
                    mapMemberController.put(usr.getId(),memberDisplay.getController());
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

    public void addMemberToConnectedMembersList(UserLite user) {
        connectedMembersList.add(user);
        displayMembers();
    }

    public void removeMemberConnectedMembersFromList(UserLite user) {
        connectedMembersList.remove(user);
        displayMembers();
    }

    public void changeNickname(UserLite user) {
        mapMemberController.get(user.getId()).changeNickname(user.getNickName());
    }
}
