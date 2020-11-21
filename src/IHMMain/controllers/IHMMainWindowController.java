package IHMMain.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import IHMChannel.IHMMainToIHMChannel;
import IHMMain.controllers.UserListViewController;
import app.MainWindowController;
import common.IHMTools.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class IHMMainWindowController implements Initializable{

    private MainWindowController mainWindowController;

    private IHMMainToIHMChannel ihmMainToIHMChannel;

    @FXML
    private StackPane stackMenu;

    @FXML
    private StackPane mainArea;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue
        ihmMainToIHMChannel = new IHMMainToIHMChannel();
        loadUserListView();
    }

    @FXML
    public void loadIHMChannelWindow(){
        this.mainArea.getChildren().clear(); //On efface les noeuds fils
        //On charge la vue IHMMainWindow
        Region ihmChannelNode = ihmMainToIHMChannel.getIHMChannelWindow();
        this.mainArea.getChildren().addAll(ihmChannelNode); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
        IHMTools.fitSizeToParent((Region)this.mainArea,ihmChannelNode);
    }

    @FXML
    public void loadUserListView(){
        this.mainArea.getChildren().clear(); //On efface les noeuds fils
        //On charge la vue UserListView
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../views/UserListView.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            UserListViewController userListViewController = fxmlLoader.getController(); //On récupère la classe controller liée au fxml
            userListViewController.setMainWindowController(this.mainWindowController); //On donne au controller fils une référence de son controller grand-parent
            this.mainArea.getChildren().addAll(parent); //On ajoute le noeud parent (fxml) au noeud racine de cette vue
            IHMTools.fitSizeToParent((Region)this.mainArea,(Region)parent);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public StackPane getMainArea() {
        return this.mainArea;
    }

}
