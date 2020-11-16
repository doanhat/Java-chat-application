package common.interfaces.server;

import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

public interface IServerCommunicationToData {

    public List<Channel> requestUserChannelList(UserLite user);
    public List<Channel> requestChannelSuppression(Channel ch, UserLite user);
    public List<Channel> requestAddChannel(Channel ch, UserLite user);
    public List<UserLite> disconnectUser(UserLite user);
    public void updateNickname(Channel ch, UserLite user, String newNickname);

}