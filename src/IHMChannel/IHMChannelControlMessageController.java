package IHMChannel;
import IHMChannel.IHMChannelWindowController;
import IHMMain.IHMMainWindowController;
import app.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class IHMChannelControlMessageController implements Initializable {

    @FXML
    private TextArea messageSend;

    @FXML
    private Button likeBtn;

    @FXML
    private Button replyBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button delBtn;

    private boolean liked = false;
    private IHMChannelWindowController ihmChannelWindowController;
    private Parent parent;
    @FXML
    void LikeMessage() {
        if (this.liked == false) {
            this.liked = true;
            this.likeBtn.setStyle("-fx-background-color: #f80303;");
        }
        else {
            this.liked = false;
            this.likeBtn.setStyle("");
        }
    }

    @FXML
    void deleteMessage() {
        this.ihmChannelWindowController.messagesToDisplay.remove(this.ihmChannelWindowController.messagesToDisplay.indexOf(this.parent));
    }

    @FXML
    void editMessage(ActionEvent e) {
        if (messageSend.isEditable()) {
            this.messageSend.setText(this.messageSend.getText());
            this.messageSend.setEditable(false);
        }
        else if (!messageSend.isEditable()){
            this.messageSend.setEditable(true);
            this.messageSend.setFocusTraversable(true);
        }
    }

    @FXML
    void replyMessage() {

    }
    private MainWindowController mainWindowController;
    private IHMMainWindowController ihmMainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController) {
        this.ihmMainWindowController = ihmMainWindowController;
    }

    public void setMessage(TextArea mes) {
        // this.messageSend = new TextArea();
        this.messageSend.setText(mes.getText());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
    }

    public void uneditabled(){
        this.messageSend.setEditable(false);
    }

    public void setIHMChannelWindowController(IHMChannelWindowController ihmChannelWindowController){
        this.ihmChannelWindowController = ihmChannelWindowController;
    }
    public void setParent(Parent parent){
        this.parent = parent;
    }
}
