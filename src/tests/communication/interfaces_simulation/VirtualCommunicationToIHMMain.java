package tests.communication.interfaces_simulation;

import common.interfaces.client.ICommunicationToIHMMain;
import common.shared_data.Channel;
import common.shared_data.ConnectionStatus;
import common.shared_data.User;
import common.shared_data.UserLite;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VirtualCommunicationToIHMMain implements ICommunicationToIHMMain {

    private List<UserLite> otherUsers;
    private Map<UUID, Channel> channels;

    public VirtualCommunicationToIHMMain(List<UserLite> otherUsers, Map<UUID, Channel> channels) {
        this.otherUsers = otherUsers;
        this.channels = channels;
    }

    @Override
    public void setConnectionStatus(ConnectionStatus status) { }

    @Override
    public void setConnectedUsers(List<UserLite> users) {
        otherUsers.clear();
        otherUsers.addAll(users);

        System.err.println("Connected users " + otherUsers);
    }

    @Override
    public void addConnectedUser(UserLite user) {
        if (user != null) {
            if (otherUsers != null) {
                otherUsers.add(user);
            }

            System.err.println("Add new connected user " + user.getId());
        }
        else {
            System.err.println("Fail to add new connected user, user is null");
        }
    }

    @Override
    public void removeConnectedUser(UserLite user) {
        if (user != null) {
            if (otherUsers != null) {
                otherUsers.remove(user);
            }

            System.err.println("Remove user " + user.getId());
        }
        else {
            System.err.println("Fail to remove user, user is null");
        }
    }

    @Override
    public void channelCreated(Channel channel) {
        channels.put(channel.getId(), channel);

        System.err.println("Channel " + channel.getId() + " created");
    }

    @Override
    public void channelAdded(Channel channel) {
        channels.put(channel.getId(), channel);

        System.err.println("Channel " + channel.getId() + " added");
    }

    @Override
    public void channelAddedAll(List<Channel> channelsList) {
        for (Channel channel : channelsList) {
            channels.put(channel.getId(), channel);

            System.err.println("Channel " + channel.getId() + " added");
        }
    }

    @Override
    public void channelConnectedUsers(UUID channelID, List<UserLite> connectedUsers) {

    }
}
