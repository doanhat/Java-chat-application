package IHMChannel;

import IHMMain.controllers.IHMMainWindowController;
import common.IHMTools.IHMTools;
import app.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class IHMChannelWindowController implements Initializable{

    private MainWindowController mainWindowController;
    private IHMMainWindowController ihmMainWindowController;

    @FXML
    private TextArea messageSend;

    @FXML
    private ListView chatView;

    @FXML
    private Button sendBtn;

    public ObservableList<VBox> messagesToDisplay = FXCollections.observableArrayList();

    @FXML
    public void sendMessage() {

        try {

            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMChannel/IHMChannelControlMessage.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            IHMChannelControlMessageController ihmChannelControlMessageController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            ihmChannelControlMessageController.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            ihmChannelControlMessageController.setIHMChannelWindowController(this);
            ihmChannelControlMessageController.setParent(parent);
            ihmChannelControlMessageController.uneditabled();
            ihmChannelControlMessageController.setMessage(this.messageSend);
            this.messagesToDisplay.add((VBox) parent);
            this.chatView.setItems(this.messagesToDisplay);
            IHMTools.fitSizeToParent((Region)this.chatView, (Region)parent);
            IHMTools.fitSizeListMessage((Region)this.chatView, (Region)parent, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
