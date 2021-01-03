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

public class KickedMembersListPopUpController {
    @FXML
    ListView kickedMembersList;

    @FXML
    Button saveBtn;

    ObservableList<Kick> kickList ;
    ObservableList<UserLite> unKickedList;
    ObservableList<UserLite> kickedMembers ;
    ObservableList<KickedMemberController> kickedMembersControllers;
    ObservableList<HBox> kickedMembersToDisplay;

    public void initialize() {
        kickList = FXCollections.observableArrayList();
        unKickedList = FXCollections.observableArrayList();
        kickedMembers = FXCollections.observableArrayList();
        kickedMembersControllers = FXCollections.observableArrayList();
        kickedMembersToDisplay = FXCollections.observableArrayList();
    }

    public  Button getSaveBtn() { return saveBtn;    }
    public  ObservableList<UserLite> getUnKickedList() {
        unKickedList.clear();
        kickedMembersControllers.forEach(kickedMemberController -> {
            if(kickedMemberController.isUnKicked()){
                unKickedList.add(kickedMemberController.getUserToDisplay());
            }
        });
        return unKickedList;
    }

    public void setKickedMembers(ObservableList<Kick> kickList) {
        this.kickList = kickList;
        kickList.forEach(kick->
            kickedMembers.add(kick.getUser())
        );
        displayMembers();
    }

    private void displayMembers() {
        kickedMembersToDisplay.clear();
        for (UserLite usr : kickedMembers){
            KickedMemberDisplay kickedMemberDisplay = new KickedMemberDisplay(usr);
            kickedMembersControllers.add(kickedMemberDisplay.getController());
            kickedMembersToDisplay.add((HBox) kickedMemberDisplay.root);
        }
        kickedMembersList.setItems(kickedMembersToDisplay);
    }
}
