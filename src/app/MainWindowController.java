package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import IHMMain.controllers.ConnectionController;
import IHMMain.controllers.IHMMainWindowController;
import IHMMain.IHMMainController;
import com.sun.corba.se.pept.transport.ContactInfo;
import common.IHMTools.*;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class MainWindowController implements Initializable {

    @FXML
    private AnchorPane root;

    private IHMMainController ihmMainController;
    private ConnectionController connectionController;
    private IHMMainWindowController ihmMainWindowController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue

        //Charge la vue de connection à l'intérieur de la fenêtre
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMMain/views/Connection.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            connectionController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            connectionController.setMainWindowController(this); //On donne au controller fils une référence de son controller parent;
            this.root.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region) root, (Region) parent);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ConnectionController getConnectionController() {return connectionController;}



    public void loadIHMMainWindow() {
        this.root.getChildren().clear(); //On efface les noeuds fils de la racine
        //On charge la vue IHMMainWindow
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMMain/views/IHMMainWindow.fxml"));
            //Parent parent = fxmlLoader.load();
           IHMMainWindowController ihmMainWindowController = new IHMMainWindowController();
           this.ihmMainWindowController = ihmMainWindowController;
           fxmlLoader.setController(ihmMainWindowController);
           ihmMainWindowController.setMainWindowController(this); //On donne au controller fils une référence de son controller parent
            ihmMainWindowController.setIhmMainController(ihmMainController);
           Parent parent = fxmlLoader.load();

            this.root.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region) root, (Region) parent);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public IHMMainController getIhmMainController() {
        return ihmMainController;
    }

    public void setIhmMainController(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    public IHMMainWindowController getIHMMainWindowController() {
        return ihmMainWindowController;
    }
}
