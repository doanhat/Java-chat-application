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

public class AdminMembersListController {

    private IHMChannelController ihmChannelController;

    @FXML
    ListView<HBox> creatorList;
    @FXML
    ListView<HBox> adminList;
    @FXML
    ListView<HBox> membersList;

    Channel channel;

    List<UserLite> connectedMembersList;
    ObservableList<UserLite> channelMembers ;
    ObservableList<UserLite> adminMembers;
    UserLite creator;
    UserLite localUser;
    boolean isLocalUserAdmin = false;

    ObservableList<HBox> creatorToDisplay;
    ObservableList<HBox> adminsToDisplay;
    ObservableList<HBox> membersToDisplay;

    //Link Member's UUID and MemberController of the user's HBox
    private HashMap<UUID, MemberController> mapMemberController = new HashMap<UUID, MemberController>();

    /**
     * Initialise la liste des membres (acceptedPerson) contenus dans l'attribut channel de la classe
     */
    private void initMembersList() {
        channelMembers.clear();
        adminMembers.clear();

        creator = this.channel.getCreator();

        isLocalUserAdmin = false;
        this.channel.getAdministrators().forEach(userLite -> {
            // On n'ajoute pas le créateur dans cette liste.
            if(!userLite.getId().equals(creator.getId())){adminMembers.add(userLite);}

            // On regarde si le localUser est admin
            if(userLite.getId().equals(localUser.getId())) {
                isLocalUserAdmin = true;
            }
        });

        this.channel.getAcceptedPersons().forEach(userLite -> {
            if(!adminMembers.contains(userLite) && !userLite.getId().equals(creator.getId())){channelMembers.add(userLite);}
        });

    }

    /**
     * Permet l'affichage de la liste des membres en faisant une conversion en Hbox.
     * @throws IOException
     */

    private void displayMembers() throws IOException {

        membersToDisplay.clear();
        adminsToDisplay.clear();
        mapMemberController.clear();

        for (UserLite usr : adminMembers){
            MemberDisplay memberDisplay = new MemberDisplay(usr,true,false,(connectedMembersList!=null && connectedMembersList.contains(usr)),isLocalUserAdmin ,  channel, ihmChannelController);
            adminsToDisplay.add((HBox) memberDisplay.root);
            mapMemberController.put(usr.getId(),memberDisplay.getController());
        }
        for (UserLite usr : channelMembers){
            MemberDisplay memberDisplay = new MemberDisplay(usr,false,false,(connectedMembersList!=null && connectedMembersList.contains(usr)),isLocalUserAdmin, channel, ihmChannelController);
            membersToDisplay.add((HBox) memberDisplay.root);
            mapMemberController.put(usr.getId(),memberDisplay.getController());
        }
        //Creator
        MemberDisplay memberDisplay = new MemberDisplay(creator,true,true,(connectedMembersList!=null && connectedMembersList.contains(creator)),false, channel, ihmChannelController);
        creatorToDisplay.add((HBox) memberDisplay.root);
        mapMemberController.put(creator.getId(),memberDisplay.getController());

        adminList.setItems(adminsToDisplay);
        membersList.setItems(membersToDisplay);
        creatorList.setItems(creatorToDisplay);
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

    public void initialize(){
        channelMembers = FXCollections.observableArrayList();
        adminMembers = FXCollections.observableArrayList();
        membersToDisplay = FXCollections.observableArrayList();
        creatorToDisplay = FXCollections.observableArrayList() ;
        adminsToDisplay= FXCollections.observableArrayList();
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
        // this.initConnectedMembersList();
    }

    public void addMemberToList(UserLite user) {
        connectedMembersList.add(user);
    }

    public void removeMemberFromList(UserLite user) {
        connectedMembersList.remove(user);
    }

    public void changeNickname(UserLite user) {
        mapMemberController.get(user.getId()).changeNickname(user.getNickName());
    }
}
