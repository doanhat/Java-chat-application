package Data.server;

import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.List;

public class ServerCommunicationToData implements IServerCommunicationToData {

    private UserListController userListController;
    private ChannelsListController channelsListController;

    public ServerCommunicationToData(UserListController userListController, ChannelsListController channelsListController) {
        this.userListController = userListController;
        this.channelsListController = channelsListController;
    }

    @Override
    public List<Channel> requestChannelRemoval(Channel channel, UserLite user) {
        return null;
    }

    @Override
    public List<Channel> requestChannelCreation(Channel channel, Boolean typeOwner, Boolean typePublic, UserLite user) {
        return null;
    }

    @Override
    public List<UserLite> updateChannel(Channel channel) {
        return null;
    }

    @Override
    public void requestAddUser(Channel ch, UserLite user) {

    }

    @Override
    public void saveNewAdminIntoHistory(Channel ch, UserLite user) {

    }

    @Override
    public boolean banUserFromChannel(Channel ch, UserLite user, int duration, String reason) {
        return false;
    }

    @Override
    public boolean cancelUsersBanFromChannel(Channel ch, UserLite user) {
        return false;
    }

    @Override
    public void saveMessageIntoHistory(Channel ch, Message ms, Message response) {

    }

    @Override
    public void editMessage(Channel ch, Message ms) {

    }

    @Override
    public void saveLikeIntoHistory(Channel ch, Message ms, UserLite user) {

    }

    @Override
    public void saveRemovalMessageIntoHistory(Channel ch, Message ms, Boolean deletedByCreator) {

    }

    @Override
    public List<Message> getHistory(Channel ch) {
        return null;
    }

    @Override
    public List<Channel> getVisibleChannels(UserLite user) {
        return null;
    }

    @Override
    public Channel createPublicSharedChannel(String name, UserLite creator, String description) {
        return null;
    }

    @Override
    public List<UserLite> disconnectUser(UserLite user) {
        return userListController.removeConnectedUser(user);
    }

    @Override
    public List<UserLite> newConnection(UserLite user) {
        return userListController.addConnectedUser(user);
    }

    @Override
    public List<UserLite> getConnectedUsers() {
        return userListController.getConnectedUsers();
    }

    @Override
    public void updateNickname(Channel ch, UserLite user, String newNickname) {

    }

    @Override
    public void sendChannelInvitation(UserLite sender, UserLite receiver, String message) {

    }

    @Override
    public List<UserLite> joinChannel(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public void leaveChannel(Channel ch, UserLite user) {

    }

    @Override
    public Object getUserAddress(UserLite user) {
        return null;
    }

    @Override
    public Boolean checkAuthorization(Channel ch, UserLite user) {
        return null;
    }
}
