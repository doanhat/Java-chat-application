package IHMChannel;

import IHMChannel.controllers.AdminMembersListController;
import IHMChannel.controllers.ConnectedMembersListController;
import common.sharedData.UserLite;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

public class ConnectedMembersListDisplay {
    public Parent root = null;
    public ConnectedMembersListController connectedMembersController;

    public ConnectedMembersListController getController(){return this.connectedMembersController;}

    public ConnectedMembersListDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ConnectedMembersList.fxml"));
        root = fxmlLoader.load();
        connectedMembersController = fxmlLoader.getController();
    }

    public void configureController(IHMChannelController ihmChannelController){
        connectedMembersController.setIhmChannelController(ihmChannelController);
    }

    public void setConnectedMembersList(List<UserLite> connectedMembersList){
        connectedMembersController.setConnectedMembersList(connectedMembersList);
    }
    public void addMemberToList(UserLite user) {
        connectedMembersController.addMemberToList(user);
    }
    public void removeMemberFromList(UserLite user) {
        connectedMembersController.removeMemberFromList(user);
    }
}
