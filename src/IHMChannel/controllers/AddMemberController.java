package IHMChannel.controllers;

import IHMChannel.tools.AddMemberCellFactory;
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

    //TODO implémenter la recherche
    //Par exemple, mettre un listener sur le textarea et faire la recherche "dynamiquement" dès que le texte change
    //ou, + simple, ajouter un bouton de recherche

    public AddMemberController(){
        List<UserLite> tmp = new ArrayList();
        UserLite usr = new UserLite(UUID.randomUUID(),"TOTO","/icons/heart-solid.png");
        tmp.add(usr);
        UserLite usr2 = new UserLite(UUID.randomUUID(),"TITI","/icons/heart-solid.png");
        tmp.add(usr2);
        UserLite usr3 = new UserLite(UUID.randomUUID(),"TATA","/icons/heart-solid.png");
        tmp.add(usr3);

        this.observableList = FXCollections.observableList(tmp);
    }


    public void initialize(){
        usersList.setCellFactory(new AddMemberCellFactory());
        usersList.setItems(observableList);
    }
}
