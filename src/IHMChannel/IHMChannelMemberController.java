package IHMChannel;

import IHMMain.IHMMainWindowController;
import app.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class IHMChannelMemberController implements Initializable{
    @FXML
    private Label user;

    @FXML
    private ToggleButton toggleBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setUser(String name) {
        if (this.user == null) {
            this.user = new Label();
        }
        this.user.setText(name);
    }

}
