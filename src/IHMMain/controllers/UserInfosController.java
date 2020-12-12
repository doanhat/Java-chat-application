package IHMMain.controllers;


import common.sharedData.Channel;
import common.sharedData.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserInfosController {
    @FXML
    private ImageView avatarUser;
    @FXML
    private Text  nicknameUser;
    @FXML
    private Text  surnameUser;
    @FXML
    private Text  nameUser;
    @FXML
    private Text birthDateUser;
    @FXML
    private ListView<String> listChannelsUser;
    private IHMMainWindowController parentController;

    @FXML
    public void loadUserInfos(User u){
        //TODO décommenter et virer will smith à l'intégration
        try{
            //String avatarPath = parentController.getIhmMainController().getIIHMMainToCommunication().getAvatarPath(u.getUserLite());
            String avatarPath = "IHMMain/icons/willsmith.png";
            Image image = new Image(avatarPath);
            avatarUser.setImage(image);

            nicknameUser.setText(u.getNickName());
            surnameUser.setText(u.getFirstName());
            nameUser.setText(u.getLastName());

            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            birthDateUser.setText(dateFormat.format(u.getBirthDate()));

            //populate listView
            ObservableList<Channel> listChannels = parentController.getIhmMainController().getVisibleChannels();
            ObservableList<String> listNamesChannels = FXCollections.observableArrayList();
            listChannels.forEach(channel -> listNamesChannels.add(channel.getName()));
            listChannelsUser.setItems(listNamesChannels);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setParentController(IHMMainWindowController parentController){
        this.parentController = parentController;
        this.loadUserInfos(parentController.getIhmMainController().getIHMMainToData().getUser());
    }


}
