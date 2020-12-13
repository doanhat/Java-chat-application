package tests.communication.interfaces_simulation;

import common.interfaces.client.ICommunicationToData;
import common.sharedData.*;

import java.util.*;

public class VirtualCommunicationToData implements ICommunicationToData {

    private Map<UUID, Channel> channels;
    private List<UserLite> otherUsers;
    UserLite localUser;

    public VirtualCommunicationToData(UserLite localUser, List<UserLite> otherUsers, Map<UUID, Channel> channels) {
        this.localUser = localUser;
        this.otherUsers = otherUsers;
        this.channels = channels;
    }

    /*@Override
    public void createChannel(Channel channel) { //TO-DO : Remplacer l'UUID par l'objet Channel
        System.err.println("New visible channel " + channel.getId());

        *//*Channel newChannel = new Channel("channel", localUser, "test", Visibility.PUBLIC,ChannelType.SHARED);
        newChannel.setId(channelId);*//*

        channels.put(channel.getId(), channel);
    }*/

    @Override
    public void userAddedToChannel(UserLite user, UUID channelId) {
        System.err.println("Join channel " + channelId + "successfully");

        Channel channel = channels.get(channelId);

        if (channel != null) {
            channel.addJoinedUser(user);
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
    public void removeUserFromJoinedUserChannel(UserLite user, UUID channelId, int duration, String explanation) {

    }

    @Override
    public void removeAllUserFromJoinedUserChannel(UUID channelId, int duration, String explanation) {

    }

    @Override
    public void removeUserFromAuthorizedUserChannel(UserLite user, UUID channelId, int duration, String explanation) {

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
        return otherUsers;
    }

    @Override
    public void updateNickname(UserLite user, UUID channelId, String newNickname) {

    }

    @Override
    public void saveNicknameIntoHistory(UserLite user, UUID channelId, String newNickname) {

    }

    @Override
    public void unbannedUserToChannel(UserLite user, UUID channelId) {
        System.err.println("New user added to Channel " + channelId);

        Channel channel = channels.get(channelId);

        if (channel != null) {
            channel.addJoinedUser(user);
        }
    }

    @Override
    public void addUserToOwnedChannel(UserLite user, UUID channelId) {

    }

    @Override
    public void inviteUserToOwnedChannel(UserLite user, UUID channelId) {

    }

}
