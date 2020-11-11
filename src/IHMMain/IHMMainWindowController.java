package IHMMain;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import IHMChannel.IHMChannelWindowController;
import app.MainWindowController;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class IHMMainWindowController implements Initializable{

    private MainWindowController mainWindowController;

    @FXML
    private StackPane mainArea;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
    }

    @FXML
    public void loadIHMChannelWindow(){
        this.mainArea.getChildren().clear(); //On efface les noeuds fils
        //On charge la vue IHMMainWindow
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMChannel/IHMChannelWindow.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            IHMChannelWindowController ihmChannelWindowController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            ihmChannelWindowController.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            ihmChannelWindowController.setIhmMainWindowController(this); //On donne au controller fils une référence de son controller parent
            this.mainArea.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}