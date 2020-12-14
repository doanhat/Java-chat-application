package IHMMain.controllers;


import IHMMain.controllers.IHMMainWindowController;
import com.sun.org.apache.xpath.internal.operations.Bool;
import common.sharedData.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class CreationChannelPopupController {

    @FXML
    private AnchorPane ap;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField channelName;

    @FXML
    private TextArea channelDescription;

    @FXML
    private RadioButton sharedChannelButton;

    @FXML
    private RadioButton publicChannelButton;

    @FXML
    private Label errorLabel;

    private Channel newChannel;

    private IHMMainWindowController parentController;

    @FXML
    private void cancelCreation(){
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void confirmCreation(){
        if (channelName.getText().isEmpty()) {
            System.out.println("Il faut ajouter un nom de channel");
            errorLabel.setText("Il faut ajouter un nom de channel");
        } else {
            Boolean isShared = sharedChannelButton.isSelected();
            Boolean isPublic = publicChannelButton.isSelected();

            UserLite user = parentController.getIhmMainController().getIHMMainToData().getUser().getUserLite();
            Visibility channelVisibility = isPublic ? Visibility.PUBLIC : Visibility.PRIVATE;

            if(isShared){
                newChannel = new Channel(channelName.getText(), user, channelDescription.getText(), channelVisibility,ChannelType.SHARED);
            } else {
                newChannel = new Channel(channelName.getText(), user, channelDescription.getText(), channelVisibility,ChannelType.OWNED);
            }

            try {
                parentController.createChannel(newChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = (Stage) ap.getScene().getWindow();
            stage.close();
        }
    }

    public void setParentController(IHMMainWindowController parentController){
        this.parentController = parentController;
    }

}
