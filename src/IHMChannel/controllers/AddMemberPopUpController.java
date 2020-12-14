package IHMChannel.controllers;

import IHMChannel.tools.AddMemberCellFactory.AddMemberCell;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AddMemberPopUpController {
    @FXML
    TextField searchBar;

    @FXML
    ListView<UserLite> usersListView;

    @FXML
    Button searchBtn;

    ObservableList<UserLite> usersObservableList;

    public void setUsersObservableList(List<UserLite> usersObservableList) {
        this.usersObservableList = FXCollections.observableArrayList(usersObservableList);

        usersListView.setItems(this.usersObservableList);
    }

    private ChannelController channelController;

    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
        usersListView.setCellFactory(list-> new AddMemberCell(channelController));
    }

    /**
     * Utilisé seulement pour les données de test
     */
    public AddMemberPopUpController(){
        //TEST DATA
        List<UserLite> tmp = new ArrayList();
        UserLite usr = new UserLite("TOTO","IHMChannel/icons/heart-solid.png");
        tmp.add(usr);
        UserLite usr2 = new UserLite("TITI","IHMChannel/icons/heart-solid.png");
        tmp.add(usr2);
        UserLite usr3 = new UserLite("TATA","IHMChannel/icons/heart-solid.png");
        tmp.add(usr3);

        this.usersObservableList = FXCollections.observableList(tmp);
    }


    public void initialize(){
        ImageView editIcon = new ImageView(new Image("IHMChannel/icons/search_icon.jpg"));
        editIcon.setFitHeight(25);
        editIcon.setFitWidth(25);
        searchBtn.setGraphic(editIcon);


        //usersList.setCellFactory(new AddMemberCellFactory()); //appliquer la cell factory
        usersListView.setItems(usersObservableList);
        //On n'applique pas encore la cell factory. On le fait une fois qu'on aura une référence sur le channelController, càd dans le call du setChannelController

        searchBar.textProperty().addListener((obs,oldValue,newValue)->{
            if (newValue != "" && !newValue.trim().isEmpty()) {
                System.out.println("(" + oldValue + "Changed to  " + newValue + ")\n");
                ObservableList<UserLite> containList = usersObservableList.filtered(t -> t.getNickName().toLowerCase().substring(0, newValue.length()).contains(newValue.toLowerCase()));
                System.out.println(containList);
                usersListView.setItems(containList);
            }
            else {
                usersListView.setItems(usersObservableList);
            }

        });
    }
}
