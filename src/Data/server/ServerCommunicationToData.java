package Data.server;

import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

public class ServerCommunicationToData implements IServerCommunicationToData {

    // Retourne la liste des channels d'un utilisateur lors de connexion
    @Override
    public List<Channel> requestUserChannelList(UserLite user) {
        return null;
    }

    @Override
    public List<Channel> requestChannelSuppression(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public List<Channel> requestAddChannel(Channel ch, UserLite user) {
        return null;
    }

    @Override
    public List<UserLite> disconnectUser(UserLite user) {
        return null;
    }

    @Override
    public void updateNickname(Channel ch, UserLite user, String newNickname) {

    }
}