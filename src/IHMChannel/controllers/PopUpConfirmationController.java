package IHMChannel.controllers;

import common.sharedData.UserLite;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PopUpConfirmationController {
    ChannelController channelController;

    @FXML
    private Text textConfirm;

    @FXML
    private Button confirmerBtn;

    @FXML
    private Button annulerBtn;

    public Button getConfirmerBtn(){
        return confirmerBtn;
    }

    public Button getAnnulerBtn(){
        return annulerBtn;
    }

    public void setText(String text) {
        this.textConfirm.setText(text);
    }
    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
    }
}
