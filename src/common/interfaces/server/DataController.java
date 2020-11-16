package common.interfaces.server;

public class DataController {
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