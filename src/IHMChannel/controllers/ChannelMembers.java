package IHMChannel.controllers;

import IHMChannel.MemberDisplay;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Contrôleur de la vue "ChannelMembers" qui contient la liste des membres d'un channel et les options d'affichage de cette liste
 */
public class ChannelMembers {
    @FXML
    ListView membersList;
    ObservableList<HBox> membersToDisplay = FXCollections.observableArrayList();

    //TODO gérer les radio buttons /!\ listener

    public ChannelMembers(){

    }

    public void initialize() throws IOException {
        //Membres
        UserLite tmpUser = new UserLite();
        tmpUser.setNickName("Léa");
        HBox tmp = (HBox) new MemberDisplay(tmpUser).root;
        membersToDisplay.add(tmp);
        tmpUser.setNickName("Aida");
        tmp = (HBox) new MemberDisplay(tmpUser).root;
        membersToDisplay.add(tmp);
        tmpUser.setNickName("Lucas");
        tmp = (HBox) new MemberDisplay(tmpUser).root;
        membersToDisplay.add(tmp);
        tmpUser.setNickName("Vladimir");
        tmp = (HBox) new MemberDisplay(tmpUser).root;
        membersToDisplay.add(tmp);
        tmpUser.setNickName("Jérôme");
        tmp = (HBox) new MemberDisplay(tmpUser).root;
        membersToDisplay.add(tmp);
        tmpUser.setNickName("Van-Triet");
        tmp = (HBox) new MemberDisplay(tmpUser).root;
        membersToDisplay.add(tmp);
        membersList.setItems(membersToDisplay);
    }
}
