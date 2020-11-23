package tests.communication.interfaces_simulation;

import common.interfaces.client.ICommunicationToData;
import common.sharedData.*;

import java.util.*;

public class VirtualCommunicationToData implements ICommunicationToData {

    private Map<UUID, Channel>  channels = new HashMap<>();
    private Map<UUID, UserLite> users    = new HashMap<>();
    UserLite localUser;

    public VirtualCommunicationToData(UserLite localUser) {
        this.localUser = localUser;
    }

    @Override
    public void addVisibleChannel(UUID channelId) {
        System.err.println("New visible channel " + channelId);

        Channel newChannel = new SharedChannel("channel", localUser, "test", Visibility.PUBLIC);
        newChannel.setId(channelId);
        
        channels.put(channelId, newChannel);
    }

    @Override
    public void userAddedToChannel(UserLite user, UUID channelId) {
        System.err.println("Join channel " + channelId + "successfully");

        Channel channel = channels.get(channelId);

        if (channel != null) {
            channel.addUser(user);
        }
    }

    @Override
    public void saveNewAdminIntoHistory(UserLite user, UUID channelId) {

    }

    @Override
    public void newAdmin(UserLite user, UUID channelId) {

    }

    @Override
    public void removeChannelFromList(UUID channelId, int duration, String explanation) {

    }

    @Override
    public void banUserIntoHistory(UserLite user, UUID channelId, int duration) {

    }

    @Override
    public void cancelBanOfUserIntoHistory(User user, UUID channelId) {

    }

    @Override
    public void deleteUserFromChannel(UserLite user, UUID channelId, int duration, String explanation) {

    }

    @Override
    public List<Message> getHistory(UUID channelId) {
        Channel channel = channels.get(channelId);

        if (channel != null) {
            return channel.getMessages();
        }

        return new ArrayList<>();
    }

    @Override
    public void saveMessageIntoHistory(Message message, UUID channelId, Message response) {
        System.err.println("Save message to history of channel " + channelId);
    }

    @Override
    public void receiveMessage(Message message, UUID channelId, Message response) {
        System.err.println("Receive new message " + message + " in channel " + channelId);
    }

    @Override
    public void saveEditionIntoHistory(Message oldMessage, Message newMessage, UUID channelId) {

    }

    @Override
    public void editMessage(Message message, Message newMessage, UUID channelId) {

    }

    @Override
    public void saveLikeIntoHistory(UUID channelId, Message message, UserLite user) {

    }

    @Override
    public void likeMessage(UUID channelId, Message message, UserLite user) {

    }

    @Override
    public void saveDeletionIntoHistory(Message oldMessage, Message newMessage, UUID channelId) {

    }

    @Override
    public void deleteMessage(Message message, UUID channelId, boolean deletedByCreator) {

    }

    @Override
    public List<UserLite> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void updateNickname(UserLite user, UUID channelId, String newNickname) {

    }

    @Override
    public void saveNicknameIntoHistory(UserLite user, UUID channelId, String newNickname) {

    }

    @Override
    public void addUserToChannel(UserLite user, UUID channelId) {
        System.err.println("New user added to Channel " + channelId);

        Channel channel = channels.get(channelId);

        if (channel != null) {
            channel.addUser(user);
        }
    }

    @Override
    public void disconnectUser(UserLite user) {

    }

    @Override
    public void newConnectionUser(User user) {

    }
}
