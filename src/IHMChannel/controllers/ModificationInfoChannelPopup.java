package IHMChannel.controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import common.shared_data.Channel;
import common.shared_data.ChannelType;
import common.shared_data.Visibility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModificationInfoChannelPopup {

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
    private  RadioButton ownedChannelButton;

    @FXML
    private  RadioButton privateChannelButton;



    @FXML
    private Label errorLabel;

    private Channel channel;

    private ChannelController parentController;

    @FXML
    private void cancelModification(){
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void confirmModification(){
        if (channelName.getText().isEmpty()) {
            errorLabel.setText("Il faut ajouter un nom de channel");
        } else {
            Boolean isShared = sharedChannelButton.isSelected();
            Boolean isPublic = publicChannelButton.isSelected();

            Visibility channelVisibility = isPublic ? Visibility.PUBLIC : Visibility.PRIVATE;
            ChannelType type = isShared ? ChannelType.SHARED : ChannelType.OWNED;

            channel.setName(channelName.getText());
            channel.setDescription(channelDescription.getText());
            channel.setVisibility(channelVisibility);
            channel.setType(type);

            parentController.getIhmChannelController().getInterfaceToCommunication().updateChannel(channel.getId(), channel.getName(), channel.getDescription(), channel.getVisibility());

            Stage stage = (Stage) ap.getScene().getWindow();
            stage.close();
        }
    }

    public void setParentController(ChannelController parentController){
        this.parentController = parentController;
    }

    public void setChannel(Channel c) {channel = c;}


    public void setUI(){
        channelDescription.setText(channel.getDescription());
        channelName.setText(channel.getName());
        Boolean shared = channel.getType().equals(ChannelType.SHARED);
        Boolean visible = channel.getVisibility().equals(Visibility.PUBLIC);

        if (Boolean.TRUE.equals(shared)) {
            sharedChannelButton.setSelected(true);
        }else{
            ownedChannelButton.setSelected(true);
        }
        if(Boolean.TRUE.equals(visible)) {
            publicChannelButton.setSelected(true);
        }else {
            privateChannelButton.setSelected(true);
        }


    }
}
