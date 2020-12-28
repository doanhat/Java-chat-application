package tests.communication.interfaces_simulation;

import common.interfaces.client.ICommunicationToIHMChannel;
import common.shared_data.*;

import java.util.List;
import java.util.UUID;

public class VirtualCommunicationToIHMChannel implements ICommunicationToIHMChannel {

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
    @Override
    public void leaveChannel(UUID channelID, UserLite user) {

    }

    @Override
    public void banOfUserCancelledNotification(Kick kick, UUID channelID) {

    }
}
