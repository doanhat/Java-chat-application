package IHMChannel;

import IHMMain.IHMMainWindowController;
import IHMTools.IHMTools;
import app.MainWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import IHMChannel.MemberDisplay;
import java.io.IOException;
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
        this.init();
    }
    public void init() {
        try {

            this.memberDisplay = new MemberDisplay("Léa");
            HBox tmp = (HBox) this.memberDisplay.root;
            memberToDisplay.add(tmp);
            IHMTools.fitSizeToParent((Region)this.listMember, (Region)tmp);
            IHMTools.fitSizeListMessage((Region)this.listMember, (Region)tmp, 10);
            this.memberDisplay = new MemberDisplay("Jerôme");
            tmp = (HBox) this.memberDisplay.root;
            memberToDisplay.add(tmp);
            IHMTools.fitSizeToParent((Region)this.listMember, (Region)tmp);
            IHMTools.fitSizeListMessage((Region)this.listMember, (Region)tmp, 10);
            this.memberDisplay = new MemberDisplay("Lucas");
            tmp = (HBox) this.memberDisplay.root;
            memberToDisplay.add(tmp);
            IHMTools.fitSizeToParent((Region)this.listMember, (Region)tmp);
            IHMTools.fitSizeListMessage((Region)this.listMember, (Region)tmp, 10);
            this.memberDisplay = new MemberDisplay("Vladimir");
            tmp = (HBox) this.memberDisplay.root;
            memberToDisplay.add(tmp);
            IHMTools.fitSizeToParent((Region)this.listMember, (Region)tmp);
            IHMTools.fitSizeListMessage((Region)this.listMember, (Region)tmp, 10);
            this.memberDisplay = new MemberDisplay("Aïda");
            tmp = (HBox) this.memberDisplay.root;
            memberToDisplay.add(tmp);
            IHMTools.fitSizeToParent((Region)this.listMember, (Region)tmp);
            IHMTools.fitSizeListMessage((Region)this.listMember, (Region)tmp, 10);
            this.memberDisplay = new MemberDisplay("Van Triet");
            tmp = (HBox) this.memberDisplay.root;
            memberToDisplay.add(tmp);
            IHMTools.fitSizeToParent((Region)this.listMember, (Region)tmp);
            IHMTools.fitSizeListMessage((Region)this.listMember, (Region)tmp, 10);
            listMember.setItems(memberToDisplay);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
