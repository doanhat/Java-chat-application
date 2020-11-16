package common.interfaces.server;

import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.List;

public interface IServerCommunicationToData {
    
    List<Channel> requestUserChannelList(UserLite user);
    List<Channel> requestChannelRemoval(Channel ch, UserLite user);
    List<Channel> requestAddChannel(Channel ch, UserLite user);

    /* Channels section */
    void updateChannel(Channel ch);
    void requestAddUser(Channel ch, UserLite user);
    void addAdmin(Channel ch, UserLite user);
    boolean banUserFromChannel(Channel ch, UserLite user, int duration, String reason);
    boolean cancelUsersBanFromChannel(Channel ch, UserLite user);
    void postMessage(Channel ch, Message ms, UserLite user);
    void editMessage(Channel channel, Message ms, Message newMessage);
    void saveMessageLike(Channel ch, Message ms, UserLite user);
    void removeMessage(Channel ch, Message ms, UserLite user);
    List<Message> getChannelMessages(Channel ch, UserLite user);
    List<Channel> getVisibleChannels(Channel ch, UserLite user);
    Channel requestChannelCreation(Channel ch, boolean owner, boolean publicity, UserLite user);
    void leaveChannel(Channel ch, UserLite user);
    void channelSubscriptionRequest(Channel ch, UserLite user);

    /* Users section */
    List<UserLite> disconnectUser(UserLite user);
    List<UserLite> newConnection(UserLite user);
    List<UserLite> getConnectedUsers();
    void updateNickname(Channel ch, UserLite user, String newNickname);
    void sendChannelInvitation(UserLite sender, UserLite receiver, String message);

}