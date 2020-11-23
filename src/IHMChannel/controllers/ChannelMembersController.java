package IHMChannel.controllers;

import IHMChannel.MemberDisplay;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Contrôleur de la vue "ChannelMembers" qui contient la liste des membres d'un channel et les options d'affichage de cette liste
 */
public class ChannelMembersController {
    @FXML
    ListView membersList;
    @FXML
    ToggleGroup modeAffichage;
    @FXML
    Toggle alphaBtn;

    ObservableList<HBox> membersToDisplay = FXCollections.observableArrayList();
    Channel channel;
    ListChangeListener<UserLite> membersListListener;
    ListChangeListener<UserLite> adminsListListener;

    /**
     * Initialise l'affichage de la liste des membres (acceptedPerson) contenus dans l'attribut channel de la classe
     */
    private void initMembersList() throws IOException {
        membersToDisplay.removeAll(); //réinitialisation
        for (UserLite usr : this.channel.getAcceptedPersons()){
            membersToDisplay.add((HBox) new MemberDisplay(usr).root);
        }
        membersList.setItems(membersToDisplay);
    }

    /**
     * Setter du channel
     * Met à jour la liste des membres en conséquence
     * @param channel
     */
    public void setChannel(Channel channel){
        this.channel = channel;
        try {
            initMembersList();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'affichage des membres du channel");
            e.printStackTrace();
        }
        //TODO implémenter ces méthodes dans Channel
//        this.channel.getAcceptedPersons().addListener(membersListListener);
//        this.channel.getAdministrators().addListener(adminsListListener);
    }


    //TODO gérer les radio buttons /!\ listener

    public ChannelMembersController(){

    }

    public void initialize() throws IOException {
        //Membres
        List<String> nickName = new ArrayList<>();
        nickName.add("Léa");
        nickName.add("Aida");
        nickName.add("Lucas");
        nickName.add("Vladimir");
        nickName.add("Jérôme");
        nickName.add("Van-Triet");
        nickName.add("Benjamin");
        nickName.add("Stéphane");

        for (int i = 0; i < 6; i++) {
            int d = new Random().nextInt(nickName.size());
            UserLite tmpUser = new UserLite();
            tmpUser.setNickName(nickName.get(d));
            nickName.remove(d);
            HBox tmp = (HBox) new MemberDisplay(tmpUser).root;
            tmp.setMaxWidth(membersList.getMaxWidth());
            tmp.setMinWidth(membersList.getMinWidth());
            tmp.setMaxHeight(membersList.getMaxHeight());
            tmp.setMinHeight(membersList.getMinHeight());
            membersToDisplay.add(tmp);
        }
//        tmpUser.setNickName("Aida");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
//        tmpUser.setNickName("Lucas");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
//        tmpUser.setNickName("Vladimir");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
//        tmpUser.setNickName("Jérôme");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
//        tmpUser.setNickName("Van-Triet");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
        membersList.setItems(membersToDisplay);
    }
}
