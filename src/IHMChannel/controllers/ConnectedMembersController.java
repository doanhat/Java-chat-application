package IHMChannel.controllers;

import IHMChannel.ConnectedMemberDisplay;
import IHMChannel.IHMChannelController;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectedMembersController {
    private IHMChannelController ihmChannelController;
    private List<UserLite> connectedMembersList;

    ObservableList<HBox> connectedMembersToDisplay = FXCollections.observableArrayList();
    ListChangeListener<UserLite> membersListListener;
    private ObservableList<UserLite> observableUsers;

    @FXML
    ListView connectedMembersListView;
    private List<ConnectedMemberDisplay> connectedMembersDisplayed = new ArrayList<>();


    /**
     * Pour les tests seulement. A l'avenir, nécessite appel à interface de comm
     */
    private void initConnectedMembersList(){
        connectedMembersToDisplay.clear(); //réinitialisation
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
                        ConnectedMemberDisplay c = new ConnectedMemberDisplay(userAdded);
                        //this.connectedMembersDisplayed.add(c);
                        this.connectedMembersToDisplay.add((HBox) c.root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                if(changed.wasRemoved()){
                    this.connectedMembersToDisplay.remove(changed.getFrom(), changed.getTo()+1);

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

    public void setConnectedMembersList(List<UserLite> updatedConnectedMembersList) {
        if(this.connectedMembersList != null){
            this.connectedMembersList.clear();
        }
        //this.connectedMembersList = new ArrayList<>(updatedConnectedMembersList);
        this.connectedMembersList = updatedConnectedMembersList;
        observableUsers = FXCollections.observableArrayList(updatedConnectedMembersList);
        observableUsers.addListener(membersListListener);
        this.initConnectedMembersList();
    }

    public void addMemberToObservableList(UserLite user) {
        observableUsers.add(user);
    }

    public void removeMemberFromObservableList(UserLite user) {
        observableUsers.remove(user);
    }
}
