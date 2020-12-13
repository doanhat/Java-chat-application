package IHMMain.controllers;

import app.MainWindowController;
import common.shared_data.UserLite;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UserListViewController implements Initializable{

    private MainWindowController mainWindowController;

    private ObservableList<UserLite> connectedUsersObservableList ;


    @FXML
    private ListView<UserLite> connectedUsersListView;
    @FXML
    private TextField filteredInput;
    @FXML
    private CheckBox filtrerChannelCheckBox;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        connectedUsersObservableList= mainWindowController.getIhmMainController().getConnectedUsers();
        FilteredList<UserLite> filteredData = new FilteredList<>(connectedUsersObservableList, b-> true);
        filteredInput.textProperty().addListener((observable,oldValue,newValue) -> {
            filteredData.setPredicate(userLite -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(userLite.getNickName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            });
            //Added for channel filter
            if(filtrerChannelCheckBox.isSelected()){
                
            }
        });
        connectedUsersListView.setItems(filteredData.sorted());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue

    }
}
