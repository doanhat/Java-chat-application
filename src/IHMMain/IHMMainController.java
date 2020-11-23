package IHMMain;

import Communication.client.CommunicationClientInterfaceImpl;
import common.interfaces.client.*;
import Data.client.IHMMainToData;
import IHMChannel.IHMMainToIHMChannel;
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


        // TODO (data test) get by interface
        UserLite testUser = new UserLite("Jean Valjean", "");
        visibleChannels.setAll(
                new Channel("chan0", testUser, "channel 0", Visibility.PRIVATE, ChannelType.SHARED),
                new Channel("chan1", testUser, "channel 1", Visibility.PRIVATE,ChannelType.SHARED),
                new Channel("chan2", testUser, "channel 3", Visibility.PUBLIC,ChannelType.SHARED),
                new Channel("chan3", testUser, "channel 3", Visibility.PUBLIC,ChannelType.SHARED)
        );
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
        // TODO remove if where integration is done
        /*if (ihmMainToCommunication == null) {
            ihmMainToCommunication = new CommunicationClientInterfaceImpl(null);
        }*/
        return ihmMainToCommunication;
    }

    public void setIhmMainToIHMChannel(IHMMainToIHMChannel ihmMainToIHMChannel) {
        this.ihmMainToIHMChannel = ihmMainToIHMChannel;
    }
    public IHMMainToIHMChannel getIHMMainToIHMChannel() {
        // TODO remove if where integration is done
        /*if (ihmMainToIHMChannel == null) {
            ihmMainToIHMChannel = new IHMMainToIHMChannel();
        }*/
        return ihmMainToIHMChannel;
    }

    public void setIhmMainToData(IHMMainToData ihmMainToData) {
        this.ihmMainToData = ihmMainToData;
    }
    public IHMMainToData getIHMMainToData() {
        // TODO remove if where integration is done
        /*if (ihmMainToData == null) {
            ihmMainToData = new IHMMainToData(null);
        }*/
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
