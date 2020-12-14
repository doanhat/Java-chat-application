package IHMMain.controllers;

import app.MainWindowController;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import common.shared_data.Visibility;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserListViewController implements Initializable {
    @FXML
    private ListView<UserLite> connectedUsersListView;
    @FXML
    private TextField filteredInput;
    @FXML
    private CheckBox filtrerChannelCheckBox;

    public void setMainWindowController(MainWindowController mainWindowController) {
        ObservableList<UserLite> connectedUsersObservableList ;
        ObservableList<Channel> visibleChannelsObservableList ;

        connectedUsersObservableList= mainWindowController.getIhmMainController().getConnectedUsers();
        FilteredList<UserLite> filteredData = new FilteredList<>(connectedUsersObservableList, b-> true);
        visibleChannelsObservableList = mainWindowController.getIhmMainController().getVisibleChannels();
        FilteredList<Channel> filteredChannels = new FilteredList<>(visibleChannelsObservableList, b-> true);

        filteredInput.textProperty().addListener((observable,oldValue,newValue) -> {
            filteredData.setPredicate(userLite -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return (userLite.getNickName().toLowerCase().indexOf(lowerCaseFilter) != -1);
            });
            //Added for channel filter
            if(filtrerChannelCheckBox.isSelected()) {
                filteredChannels.setPredicate(channel -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    try {
                        for (UserLite userLite : mainWindowController.getIhmMainController().getIHMMainToIHMChannel().getConnectedUsers(channel.getId())) {
                            if (userLite.getNickName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                                return true;
                            }
                        }
                    }catch(Exception e){
                        return false;
                    }
                    return false;
                });
                mainWindowController.getIHMMainWindowController().getPrivateChannels().setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PRIVATE));
                mainWindowController.getIHMMainWindowController().getPublicChannels().setItems(filteredChannels.filtered(channel -> channel.getVisibility() == Visibility.PUBLIC));
            }
        });
        connectedUsersListView.setItems(filteredData.sorted());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue

    }
}
