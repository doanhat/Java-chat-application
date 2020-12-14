package IHMChannel.controllers;

import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SendInvitePopUpController {

    @FXML
    Button cancelBtn;

    @FXML
    Button sendInviteBtn;

    @FXML
    TextField inviteText;

    @FXML
    Text text;

    public String getInvitationMessage(){
        return inviteText.getText();
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public Button getSendInviteBtn() {
        return sendInviteBtn;
    }

    public void setText(UserLite guest, Channel channel){
        text.setText("Quel message voulez-vous adresser à "+guest.getNickName()+" dans son invitation au channel "+channel.getName()+" ?");
    }
}
