package Data.server;

import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.List;

public class ServerCommunicationToData implements IServerCommunicationToData {

    @Override
    public List<Channel> requestUserChannelList(UserLite user) {
        return null;
    }

    @Override
    public List<Channel> requestChannelRemoval(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public List<Channel> requestAddChannel(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public void updateChannel(Channel ch) {

    }

    @Override
    public void requestAddUser(Channel ch, UserLite user) {

    }

    @Override
    public void addAdmin(Channel ch, UserLite user) {

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
    public void postMessage(Channel ch, Message ms, UserLite user) {

    }

    @Override
    public void editMessage(Channel channel, Message ms, Message newMessage) {

    }

    @Override
    public void saveMessageLike(Channel ch, Message ms, UserLite user) {

    }

    @Override
    public void removeMessage(Channel ch, Message ms, UserLite user) {

    }

    @Override
    public List<Message> getChannelMessages(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public List<Channel> getVisibleChannels(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public Channel requestChannelCreation(Channel ch, boolean owner, boolean publicity, UserLite user) {
        return null;
    }

    @Override
    public void leaveChannel(Channel ch, UserLite user) {

    }

    @Override
    public void channelSubscriptionRequest(Channel ch, UserLite user) {

    }

    @Override
    public List<UserLite> disconnectUser(UserLite user) {
        return null;
    }

    @Override
    public List<UserLite> newConnection(UserLite user) {
        return null;
    }

    @Override
    public List<UserLite> getConnectedUsers() {
        return null;
    }

    @Override
    public void updateNickname(Channel ch, UserLite user, String newNickname) {

    }

    @Override
    public List<Channel> requestChannelSuppression(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public void sendChannelInvitation(UserLite sender, UserLite receiver, String message) {

    }
}