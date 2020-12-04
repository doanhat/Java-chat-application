package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import IHMChannel.tools.AddMemberCellFactory.AddMemberCellFactory;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddMemberController {
    @FXML
    TextArea searchBar;

    @FXML
    ListView<UserLite> usersList;

    ObservableList<UserLite> observableList;

    private IHMChannelController ihmChannelController;

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    public AddMemberController(){
        //TEST DATA
        //TODO binding avec la liste de tous les utilisateurs connectés via interface
        List<UserLite> tmp = new ArrayList();
        UserLite usr = new UserLite("TOTO","/icons/heart-solid.png");
        tmp.add(usr);
        UserLite usr2 = new UserLite("TITI","/icons/heart-solid.png");
        tmp.add(usr2);
        UserLite usr3 = new UserLite("TATA","/icons/heart-solid.png");
        tmp.add(usr3);

        this.observableList = FXCollections.observableList(tmp);
    }


    public void initialize(){
        usersList.setCellFactory(new AddMemberCellFactory());
        usersList.setItems(observableList);

        searchBar.textProperty().addListener((obs,oldValue,newValue)->{
            // TODO refresh la liste en fonction de ce qui est tapé
            //Attention, cette façon de faire fait que la recherche est déclenchée à chaque lettre tapée et / effacée.
            //Dynamique mais peut-être plus lourd, à voir.
            System.out.println("On recherche "+newValue);
        });
    }
}
