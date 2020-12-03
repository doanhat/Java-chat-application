package IHMChannel.controllers;

import IHMChannel.AdminMembersListDisplay;
import IHMChannel.AlphabeticalMembersListDisplay;
import IHMChannel.IHMChannelController;
import IHMChannel.MemberDisplay;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

    private IHMChannelController ihmChannelController;

    @FXML
    ToggleGroup viewMode;
    @FXML
    RadioButton alphaBtn;
    @FXML
    BorderPane listMembersDisplay;

    AlphabeticalMembersListDisplay alphabeticalMembersListDisplay;
    AdminMembersListDisplay adminMembersListDisplay;
    Channel channel;

    public ChannelMembersController() {
    }

    public void initialize() throws IOException {
        alphabeticalMembersListDisplay = new AlphabeticalMembersListDisplay();
        adminMembersListDisplay = new AdminMembersListDisplay();

        viewMode.selectToggle(alphaBtn);
        alphabeticSort();
    }

    /**
     * Setter du channel
     * Met à jour la liste des membres en conséquence
     *
     * @param channel
     */
    public void setCurrentChannel(Channel channel) throws IOException {
        this.channel = channel;
        alphabeticalMembersListDisplay.getController().setCurrentChannel(channel);
        adminMembersListDisplay.getController().setCurrentChannel(channel);
    }

    public void alphabeticSort() throws IOException {
        alphabeticalMembersListDisplay.configureController(ihmChannelController);
        listMembersDisplay.setCenter(alphabeticalMembersListDisplay.root);

    }

    public void adminSort() {
        adminMembersListDisplay.configureController(ihmChannelController);
        listMembersDisplay.setCenter(adminMembersListDisplay.root);
    }

    public void onlineUserSort() {
        /* TO - DO quand on pourra savoir qui est connecté.
        channelMembers.sort(Comparator.comparing(UserLite::isConnected));
        displayMembers();
        */
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

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }
}
