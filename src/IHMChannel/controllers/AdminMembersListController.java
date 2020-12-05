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

public class AdminMembersListController {

    private IHMChannelController ihmChannelController;

    @FXML
    ListView<HBox> creatorList;
    @FXML
    ListView<HBox> adminList;
    @FXML
    ListView<HBox> membersList;

    Channel channel;

    ObservableList<UserLite> channelMembers ;
    ObservableList<UserLite> adminMembers;
    UserLite creator;
    UserLite localUser;
    boolean isLocalUserAdmin = false;

    ObservableList<HBox> creatorToDisplay;
    ObservableList<HBox> adminsToDisplay;
    ObservableList<HBox> membersToDisplay;

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

        for (UserLite usr : adminMembers){
            adminsToDisplay.add((HBox) new MemberDisplay(usr,true,false,true,isLocalUserAdmin ,  channel, ihmChannelController).root);
        }
        for (UserLite usr : channelMembers){
            membersToDisplay.add((HBox) new MemberDisplay(usr,false,false,true,isLocalUserAdmin, channel, ihmChannelController).root);
        }
        creatorToDisplay.add((HBox) new MemberDisplay(creator,true,true,true,false, channel, ihmChannelController).root);

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

    public AdminMembersListController(){

    };

    public void initialize() throws IOException {
        channelMembers = FXCollections.observableArrayList();
        adminMembers = FXCollections.observableArrayList();
        membersToDisplay = FXCollections.observableArrayList();
        creatorToDisplay = FXCollections.observableArrayList() ;
        adminsToDisplay= FXCollections.observableArrayList();
    }

    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
        localUser = ihmChannelController.getInterfaceToData().getLocalUser().getUserLite();
    }
}
