package IHMChannel;

import IHMMain.controllers.IHMMainWindowController;
import common.IHMTools.IHMTools;
import app.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.control.Button;

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
    private BorderPane channelPageToDisplay;
    @FXML
    private StackPane stackWindow;
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

    public StackPane getStackWindow(){
        return this.stackWindow;
    }
}
