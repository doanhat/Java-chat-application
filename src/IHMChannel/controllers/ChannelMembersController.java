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


    ArrayList<UserLite> channelMembers;

    ObservableList<HBox> membersToDisplay = FXCollections.observableArrayList();

    Channel channel;


    ListChangeListener<UserLite> membersListListener;
    ListChangeListener<UserLite> adminsListListener;

    /**
     * Initialise la liste des membres (acceptedPerson) contenus dans l'attribut channel de la classe
     */
    private void initMembersList() {
        channelMembers.removeAll(channelMembers);
        for (UserLite usr : this.channel.getAcceptedPersons()){
            channelMembers.add(usr);
        }
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

        // A remettre pour utiliser les users du channel // Pour le moment, on utilise les données brutes en exemples.
        //initMembersList();
        //displayMembers();
    }


    public ChannelMembersController(){
        channelMembers = new ArrayList<UserLite>();
    }

    public void initialize() throws IOException {
        //Membres

        UserLite user1 = new UserLite(UUID.randomUUID(), "Léa", null);
        channelMembers.add(user1);
        UserLite user2 = new UserLite(UUID.randomUUID(), "Aida", null);
        channelMembers.add(user2);
        UserLite user3 = new UserLite(UUID.randomUUID(), "Lucas", null);
        channelMembers.add(user3);
        UserLite user4 = new UserLite(UUID.randomUUID(), "Vladimir", null);
        channelMembers.add(user4);
        UserLite user5 = new UserLite(UUID.randomUUID(), "Jérôme", null);
        channelMembers.add(user5);
        UserLite user6 = new UserLite(UUID.randomUUID(), "Van-Triet", null);
        channelMembers.add(user6);

        displayMembers();
    }

    public void alphabeticSort() throws IOException {
        channelMembers.sort(Comparator.comparing(UserLite::getNickName));
        channelMembers.forEach(user -> System.out.println(user.getNickName()));
        displayMembers();
    }


    public void adminSort() {

    }

    public void onlineUserSort() {
        /* TO - DO quand on pourra savoir qui est connecté.

        channelMembers.sort(Comparator.comparing(UserLite::isConnected));
        displayMembers();

        */
    }
}
