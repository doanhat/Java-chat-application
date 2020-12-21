package IHMMain.controllers;


import IHMMain.IHMMainController;
import common.shared_data.Channel;
import common.shared_data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

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
    private ChoiceBox<Channel> listChannelsUser;

    private IHMMainController ihmMainController;

    public void loadUserInfos(User u, ObservableList<Channel> channels, boolean isLocal){
        try {
            //String avatarPath = parentController.getIhmMainController().getIIHMMainToCommunication().getAvatarPath(u.getUserLite());
            Image image;
            if (u.getAvatar().equals("")) {
                String avatarPath = "IHMMain/icons/willsmith.png";
                image = new Image(avatarPath);
            } else {
                if (isLocal) {
                    String avatarPath = ihmMainController.getIHMMainToData().getLocalAvatarDirectoryPath() + u.getAvatar();
                    image = new Image(Paths.get(avatarPath).toUri().toString());
                } else {
                    // set from base64
                    byte[] decodedBytes = Base64
                            .getDecoder()
                            .decode(u.getAvatar());
                    ByteArrayInputStream is=new ByteArrayInputStream(decodedBytes);
                    image = new Image(is);
                }
            }
            avatarUser.setImage(image);

            nicknameUser.setText(u.getNickName());
            surnameUser.setText(u.getFirstName());
            nameUser.setText(u.getLastName());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            birthDateUser.setText(dateFormat.format(u.getBirthDate()));

            //populate listView
            listChannelsUser.setItems(channels != null ? channels : FXCollections.observableArrayList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIhmMainController(IHMMainController ihmMainController, User u, ObservableList<Channel> channels, boolean isLocal){
        this.ihmMainController = ihmMainController;
        this.loadUserInfos(u, channels, isLocal);
    }
}
