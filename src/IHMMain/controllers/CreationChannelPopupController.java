package IHMMain.controllers;


import common.shared_data.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
    private RadioButton privateChannelButton;

    @FXML
    private Label errorLabel;

    private IHMMainWindowController parentController;

    @FXML
    private void cancelCreation(){
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void confirmCreation(){
        Channel newChannel;

        if (channelName.getText().isEmpty()) {
            System.out.println("Il faut ajouter un nom de channel");
            errorLabel.setText("Il faut ajouter un nom de channel");
        } else {
            Boolean isShared = sharedChannelButton.isSelected();
            Boolean isPublic = publicChannelButton.isSelected();

            UserLite user = parentController.getIhmMainController().getIHMMainToData().getUser().getUserLite();
            Visibility channelVisibility = Boolean.TRUE.equals(isPublic) ? Visibility.PUBLIC : Visibility.PRIVATE;

            if(Boolean.TRUE.equals(isShared)){
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

    public void setVisibility(Visibility visibility) {
        if (visibility == Visibility.PUBLIC) {
            publicChannelButton.setSelected(true);
        } else {
            privateChannelButton.setSelected(true);
        }
    }

    public void setParentController(IHMMainWindowController parentController){
        this.parentController = parentController;
    }

}
