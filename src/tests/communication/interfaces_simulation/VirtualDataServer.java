package tests.communication.interfaces_simulation;

import common.interfaces.server.IServerCommunicationToData;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.UserLite;
import common.shared_data.Visibility;

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
    public Channel requestChannelCreation(Channel channel, boolean isShared, boolean isPublic, UserLite owner) {
        if (channel != null && owner != null) {
            channels.put(channel.getId(), channel);

            if (! mapUserChannels.containsKey(owner)) {
                mapUserChannels.put(owner, new ArrayList<>());
            }

            List<UUID> visibleChannels = mapUserChannels.get(owner);
            visibleChannels.add(channel.getId());

            mapUserChannels.put(owner, visibleChannels);

            System.err.println("Channel " + channel.getId() + " created");
        }

        return channel;
    }

    @Override
    public boolean requestChannelRemoval(UUID channel, UserLite user) {
        if (channel == null || user == null) {
            return false;
        }

        Channel correctChannel = channels.get(channel);

        if (correctChannel != null && correctChannel.getCreator().getId().equals(user.getId())) {
            channels.remove(correctChannel.getId());

            return true;
        }

        return false;
    }

    @Override
    public void updateChannel(UUID channelID, UUID userID, String name, String description, Visibility visibility) {

    }

    @Override
    public void requestAddUser(Channel channel, UserLite user) {
        Channel correctChannel = channels.get(channel.getId());

        if (correctChannel == null)
        {
            System.err.println("Cannot find channel");
            return;
        }

        correctChannel.addJoinedUser(user);
        mapUserChannels.get(user).add(channel.getId());
    }

    @Override
    public void quitChannel(UUID channelID, UserLite user) {

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
            if (usr.getId().equals(userID)) {
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

        correctChannel.addJoinedUser(user);

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

    @Override
    public List<Channel> disconnectOwnedChannel(UserLite owner) {
        return null;
    }

    @Override
    public List<UUID> getChannelsWhereUser(UUID userID) {
        return null;
    }

    @Override
    public List<UUID> getChannelsWhereUserActive(UUID userID) {
        return null;
    }

    @Override
    public List<UserLite> getActiveUsersInChannel(UUID channelID) {
        return null;
    }


    @Override
    public void addOwnedChannelsToServerList(List<Channel> ownedChannels, UUID ownerID) {

    }

    /**
     * Envoyer une image encodée en string Base64 au server pour stocker
     *
     * @param user          utilisateur ayant l'image comme avatar
     * @param encodedString le string encodée en Base64
     */
    @Override
    public void saveAvatarToServer(UserLite user, String encodedString) {

    }

    /**
     * Récupérer le chemin vers l'avatar de l'utilisateur dans le serveur
     *
     * @param user utilisateur
     * @return
     */
    @Override
    public String getAvatarPath(UserLite user) {
        return null;
    }
}
