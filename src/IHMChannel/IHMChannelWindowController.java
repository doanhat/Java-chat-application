package IHMChannel;

import IHMMain.IHMMainWindowController;
import common.IHMTools.IHMTools;
import app.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
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

    public ObservableList<VBox> messagesToDisplay = FXCollections.observableArrayList();

    @FXML
    private BorderPane channelPageToDisplay;

    /**
     * Setter de la référence au contrôleur de app/MainWindow.fxml
     * @param mainWindowController
     */
    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    /**
     * Setter de la référence au contrôleur de IHMMain/IHMMainWindow.fxml
     * @param ihmMainWindowController
     */
    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController) {
        this.ihmMainWindowController = ihmMainWindowController;
    }

    /**
     * Getter de la référence au contrôleur de IHMMain/IHMMainWindow.fxml
     * @return ihmMainWindowController
     */
    public IHMMainWindowController getIhmMainWindowController(){
        return this.ihmMainWindowController;
    }

    /**
     * Getter de l'élément BorderPane où sera affiché la vue views/ChannelPage.fxml
     * @return
     */
    public BorderPane getChannelPageToDisplay() {
        return channelPageToDisplay;
    }

    /**
     * Setter de la référence à l'instance de ChannelPageDisplay qui gère les vues de IHM-Channel
     * @param channelPageToDisplay
     */
    public void setChannelPageToDisplay(BorderPane channelPageToDisplay) {
        this.channelPageToDisplay = channelPageToDisplay;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
    }


}
