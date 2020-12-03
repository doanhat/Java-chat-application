package IHMChannel.controllers;

import IHMChannel.ConnectedMemberDisplay;
import IHMChannel.IHMChannelController;
import IHMChannel.MessageDisplay;
import common.sharedData.Message;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectedMembersController {
    private IHMChannelController ihmChannelController;
    private List<UserLite> connectedMembersList;

    ObservableList<HBox> connectedMembersToDisplay = FXCollections.observableArrayList();
    ListChangeListener<UserLite> membersListListener;
    private ObservableList<UserLite> observableUsers;

    @FXML
    ListView connectedMembersListView;



    /**
     * Pour les tests seulement. A l'avenir, nécessite appel à interface de comm
     */
    private void initConnectedMembersList(){
        connectedMembersToDisplay.removeAll(); //réinitialisation
        for (UserLite user : observableUsers){
            try {
                connectedMembersToDisplay.add((HBox) new ConnectedMemberDisplay(user).root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        connectedMembersListView.setItems(connectedMembersToDisplay);

    }

    public void initialize(){
        // Définition listener sur la liste de membres connectés
        membersListListener = changed -> {
            changed.next();
            if(changed.wasAdded()){
                for(UserLite userAdded : changed.getAddedSubList()){
                    try {
                        this.connectedMembersToDisplay.add((HBox) new ConnectedMemberDisplay(userAdded).root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        /*initConnectedMembersList();*/
    }

    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    public List<UserLite> getConnectedMembersList() {
        return connectedMembersList;
    }

    public void setConnectedMembersList(List<UserLite> connectedMembersList) {
        this.connectedMembersList = connectedMembersList;
        observableUsers = FXCollections.observableArrayList(connectedMembersList);
        observableUsers.addListener(membersListListener);
        this.initConnectedMembersList();
    }
}
