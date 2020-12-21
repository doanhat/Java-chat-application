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
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddMemberController {
    @FXML
    TextArea searchBar;

    @FXML
    ListView<UserLite> usersList;

    ObservableList<UserLite> observableList;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void initialize(){
        sortValueSearch(observableList);
        usersList.setItems(observableList);

        searchBar.textProperty().addListener((obs,oldValue,newValue)->{
            // TODO refresh la liste en fonction de ce qui est tapé
            //Attention, cette façon de faire fait que la recherche est déclenchée à chaque lettre tapée et / effacée.
            //Dynamique mais peut-être plus lourd, à voir.
            logger.log(Level.INFO, String.format("On recherche {} \n", newValue));
            if (!newValue.equals("") && newValue.trim().length() > 0) {
                ObservableList<UserLite> newlist = observableList.filtered(t -> t.getNickName().substring(0, newValue.length()).toLowerCase().contains(newValue.toLowerCase()));
                usersList.setItems(newlist);
            }
            else {
                usersList.setItems(observableList);
            }
        });
    }


    public void sortValueSearch(ObservableList<UserLite> userLites) {
        userLites.sort(Comparator.comparing(UserLite::getNickName));
    }
}
