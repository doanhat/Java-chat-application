package IHMChannel;

import IHMMain.controllers.IHMMainWindowController;
import app.MainWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class IHMChannelListMemberController implements Initializable {
    @FXML
    private ListView listMember;
    public ObservableList<HBox> memberToDisplay = FXCollections.observableArrayList();
    private MainWindowController mainWindowController;
    private MemberDisplay memberDisplay;
    private IHMMainWindowController ihmMainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController) {
        this.ihmMainWindowController = ihmMainWindowController;
    }

    public IHMMainWindowController getIhmMainWindowController(){
        return this.ihmMainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
    }

}
