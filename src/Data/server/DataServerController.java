package Data.server;

import common.interfaces.server.IServerCommunicationToData;

public class DataServerController {
    private ChannelsListController channelsListController;
    private UserListController userListController;
    private IServerCommunicationToData iServerCommunicationToData;

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