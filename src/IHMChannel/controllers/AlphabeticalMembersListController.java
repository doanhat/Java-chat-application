package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import IHMChannel.MemberDisplay;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class AlphabeticalMembersListController {
    private IHMChannelController ihmChannelController;

    @FXML
    ListView<HBox> membersList;

    Channel channel;

    List<UserLite> connectedMembersList;
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
        channelMembers.clear();
        for (UserLite usr : this.channel.getAuthorizedPersons()){
            channelMembers.add(usr);
        }
        adminMembers.clear();
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
        membersToDisplay.clear();
        //TODO : le tri fonctionne, mais il devrait y avoir moyen de faire cela automatiquement avec JavaFX (possiblement s'inspirer de la liste dans homepage)
        channelMembers.sort(Comparator.comparing(UserLite::getNickName));
        for (UserLite usr : channelMembers){
            if(usr.getId().equals(creator.getId())){
                membersToDisplay.add((HBox) new MemberDisplay(usr,true,true,(connectedMembersList!=null && containsUser(connectedMembersList, usr)),false, channel, ihmChannelController).root);
            }else if(adminMembers.contains(usr)){
                membersToDisplay.add((HBox) new MemberDisplay(usr, true,false,(connectedMembersList!=null && containsUser(connectedMembersList, usr)),isLocalUserAdmin,  channel, ihmChannelController).root);
            }else{
                membersToDisplay.add((HBox) new MemberDisplay(usr, false, false,(connectedMembersList!=null && containsUser(connectedMembersList, usr)),isLocalUserAdmin, channel, ihmChannelController).root);
            }
        }
        membersList.setItems(membersToDisplay);
    }

    /**
     * Setter du channel
     * Met à jour la liste des membres en conséquence
     * @param channel
     */
    public void setCurrentChannel(Channel channel) {
        this.channel = channel;
        initMembersList();
        displayMembers();
    }


    public void initialize() {
        channelMembers = FXCollections.observableArrayList();
        adminMembers = FXCollections.observableArrayList();
        membersToDisplay = FXCollections.observableArrayList();
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
