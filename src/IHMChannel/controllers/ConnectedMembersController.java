package IHMChannel.controllers;

import IHMChannel.ConnectedMemberDisplay;
import IHMChannel.IHMChannelController;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectedMembersController {
    private IHMChannelController ihmChannelController;
    private List<UserLite> connectedMembersList;
    private Channel channel;

    ObservableList<HBox> connectedMembersToDisplay = FXCollections.observableArrayList();
    ListChangeListener<UserLite> membersListListener;
    private ObservableList<UserLite> observableUsers;

    @FXML
    ListView connectedMembersListView;


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
                        this.connectedMembersToDisplay.add((HBox) c.root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                if(changed.wasRemoved()){
                    this.connectedMembersToDisplay.remove(changed.getFrom(), changed.getTo()+1);

                }else{
                    if(changed.wasUpdated()){
                        initConnectedMembersList();
                    }
                }
            }
        };
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

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void changeNickname(Channel channel) {
        for(UserLite user : observableUsers){
            String nickname = channel.getNickNames().get(user.getId().toString());
            if(nickname != null){
                user.setNickName(nickname);
            }
        }
        for(UserLite user : connectedMembersList){
            String nickname = channel.getNickNames().get(user.getId().toString());
            if(nickname != null){
                user.setNickName(nickname);
            }
        }
        initConnectedMembersList();
    }
}
