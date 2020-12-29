package IHMChannel.controllers;

import IHMChannel.*;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

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
    RadioButton adminBtn;
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

    public ConnectedMembersListDisplay getConnectedMembersListDisplay() {
        return connectedMembersListDisplay;
    }

    public boolean containsUser(List<UserLite> list, UserLite user){
        for(UserLite u : list){
            if(u.getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Tri des utilisateurs par ordre alphabétique
     * @throws IOException
     */
    public void alphabeticSort(){
        listMembersDisplay.setCenter(alphabeticalMembersListDisplay.getRoot());
    }

    /**
     * Tri des membres selon leur rôle
     */
    public void adminSort() {
        listMembersDisplay.setCenter(adminMembersListDisplay.getRoot());
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
        alphabeticalMembersListDisplay.addMemberToConnectedMembersList(user);
        adminMembersListDisplay.addMemberToConnectedMembersList(user);
        connectedMembersListDisplay.addMemberToConnectedMembersList(user);
    }

    public void removeMemberFromObservableList(UserLite user) {
        alphabeticalMembersListDisplay.removeMemberFromConnectedMembersList(user);
        adminMembersListDisplay.removeMemberFromConnectedMembersList(user);
        connectedMembersListDisplay.removeMemberFromConnectedMembersList(user);
    }

    public void changeNickname(UserLite user, String newNickname) {
        System.out.println("change nick for user " + user.getId() + " " + user.getNickName());

        //Update all views
        alphabeticalMembersListDisplay.getController().changeNickname(user, newNickname);
        connectedMembersListDisplay.getController().changeNickname(user, newNickname);
        adminMembersListDisplay.getController().changeNickname(user, newNickname);
    }
}
