package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
import common.shared_data.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
//        usersList.setCellFactory(new AddMemberCellFactory());
        sortValueSearch(observableList);
        usersList.setItems(observableList);

        searchBar.textProperty().addListener((obs,oldValue,newValue)->{
            // TODO refresh la liste en fonction de ce qui est tapé
            //Attention, cette façon de faire fait que la recherche est déclenchée à chaque lettre tapée et / effacée.
            //Dynamique mais peut-être plus lourd, à voir.
            System.out.println("On recherche "+newValue);
            if (newValue != "" && newValue.trim().length() > 0) {
                ObservableList<UserLite> newlist = observableList.filtered(t -> t.getNickName().substring(0, newValue.length()).toLowerCase().contains(newValue.toLowerCase()));
                usersList.setItems(newlist);
            }
            else {
                usersList.setItems(observableList);
            }
        });
    }


    public void sortValueSearch(ObservableList<UserLite> userLites) {
        userLites.sort(new Comparator<UserLite>() {
            @Override
            public int compare(UserLite o1, UserLite o2) {
                return o1.getNickName().compareTo(o2.getNickName());
            }
        });
    }
}
