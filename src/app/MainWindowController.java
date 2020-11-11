package app;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import IHMMain.ConnectionController;
import IHMMain.IHMMainWindowController;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainWindowController implements Initializable{

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue

        //Charge la vue de connection à l'intérieur de la fenêtre
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMMain/Connection.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            ConnectionController connectionController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            connectionController.setMainWindowController(this); //On donne au controller fils une référence de son controller parent
            this.root.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void loadIHMMainWindow(){
        this.root.getChildren().clear(); //On efface les noeuds fils de la racine
        //On charge la vue IHMMainWindow
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMMain/IHMMainWindow.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            IHMMainWindowController ihmMainWindowController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            ihmMainWindowController.setMainWindowController(this); //On donne au controller fils une référence de son controller parent
            this.root.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


}
