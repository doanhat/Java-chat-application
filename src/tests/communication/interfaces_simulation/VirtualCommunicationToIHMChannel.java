package tests.communication.interfaces_simulation;

import common.interfaces.client.ICommunicationToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

public class VirtualCommunicationToIHMChannel implements ICommunicationToIHMChannel {
    @Override
    public void changeNickname(User user) {

    }

    @Override
    public void displayChannelHistory(Channel channel, List<Message> history, List<UserLite> connectedUsers) {

    }

    @Override
    public void addConnectedUser(UUID channelId, UserLite user) {

    }

    @Override
    public void removeConnectedUser(UUID channelId, UserLite user) {

    }

    @Override
    public void addAuthorizedUser(UUID channel, UserLite user) {

    }

    @Override
    public void removeAuthorizedUser(UUID channel, UserLite user) {

    }
}
