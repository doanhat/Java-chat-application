package IHMMain.controllers;

import Communication.client.CommunicationClientInterfaceImpl;
import common.interfaces.client.*;
import Data.client.IHMMainToData;
import IHMChannel.IHMMainToIHMChannel;
import IHMMain.implementations.CommunicationToIHMMain;
import IHMMain.implementations.DataToIHMMain;
import IHMMain.implementations.IHMChannelToIHMMain;
import app.MainWindowController;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class IHMMainController {

    private ICommunicationToIHMMain communicationToIHMMain;

    private IDataToIHMMain dataToIHMMain;

    private IIHMChannelToIHMMain ihmChannelToIHMMain;

    private IIHMMainToCommunication ihmMainToCommunication;

    private IHMMainToIHMChannel ihmMainToIHMChannel;

    private IHMMainToData ihmMainToData;

    private MainWindowController mainWindowController;

    private ObservableList<UserLite> connectedUsers = FXCollections.observableArrayList();

    public IHMMainController(){
        communicationToIHMMain = new CommunicationToIHMMain();
        dataToIHMMain = new DataToIHMMain();
        ihmChannelToIHMMain = new IHMChannelToIHMMain();
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
        if (ihmMainToCommunication == null) {
            ihmMainToCommunication = new CommunicationClientInterfaceImpl(null);
        }
        return ihmMainToCommunication;
    }

    public void setIhmMainToIHMChannel(IHMMainToIHMChannel ihmMainToIHMChannel) {
        this.ihmMainToIHMChannel = ihmMainToIHMChannel;
    }
    public IHMMainToIHMChannel getIHMMainToIHMChannel() {
        // TODO remove if where integration is done
        if (ihmMainToIHMChannel == null) {
            ihmMainToIHMChannel = new IHMMainToIHMChannel();
        }
        return ihmMainToIHMChannel;
    }

    public void setIhmMainToData(IHMMainToData ihmMainToData) {
        this.ihmMainToData = ihmMainToData;
    }
    public IHMMainToData getIHMMainToData() {
        // TODO remove if where integration is done
        if (ihmMainToData == null) {
            ihmMainToData = new IHMMainToData(null);
        }
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

}
