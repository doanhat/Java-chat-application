package IHMChannel;

import IHMMain.controllers.IHMMainWindowController;
import app.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class IHMChannelWindowController implements Initializable{

    private MainWindowController mainWindowController;
    private IHMMainWindowController ihmMainWindowController;
    @FXML
    private BorderPane channelPageToDisplay;

    public ObservableList<VBox> messagesToDisplay = FXCollections.observableArrayList();


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

    public BorderPane getChannelPageToDisplay() {
        return channelPageToDisplay;
    }

    public void setChannelPageToDisplay(BorderPane channelPageToDisplay) {
        this.channelPageToDisplay = channelPageToDisplay;
    }
}
