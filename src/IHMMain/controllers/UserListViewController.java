package IHMMain.controllers;

import app.MainWindowController;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import common.shared_data.Visibility;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
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
                        for(Channel c: mainWindowController.getIhmMainController().getVisibleChannels()){
                            mainWindowController.getIhmMainController().getIIHMMainToCommunication().getConnectedUsers(c.getId());
                        }
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    try {
                        mainWindowController.getIhmMainController().getIIHMMainToCommunication().getConnectedUsers(channel.getId());
                        for (UserLite userLite : mainWindowController.getIhmMainController().getConnectedUserByChannels().get(channel.getId())) {
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

        //Reset la liste des channels si déselectionne la checkbox
        filtrerChannelCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    mainWindowController.getIHMMainWindowController().getPrivateChannels().setItems(visibleChannelsObservableList.filtered(channel -> channel.getVisibility() == Visibility.PRIVATE));
                    mainWindowController.getIHMMainWindowController().getPublicChannels().setItems(visibleChannelsObservableList.filtered(channel -> channel.getVisibility() == Visibility.PUBLIC));
                    for(Channel c: mainWindowController.getIhmMainController().getVisibleChannels()){
                        mainWindowController.getIhmMainController().getIIHMMainToCommunication().getConnectedUsers(c.getId());
                    }
                    filteredInput.setText("");
                }else{
                    filteredInput.setText("");
                }
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Mettez ici le code qui s'execute avant l'apparition de la vue

    }
}
