package Data.server;

import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.*;

import java.util.List;
import java.util.UUID;

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
    public void editMessage(Channel channel, Message ms) {

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
    public SharedChannel createPublicSharedChannel(String name, UserLite creator, String description) {
        // TODO ID creation
        // TODO new constructor for sharedChannel?
        SharedChannel sChannel = new SharedChannel();
        Visibility channelVisibility = Visibility.PUBLIC;
        sChannel.setName(name);
        sChannel.setCreator(creator);
        sChannel.setDescription(description);
        sChannel.setVisibility(channelVisibility);
        return sChannel;
    }

    @Override
    public void disconnectUser(UUID userID) {
        if(userID!=null){
            if(userListController.userIsConnected(userID)){
                userListController.removeConnectedUser(userID);
            }
        }
    }


    @Override
    public void newConnection(UserLite user) {
        if(user.getId()!=null){
            if(!userListController.userIsConnected(user.getId())){
                userListController.addConnectedUser(user);
            }
        }
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
    public List<Message> getChannelMessages(UUID channelID) {
        return channelsListController.getChannelMessages(channelID);
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
