package IHMMain;

import app.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class IHMMainPage implements Initializable {

    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
    }
}
