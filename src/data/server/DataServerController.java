package data.server;

import common.interfaces.server.IServerCommunicationToData;

public class DataServerController {
    private ChannelsListController channelsListController;
    private UserListController userListController;
    private IServerCommunicationToData iServerCommunicationToData;


    public DataServerController() {
        this.channelsListController= new ChannelsListController();
        this.userListController = new UserListController(this.channelsListController);
        this.iServerCommunicationToData = new ServerCommunicationToData(this.userListController,this.channelsListController);
    }

    public ChannelsListController getChannelsListController() {
        return channelsListController;
    }

    public UserListController getUsersListController() {
        return userListController;
    }

    public IServerCommunicationToData getIServerCommunicationToData() {
        return iServerCommunicationToData;
    }
}