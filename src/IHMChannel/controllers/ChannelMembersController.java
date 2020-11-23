package IHMChannel.controllers;

import IHMChannel.MemberDisplay;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.*;

/**
 * Contrôleur de la vue "ChannelMembers" qui contient la liste des membres d'un channel et les options d'affichage de cette liste
 */
public class ChannelMembersController {
    @FXML
    ListView<HBox> membersList;
    @FXML
    ToggleGroup viewMode;
    @FXML
    GridPane members;

    Channel channel;

    ObservableList<UserLite> channelMembers ;
    ObservableList<UserLite> adminMembers;
    UserLite creator;

    ObservableList<HBox> membersToDisplay;

    /**
     * Initialise la liste des membres (acceptedPerson) contenus dans l'attribut channel de la classe
     */
    private void initMembersList() {
        channelMembers.removeAll(channelMembers);
        for (UserLite usr : this.channel.getAcceptedPersons()){
            channelMembers.add(usr);
        }
        adminMembers.removeAll(adminMembers);
        for (UserLite usr : this.channel.getAdministrators()){
            adminMembers.add(usr);
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
            membersToDisplay.add((HBox) new MemberDisplay(usr).root);
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


    public ChannelMembersController(){
    }

    public void initialize() throws IOException {
        channelMembers = FXCollections.observableArrayList();
        adminMembers = FXCollections.observableArrayList();
        membersToDisplay = FXCollections.observableArrayList();
    }

    public void alphabeticSort() throws IOException {
        channelMembers.sort(Comparator.comparing(UserLite::getNickName));
        //channelMembers.forEach(user -> System.out.println(user.getNickName()));
        displayMembers();
    }


    public void adminSort() {
        adminMembers.forEach(user -> System.out.println(user.getNickName()));

        channelMembers.forEach(user ->{
            if(!adminMembers.contains(user))
            System.out.println(user.getNickName());
        });

    }

    public void onlineUserSort() {
        /* TO - DO quand on pourra savoir qui est connecté.

        channelMembers.sort(Comparator.comparing(UserLite::isConnected));
        displayMembers();

        */
    }
}
