package Data.server;

import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.*;

import java.util.ArrayList;
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
    public Channel requestChannelCreation(Channel channel, boolean isShared, boolean isPublic, UserLite owner) {
        channel.setVisibility(isPublic ? Visibility.PUBLIC : Visibility.PRIVATE);
        channel.setType(isShared ? ChannelType.SHARED : ChannelType.OWNED);
        channel.updateCreator(owner);

        channelsListController.addChannel(channel);

        return channel;
    }

    @Override
    public boolean requestChannelRemoval(UUID channelID, UserLite user) {
        Channel channel = channelsListController.searchChannelById(channelID);
        if(channel!=null){
            if(channel.userIsAdmin(user.getId())) {
                channelsListController.removeChannel(channelID);
                return true;
            }
        }
        return false;
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
        ch.addMessage(ms);

        if (ch.getType() == ChannelType.SHARED) {
            this.channelsListController.writeMessageInChannel(ch, ms, response);
        }
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
        return this.channelsListController.getChannelMessages(ch.getId());
    }


    @Override
    public List<Channel> getVisibleChannels(UserLite user) {
        List<Channel> visibleChannels = new ArrayList<>();
        List<Channel> sharedChannels = channelsListController.getSharedChannels();
        List<Channel> ownedChannels = channelsListController.getOwnedChannels();

        for (Channel channel: sharedChannels) {
            if ((channel.getVisibility() == Visibility.PUBLIC) || (channel.userInChannel(user.getId()))) {
                visibleChannels.add(channel);
            }
        }

        for (Channel channel: ownedChannels) {
            if ((channel.getVisibility() == Visibility.PUBLIC) || (channel.userInChannel(user.getId()))) {
                visibleChannels.add(channel);
            }
        }

        return visibleChannels;
    }

    @Override
    public Channel createPublicSharedChannel(String name, UserLite creator, String description) {
        Visibility channelVisibility = Visibility.PUBLIC;
        ChannelType type = ChannelType.SHARED;
        Channel sChannel = new Channel(name,creator,description,channelVisibility,type);
        channelsListController.writeChannelDataToJSON(sChannel);
        channelsListController.addChannel(sChannel);
        return sChannel;
    }

    @Override
    public Channel createPrivateOwnedChannel(String name, UserLite creator, String description) {
        Visibility channelVisibility = Visibility.PRIVATE;
        ChannelType type = ChannelType.OWNED;
        Channel oChannel = new Channel(name,creator,description,channelVisibility,type);
        channelsListController.addChannel(oChannel);
        return oChannel;
    }

    @Override
    public Channel createPublicOwnedChannel(String name, UserLite creator, String description) {
        Visibility channelVisibility = Visibility.PUBLIC;
        ChannelType type = ChannelType.OWNED;
        Channel oChannel = new Channel(name,creator,description,channelVisibility,type);
        channelsListController.addChannel(oChannel);
        return oChannel;
    }

    @Override
    public Channel createPrivateSharedChannel(String name, UserLite creator, String description) {
        Visibility channelVisibility = Visibility.PRIVATE;
        ChannelType type = ChannelType.SHARED;
        Channel sChannel= new Channel(name,creator,description,channelVisibility,type);
        channelsListController.writeChannelDataToJSON(sChannel);
        channelsListController.addChannel(sChannel);
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
    public void joinChannel(UUID ch, UserLite user) {
        Channel channel = channelsListController.searchChannelById(ch);
        if(channel!=null){
            channel.addUser(user);
        }
    }

    @Override
    public void leaveChannel(UUID ch, UserLite user) {
        Channel channel = channelsListController.searchChannelById(ch);
        if(channel!=null){
            channel.removeUser(user.getId());
        }
    }

    @Override
    public Object getUserAddress(UserLite user) {
        return null;
    }

    @Override
    public Boolean checkAuthorization(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public Channel getChannel(UUID channelID)
    {
       return channelsListController.searchChannelById(channelID);
    }

    @Override
    public List<Channel> disconnectOwnedChannel(UserLite owner) {
        return channelsListController.disconnectOwnedChannel(owner);
    }


}
