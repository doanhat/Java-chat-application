package IHMChannel.controllers;

import IHMChannel.KickedMemberDisplay;
import common.shared_data.Kick;
import common.shared_data.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.util.UUID;

public class KickedMembersListPopUpController {
    @FXML
    ListView kickedMembersList;

    @FXML
    Button saveBtn;

    ObservableList<Kick> kickList ;
    ObservableList<UserLite> kickedMembers ;
    ObservableList<HBox> kickedMembersToDisplay;

    public void initialize() {
        kickList = FXCollections.observableArrayList();
        kickedMembers = FXCollections.observableArrayList();
        kickedMembersToDisplay = FXCollections.observableArrayList();
    }

    public  Button getSaveBtn() { return saveBtn;    }

    public void setKickedMembers(ObservableList<Kick> kickList) {
        this.kickList = kickList;
        kickList.forEach(kick->{
            kickedMembers.add(kick.getUser());
        });
        displayMembers();
    }

    private void displayMembers() {
        kickedMembersToDisplay.clear();
        for (UserLite usr : kickedMembers){
            kickedMembersToDisplay.add((HBox) new KickedMemberDisplay(usr).root);
        }
        kickedMembersList.setItems(kickedMembersToDisplay);
    }
}
