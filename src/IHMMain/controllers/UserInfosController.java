package IHMMain.controllers;


import common.shared_data.Channel;
import common.shared_data.User;
import common.shared_data.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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
    private ChoiceBox<String> listChannelsUser;
    private IHMMainWindowController parentController;

    @FXML
    public void loadUserInfos(User u){
        boolean isCurrentUser = (u.getId().equals(parentController.getIhmMainController().getIHMMainToData().getUser().getId()));

        if(isCurrentUser) {
            //TODO décommenter et virer will smith à l'intégration
            try {
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
        else {
            //String avatarPath = parentController.getIhmMainController().getIIHMMainToCommunication().getAvatarPath(u.getUserLite());
            String avatarPath = "IHMMain/icons/willsmith.png";
            Image image = new Image(avatarPath);
            avatarUser.setImage(image);

            nicknameUser.setText(u.getNickName());
            surnameUser.setText(u.getFirstName());
            nameUser.setText(u.getLastName());

            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            birthDateUser.setText(dateFormat.format(u.getBirthDate()));

            ObservableList<UserLite> connectedUsersObservableList;
/**
            connectedUsersObservableList= parentController.getIhmMainController().getConnectedUsers();
            FilteredList<UserLite> filteredData = new FilteredList<>(connectedUsersObservableList, b-> true);
            filteredInput.textProperty().addListener((observable,oldValue,newValue) -> {
                filteredData.setPredicate(userLite -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return (userLite.getNickName().toLowerCase().indexOf(lowerCaseFilter) != -1);});
            });
        }
 **/
        }
    }

    public void setParentController(IHMMainWindowController parentController, User u){
        this.parentController = parentController;
        this.loadUserInfos(u);
        //this.loadUserInfos(parentController.getIhmMainController().getIHMMainToData().getUser());
    }
}
