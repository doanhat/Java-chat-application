package IHMChannel;

import IHMMain.IHMMainWindowController;
import app.MainWindowController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class IHMChannelWindowController implements Initializable{

    private MainWindowController mainWindowController;
    private IHMMainWindowController ihmMainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setIhmMainWindowController(IHMMainWindowController ihmMainWindowController) {
        this.ihmMainWindowController = ihmMainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
    }

}