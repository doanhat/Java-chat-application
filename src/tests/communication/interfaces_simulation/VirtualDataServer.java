package tests.communication.interfaces_simulation;

import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.*;

public class VirtualDataServer implements IServerCommunicationToData {

    private List<UserLite> users;
    private Map<UUID, Channel> channels;
    private Map<UserLite, List<UUID>> mapUserChannels;

    public VirtualDataServer(List<UserLite> users, Map<UUID, Channel> channels, Map<UserLite, List<UUID>> mapUserChannels) {
        this.users = users;
        this.channels = channels;
        this.mapUserChannels = mapUserChannels;
    }

    @Override
    public boolean requestChannelRemoval(UUID channel, UserLite user) {
        if (channel == null || user == null) {
            return false;
        }

        Channel correctChannel = channels.get(channel);

        if (correctChannel != null && correctChannel.getCreator().getId() == user.getId()) {
            channels.remove(correctChannel.getId());

            return true;
        }

        return false;
    }

    /*
    //Méthode supprimée confirmer si besoin de cette méthode, il a été remplacée par
    // différentes méthodes de création

    @Override
    public List<Channel> requestChannelCreation(Channel channel, Boolean typeOwner, Boolean typePublic, UserLite user) {
        if (channel != null && user != null) {
            channels.put(channel.getId(), channel);

            if (! mapUserChannels.containsKey(user)) {
                mapUserChannels.put(user, new ArrayList<>());
            }

            List<UUID> visibleChannels = mapUserChannels.get(user);
            visibleChannels.add(channel.getId());

            mapUserChannels.put(user, visibleChannels);

            System.err.println("Channel " + channel.getId() + " created");
        }

        return new ArrayList<>(channels.values());
    }
    */


    @Override
    public List<UserLite> updateChannel(Channel channel) {
        return null;
    }

    @Override
    public void requestAddUser(Channel channel, UserLite user) {
        Channel correctChannel = channels.get(channel.getId());

        if (correctChannel == null)
        {
            System.err.println("Cannot find channel");
            return;
        }

        correctChannel.addUser(user);
        mapUserChannels.get(user).add(channel.getId());
    }

    @Override
    public void saveNewAdminIntoHistory(Channel channel, UserLite user) {

    }

    @Override
    public boolean banUserFromChannel(Channel channel, UserLite user, int duration, String reason) {
        return false;
    }

    @Override
    public boolean cancelUsersBanFromChannel(Channel channel, UserLite user) {
        return false;
    }

    @Override
    public void saveMessageIntoHistory(Channel channel, Message ms, Message response) {

    }

    @Override
    public void editMessage(Channel channel, Message ms) {

    }

    @Override
    public void saveLikeIntoHistory(Channel channel, Message ms, UserLite user) {

    }

    @Override
    public void saveRemovalMessageIntoHistory(Channel channel, Message ms, Boolean deletedByCreator) {

    }

    @Override
    public List<Message> getHistory(Channel channel) {
        Channel correctChannel = channels.get(channel.getId());

        if (correctChannel == null)
        {
            System.err.println("Cannot find channel");
            return new ArrayList<>();
        }

        return correctChannel.getMessages();
    }

    @Override
    public List<Channel> getVisibleChannels(UserLite user) {
        List<UUID> visibleChannelIDs = mapUserChannels.get(user);

        List<Channel> visibleChannels = new ArrayList<>();

        if (visibleChannelIDs == null) {
            return visibleChannels;
        }

        for (UUID id: visibleChannelIDs) {
            Channel channel = channels.get(id);

            if (channel != null) {
                visibleChannels.add(channel);
            }
        }

        return visibleChannels;
    }

    @Override
    public Channel createPublicSharedChannel(String name, UserLite creator, String description) {
        return null;
    }

    @Override
    public Channel createPrivateOwnedChannel(String name, UserLite creator, String description) {
        return null;
    }

    @Override
    public Channel createPublicOwnedChannel(String name, UserLite creator, String description) {
        return null;
    }

    @Override
    public Channel createPrivateSharedChannel(String name, UserLite creator, String description) {
        return null;
    }

    @Override
    public void disconnectUser(UUID userID) {
        for (UserLite usr: users) {
            if (usr.getId() == userID) {
                users.remove(usr);
            }
        }
    }

    @Override
    public void newConnection(UserLite user) {
        System.err.println("User " + user.getId() + "connected");
        users.add(user);
    }

    @Override
    public List<UserLite> getConnectedUsers() {
        return users;
    }

    @Override
    public void updateNickname(Channel channel, UserLite user, String newNickname) {

    }

    @Override
    public void sendChannelInvitation(UserLite sender, UserLite receiver, String message) {

    }

    @Override
    public List<Message> getChannelMessages(UUID channelID) {
        Channel channel = channels.get(channelID);

        if (channel == null) {
            return new ArrayList<>();
        }

        return channel.getMessages();
    }

    @Override
    public void joinChannel(UUID channel, UserLite user) {
        Channel correctChannel = channels.get(channel);

        if (correctChannel == null)
        {
            System.err.println("Cannot find channel");
        }

        correctChannel.addUser(user);

    }

    @Override
    public void leaveChannel(UUID channel, UserLite user) {
        Channel correctChannel = channels.get(channel);

        if (correctChannel == null)
        {
            System.err.println("Cannot find channel");
            return;
        }

        correctChannel.kickUser(user, "No reason", null);
    }

    @Override
    public Object getUserAddress(UserLite user) {
        return null;
    }

    @Override
    public Boolean checkAuthorization(Channel channel, UserLite user) {
        return null;
    }

    @Override
    public Channel getChannel(UUID channelID){
        Channel ch = null;
        return ch;
    }
}
