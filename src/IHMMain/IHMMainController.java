package IHMMain;

import IHMMain.controllers.UserInfosController;
import common.shared_data.*;
import data.client.IHMMainToData;
import IHMMain.implementations.CommunicationToIHMMain;
import IHMMain.implementations.DataToIHMMain;
import IHMMain.implementations.IHMChannelToIHMMain;
import app.MainWindowController;
import common.interfaces.client.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class IHMMainController {
    /**
     * Interface we provide to other modules
     */
    private ICommunicationToIHMMain communicationToIHMMain;

    private IDataToIHMMain dataToIHMMain;

    private IIHMChannelToIHMMain ihmChannelToIHMMain;

    /**
     * Interface we use from other modules
     */
    private IIHMMainToCommunication ihmMainToCommunication;

    private IIHMMainToIHMChannel ihmMainToIHMChannel;

    private IHMMainToData ihmMainToData;

    /**
     * Properties use inside IHM-Main module
     */
    private MainWindowController mainWindowController;

    private ObservableList<UserLite> connectedUsers = FXCollections.observableArrayList();

    private ObservableList<Channel> visibleChannels = FXCollections.observableArrayList(Channel.extractor());

    private ObservableList<Channel> openedChannels = FXCollections.observableArrayList();

    private Map<UUID, List<UserLite>> connectedUserByChannels = new HashMap<UUID, List<UserLite>>();

    public User loadUserValue;

    public String loadAvatarValue;

    public UUID userIdToLoad;

    public IHMMainController(){
        communicationToIHMMain = new CommunicationToIHMMain(this);
        dataToIHMMain = new DataToIHMMain(this);
        ihmChannelToIHMMain = new IHMChannelToIHMMain(this);
    }

    public ICommunicationToIHMMain getCommunicationToIHMMain() {
        return communicationToIHMMain;
    }

    public IDataToIHMMain getDataToIHMMain() {
        return dataToIHMMain;
    }

    public IIHMChannelToIHMMain getIhmChannelToIHMMain() {
        return ihmChannelToIHMMain;
    }

    public void setIhmMainToCommunication(IIHMMainToCommunication ihmMainToCommunication) {
        this.ihmMainToCommunication = ihmMainToCommunication;
    }
    public IIHMMainToCommunication getIIHMMainToCommunication() {

        return ihmMainToCommunication;
    }

    public void setIhmMainToIHMChannel(IIHMMainToIHMChannel ihmMainToIHMChannel) {
        this.ihmMainToIHMChannel = ihmMainToIHMChannel;
    }
    public IIHMMainToIHMChannel getIHMMainToIHMChannel() {
        return ihmMainToIHMChannel;
    }

    public void setIhmMainToData(IHMMainToData ihmMainToData) {
        this.ihmMainToData = ihmMainToData;
    }
    public IHMMainToData getIHMMainToData() {

        return ihmMainToData;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public ObservableList<UserLite> getConnectedUsers() {
        return connectedUsers;
    }

    public ObservableList<Channel> getVisibleChannels() {
        return visibleChannels;
    }

    public ObservableList<Channel> getOpenedChannels() {
        return openedChannels;
    }

    public Map<UUID, List<UserLite>> getConnectedUserByChannels() {
        return connectedUserByChannels;
    }

    /**
     * Update the value of a channel.
     * @param channelID ID of Channel to update
     * @param name New name of the channel
     * @param description New description of the channel
     * @param visibility New visibility of the channel
     */
    public void modifyChannel(UUID channelID, String name, String description, Visibility visibility) {
        Optional<Channel> channelLocal = visibleChannels.stream().filter(c -> c.getId().equals(channelID)).findFirst();
        if (channelLocal.isPresent()) {
            Channel channel = channelLocal.get();
            channel.setName(name);
            channel.setDescription(description);
            channel.setVisibility(visibility);
        }
    }
    
    public void loadIHMMainWindow(ConnectionStatus status) {
        mainWindowController.getConnectionController().loadIHMMainWindow(status);
    }

    public void loadUserInfosPopup(boolean isLocal, User user, ObservableList<Channel> channels) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/UserInfos.fxml"));
            Parent root = fxmlLoader.load();

            UserInfosController userInfosController = fxmlLoader.getController();

            userInfosController.setIhmMainController(this, user, channels, isLocal);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Informations Utilisateur");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAvatarPath(UserLite userLite, String avatarPath) {
        if (userIdToLoad.equals(userLite.getId())) {
            loadAvatarValue = avatarPath;
            if (loadUserValue != null) {
                loadUserValue.setAvatar(avatarPath);
                loadUserInfosPopup(false, loadUserValue, null);
            }
        }
    }

    public void addUser(User user) {
        if (userIdToLoad.equals(user.getId())) {
            loadUserValue = user;
            if (loadAvatarValue != null) {
                loadUserValue.setAvatar(loadAvatarValue);
                loadUserInfosPopup(false, loadUserValue, null);
            }
        }
    }

    /**
     * This method reset all the data
     * Use to clear a previous session
     */
    public void reset() {
        connectedUsers = FXCollections.observableArrayList();
        visibleChannels = FXCollections.observableArrayList(Channel.extractor());
        openedChannels = FXCollections.observableArrayList();
        connectedUserByChannels = new HashMap<UUID, List<UserLite>>();
    }
}
