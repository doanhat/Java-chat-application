package IHMChannel.controllers;

import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberAddController implements Initializable {
    UserLite userToInvite;
    ChannelController channelController;
    @FXML
    private ImageView avatar;

    @FXML
    private Text userName;

    @FXML
    private Button inviteBtn;

    @FXML
    void inviteMember() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("../views/PopUpConfirmation.fxml"));
        Parent root = fxmlLoader.load();
        PopUpConfirmationController popUpConfirmationController = fxmlLoader.getController();
        popUpConfirmationController.setText("Vous êtes sûr d'inviter " + userToInvite.getNickName() + "?");
        Stage popupwindow = new Stage();
        popupwindow.setWidth(300);
        popupwindow.setHeight(100);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Confirmation");
        popupwindow.setScene(new Scene(root));
        popupwindow.setResizable(false);
        popupwindow.show();
        UserLite user = userToInvite;
        Channel channel = this.channelController.getCurrentChannel();
        popUpConfirmationController.getAnnulerBtn().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                popupwindow.close();
            }
        });
        popUpConfirmationController.getConfirmerBtn().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("You have invited " + user.getNickName() + " to channel " + channel.getName());
                popupwindow.close();
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image usersImage = new Image("IHMChannel/icons/invite_icon.png");
        ImageView usersIcon = new ImageView(usersImage);
        usersIcon.setFitHeight(20);
        usersIcon.setFitWidth(20);
        inviteBtn.setGraphic(usersIcon);
    }


    public void setUserToInvite(UserLite userToInvite) {
        this.userToInvite = userToInvite;
        this.userName.setText(userToInvite.getNickName());
    }

    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
    }
}
