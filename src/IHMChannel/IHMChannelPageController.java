package IHMChannel;

import common.IHMTools.IHMTools;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import IHMMain.controllers.IHMMainWindowController;
import app.MainWindowController;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IHMChannelPageController implements Initializable {
    @FXML
    private StackPane StackMenu;

    @FXML
    private Button backBtn;

    @FXML
    private Button controlMemberBtn;

    @FXML
    private Button addMemberBtn;

    @FXML
    private Button quitChannelBtn;

    private ChannelMembersDisplay channelMembersDisplay;
    private ChannelMessagesDisplay channelMessagesDisplay;
    private IHMMainWindowController ihmMainWindowController;
    private MainWindowController mainWindowController;
    private boolean seeMember = false;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController) {
        this.ihmMainWindowController = ihmMainWindowController;
    }
    public IHMMainWindowController getIHMMainWindowController(){
        return this.ihmMainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
        this.init();

    }

    @FXML
    void seeMember() {
        if (!this.seeMember) {
            this.seeMember = true;
            this.ihmMainWindowController.getMainArea().getChildren().clear();
            this.ihmMainWindowController.getMainArea().getChildren().addAll(this.channelMembersDisplay.root);
            IHMTools.fitSizeToParent((Region)this.ihmMainWindowController.getMainArea(), (Region)this.channelMembersDisplay.root);

        }
        else {
            this.seeMember = false;
            this.ihmMainWindowController.getMainArea().getChildren().clear();
            this.ihmMainWindowController.getMainArea().getChildren().addAll(this.channelMessagesDisplay.root);
            IHMTools.fitSizeToParent((Region)this.ihmMainWindowController.getMainArea(), (Region)this.channelMessagesDisplay.root);
        }
    }
     public void init(){
        try{
            this.channelMessagesDisplay = new ChannelMessagesDisplay();
            this.channelMembersDisplay = new ChannelMembersDisplay();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
     }





}
