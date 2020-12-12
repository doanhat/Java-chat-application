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
    public void changeNickname(UserLite user, UUID channel) {

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
}
