package IHMChannel.controllers;

import common.shared_data.UserLite;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ConnectedMemberController {
    UserLite userToDisplay;
    @FXML
    Text username;

    public void setUserToDisplay(UserLite userToDisplay) {
        this.userToDisplay = userToDisplay;
        this.username.setText(userToDisplay.getNickName());
    }
}
