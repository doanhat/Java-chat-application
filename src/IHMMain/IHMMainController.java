package IHMMain;

import data.client.IHMMainToData;
import IHMMain.implementations.CommunicationToIHMMain;
import IHMMain.implementations.DataToIHMMain;
import IHMMain.implementations.IHMChannelToIHMMain;
import app.MainWindowController;
import common.interfaces.client.*;
import common.shared_data.Channel;
import common.shared_data.ConnectionStatus;
import common.shared_data.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    private Map<UUID, List<UserLite>> connectedUserByChannels= new HashMap<UUID, List<UserLite>>();

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
    
    public void loadIHMMainWindow(ConnectionStatus status) {
        mainWindowController.getConnectionController().loadIHMMainWindow(status);
    }

    /**
     * This method reset all the data
     * Use to clear a previous session
     */
    public void reset() {
        connectedUsers = FXCollections.observableArrayList();
        visibleChannels = FXCollections.observableArrayList(Channel.extractor());
        openedChannels = FXCollections.observableArrayList();
    }
}
