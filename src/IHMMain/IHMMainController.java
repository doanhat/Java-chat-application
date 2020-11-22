package IHMMain;

import Communication.IHMMainToCommunication;
import Data.client.IHMMainToData;
import IHMChannel.IHMMainToIHMChannel;
import app.MainWindowController;
import common.sharedData.UserLite;

import java.util.List;

public class IHMMainController {

    private CommunicationToIHMMain communicationToIHMMain;

    private DataToIHMMain dataToIHMMain;

    private IHMChannelToIHMMain ihmChannelToIHMMain;

    private IHMMainToCommunication ihmMainToCommunication;

    private IHMMainToIHMChannel ihmMainToIHMChannel;

    private IHMMainToData ihmMainToData;

    private MainWindowController mainWindowController;

    private List<UserLite> connectedUsers;

    public IHMMainController(){
        communicationToIHMMain = new CommunicationToIHMMain();
        dataToIHMMain = new DataToIHMMain();
        ihmChannelToIHMMain = new IHMChannelToIHMMain();
    }


    public CommunicationToIHMMain getCommunicationToIHMMain() {
        return communicationToIHMMain;
    }

    public DataToIHMMain getDataToIHMMain() {
        return dataToIHMMain;
    }

    public IHMChannelToIHMMain getIhmChannelToIHMMain() {
        return ihmChannelToIHMMain;
    }

    public void setIhmMainToCommunication(IHMMainToCommunication ihmMainToCommunication) {
        this.ihmMainToCommunication = ihmMainToCommunication;
    }

    public void setIhmMainToIHMChannel(IHMMainToIHMChannel ihmMainToIHMChannel) {
        this.ihmMainToIHMChannel = ihmMainToIHMChannel;
    }

    public void setIhmMainToData(IHMMainToData ihmMainToData) {
        this.ihmMainToData = ihmMainToData;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public List<UserLite> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(List<UserLite> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

}
