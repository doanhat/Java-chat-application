package tests.communication.interfaces_simulation;

import common.interfaces.client.ICommunicationToIHMMain;
import common.sharedData.Channel;
import common.sharedData.UserLite;

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
    public void connectionAccepted() {
        System.err.println("Connected to server");
    }

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
}
