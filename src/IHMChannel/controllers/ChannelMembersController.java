package IHMChannel.controllers;

import IHMChannel.*;
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

    ObservableList<HBox> membersToDisplay = FXCollections.observableArrayList();
    Channel channel;

    AlphabeticalMembersListDisplay alphabeticalMembersListDisplay;
    AdminMembersListDisplay adminMembersListDisplay;
    ConnectedMembersListDisplay connectedMembersListDisplay;



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
        connectedMembersListDisplay.getController().setCurrentChannel(channel);
    }

    /**
     * Tri des utilisateurs par ordre alphabétique
     * @throws IOException
     */
    public void alphabeticSort(){
        listMembersDisplay.setCenter(alphabeticalMembersListDisplay.root);
    }

    /**
     * Tri des membres selon leur rôle
     */
    public void adminSort() {
        listMembersDisplay.setCenter(adminMembersListDisplay.root);
    }

    /**
     * Tri des membres selon s'ils sont en ligne ou non
     */
    public void onlineUserSort() {
        listMembersDisplay.setCenter(connectedMembersListDisplay.root);
    }

    /**
     * Méthode appelée automatiquement par le FXMLLoader
     * Lance l'affichage par ordre alphabétique par défaut
     * @throws IOException
     */
    public void initialize() throws IOException {

        alphabeticalMembersListDisplay = new AlphabeticalMembersListDisplay();
        adminMembersListDisplay = new AdminMembersListDisplay();
        connectedMembersListDisplay = new ConnectedMembersListDisplay();

        viewMode.selectToggle(alphaBtn);
        alphabeticSort();

        /*
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
            //TODO Data en dur /!\
            HBox tmp = (HBox) new MemberDisplay(tmpUser,false,false,true,channel,ihmChannelController).root;
            tmp.setMaxWidth(membersList.getMaxWidth());
            tmp.setMinWidth(membersList.getMinWidth());
            tmp.setMaxHeight(membersList.getMaxHeight());
            tmp.setMinHeight(membersList.getMinHeight());
            membersToDisplay.add(tmp);
        }

        membersList.setItems(membersToDisplay);
        */
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
        alphabeticalMembersListDisplay.configureController(ihmChannelController);
        adminMembersListDisplay.configureController(ihmChannelController);
        connectedMembersListDisplay.configureController(ihmChannelController);
    }
    public void setConnectedMembersList(List<UserLite> connectedMembersList) {
        alphabeticalMembersListDisplay.setConnectedMembersList(connectedMembersList);
        adminMembersListDisplay.setConnectedMembersList(connectedMembersList);
        connectedMembersListDisplay.setConnectedMembersList(connectedMembersList);
    }

    public void addMemberToObservableList(UserLite user) {
        alphabeticalMembersListDisplay.addMemberToList(user);
        adminMembersListDisplay.addMemberToList(user);
        connectedMembersListDisplay.addMemberToList(user);
    }

    public void removeMemberFromObservableList(UserLite user) {
        alphabeticalMembersListDisplay.removeMemberFromList(user);
        adminMembersListDisplay.removeMemberFromList(user);
        connectedMembersListDisplay.removeMemberFromList(user);
    }
}
