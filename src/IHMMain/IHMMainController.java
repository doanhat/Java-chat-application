package IHMMain;

import IHMChannel.interfaces.IHMMainToIHMChannel;
import common.interfaces.client.*;
import Data.client.IHMMainToData;
import IHMChannel.interfaces.IHMMainToIHMChannel;
import IHMMain.implementations.CommunicationToIHMMain;
import IHMMain.implementations.DataToIHMMain;
import IHMMain.implementations.IHMChannelToIHMMain;
import app.MainWindowController;
import common.sharedData.Channel;
import common.sharedData.ChannelType;
import common.sharedData.UserLite;
import common.sharedData.Visibility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private IHMMainToIHMChannel ihmMainToIHMChannel;

    private IHMMainToData ihmMainToData;

    /**
     * Properties use inside IHM-Main module
     */



    private MainWindowController mainWindowController;

    private ObservableList<UserLite> connectedUsers = FXCollections.observableArrayList();

    private ObservableList<Channel> visibleChannels = FXCollections.observableArrayList();

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

    public void setIhmMainToIHMChannel(IHMMainToIHMChannel ihmMainToIHMChannel) {
        this.ihmMainToIHMChannel = ihmMainToIHMChannel;
    }
    public IHMMainToIHMChannel getIHMMainToIHMChannel() {
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
}
