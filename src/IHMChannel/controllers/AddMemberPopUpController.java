package IHMChannel.controllers;

import IHMChannel.IHMChannelController;
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
    ListView<UserLite> usersList;

    @FXML
    Button searchBtn;

    ObservableList<UserLite> membersToDisplay;

    private IHMChannelController ihmChannelController;
    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }

    private ChannelController channelController;
    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
        usersList.setCellFactory(list-> new AddMemberCell(channelController));
    }

    public AddMemberPopUpController(){
        //TEST DATA
        //TODO binding avec la liste de tous les utilisateurs connectés via interface
        List<UserLite> tmp = new ArrayList();
        UserLite usr = new UserLite("TOTO","IHMChannel/icons/heart-solid.png");
        tmp.add(usr);
        UserLite usr2 = new UserLite("TITI","IHMChannel/icons/heart-solid.png");
        tmp.add(usr2);
        UserLite usr3 = new UserLite("TATA","IHMChannel/icons/heart-solid.png");
        tmp.add(usr3);

        this.membersToDisplay = FXCollections.observableList(tmp);
    }


    public void initialize(){
        ImageView editIcon = new ImageView(new Image("IHMChannel/icons/search_icon.jpg"));
        editIcon.setFitHeight(25);
        editIcon.setFitWidth(25);
        searchBtn.setGraphic(editIcon);


        //usersList.setCellFactory(new AddMemberCellFactory()); //appliquer la cell factory
        usersList.setItems(membersToDisplay);
        //On n'applique pas encore la cell factory. On le fait une fois qu'on aura une référence sur le channelController, càd dans le call du setChannelController

        searchBar.textProperty().addListener((obs,oldValue,newValue)->{
            if (newValue != "" && !newValue.trim().isEmpty()) {
                System.out.println("(" + oldValue + "Changed to  " + newValue + ")\n");
                ObservableList<UserLite> containList = membersToDisplay.filtered(t -> t.getNickName().toLowerCase().substring(0, newValue.length()).contains(newValue.toLowerCase()));
                System.out.println(containList);
                usersList.setItems(containList);
            }
            else {
                usersList.setItems(membersToDisplay);
            }

        });
    }
}
