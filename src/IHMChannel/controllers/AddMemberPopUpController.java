package IHMChannel.controllers;

import IHMChannel.tools.AddMemberCellFactory.AddMemberCell;
import common.shared_data.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddMemberPopUpController {
    @FXML
    TextField searchBar;

    @FXML
    ListView<UserLite> usersListView;

    @FXML
    Button searchBtn;

    ObservableList<UserLite> usersObservableList;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ChannelController getChannelController() {
        return channelController;
    }

    private ChannelController channelController;

    public void setUsersObservableList(List<UserLite> usersObservableList) {
        this.usersObservableList = FXCollections.observableArrayList(usersObservableList);

        usersListView.setItems(this.usersObservableList);
    }


    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
        usersListView.setCellFactory(list-> new AddMemberCell(channelController));
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
            if (!newValue.equals("") && !newValue.trim().isEmpty()) {
                logger.log(Level.INFO, String.format("%s changed to %s %n", oldValue, newValue));
                ObservableList<UserLite> containList = usersObservableList.filtered(t -> t.getNickName().toLowerCase().substring(0, newValue.length()).contains(newValue.toLowerCase()));
                logger.log(Level.INFO, containList.toString());
                usersListView.setItems(containList);
            }
            else {
                usersListView.setItems(usersObservableList);
            }

        });
    }
}
