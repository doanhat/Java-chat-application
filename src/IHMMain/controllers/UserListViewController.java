package IHMMain.controllers;

import app.MainWindowController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserListViewController implements Initializable{

    private MainWindowController mainWindowController;

    private ObservableList<UserLite> connectedUsersObservableList;

    @FXML
    private ListView<UserLite> connectedUsersListView;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        connectedUsersObservableList = mainWindowController.getIhmMainController().getConnectedUsers();
        connectedUsersListView.setItems(connectedUsersObservableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue

    }
}
