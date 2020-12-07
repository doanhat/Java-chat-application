package Data.client;

import common.interfaces.client.ICommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommunicationToData implements ICommunicationToData {
    private final DataClientController dataController;

    public CommunicationToData(DataClientController dataClientController) {
        this.dataController = dataClientController;
    }

    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    /*@Override
    public void createChannel(Channel channel) {
        dataController.getChannelController().mainClient.addChannelToList(channel);
    }*/

    /**
     * User added to channel.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void userAddedToChannel(UserLite user, UUID channelId) {
        dataController.getChannelController().userAddedToChannel(user, channelId);
    }

    /**
     * Save new admin into history.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void saveNewAdminIntoHistory(UserLite user, UUID channelId) {
        dataController.getChannelController().saveNewAdminIntoHistory(user,channelId);
    }

    /**
     * New admin.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void newAdmin(UserLite user, UUID channelId) {
        dataController.getChannelController().newAdmin(user, channelId);
    }

    /**
     * Remove channel from list.
     *
     * @param channelId   the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    @Override
    public void removeChannelFromList(UUID channelId, int duration, String explanation) {
        throw new UnsupportedOperationException();
    }

    /**
     * Ban user into history.
     *
     * @param user      the user
     * @param channelId the channel
     * @param duration  the duration
     */
    @Override
    public void banUserIntoHistory(UserLite user, UUID channelId, int duration) {
        throw new UnsupportedOperationException();
    }

    /**
     * Cancel ban of user into history.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void cancelBanOfUserIntoHistory(User user, UUID channelId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Delete user from channel.
     *
     * @param user        the user
     * @param channelId   the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    @Override
    public void deleteUserFromChannel(UserLite user, UUID channelId, int duration, String explanation) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets history.
     *
     * @param channelId the channel
     * @return history
     */
    @Override
    public List<Message> getHistory(UUID channelId) {
        return dataController.getChannelController().getHistory(channelId);
    }

    /**
     * Save message into history.
     *
     * @param message   the message
     * @param channelId the channel
     * @param response  the response
     */
    @Override
    public void saveMessageIntoHistory(Message message, UUID channelId, Message response) {
        Channel ownedChannel = dataController.getChannelController().searchChannelById(channelId);
        if (ownedChannel != null) {
            dataController.getMessageController().saveMessageIntoHistory(
                    message,
                    ownedChannel,
                    response);
        }
    }


    /**
     * Receive message.
     *
     * @param message   the message
     * @param channelId the channel
     * @param response  the response
     */
    @Override
    public void receiveMessage(Message message, UUID channelId, Message response) {
        Channel ownedChannel = dataController.getChannelController().channelClient.getChannel(channelId);
        if (ownedChannel != null) {
            dataController.getMessageController().receiveMessage(message,ownedChannel,response);
        }
    }

    /**
     * Save edition into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channelId  the channel
     */
    @Override
    public void saveEditionIntoHistory(Message oldMessage, Message newMessage, UUID channelId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Edit message.
     *
     * @param message    the message
     * @param newMessage the new message
     * @param channelId  the channel
     */
    @Override
    public void editMessage(Message message, Message newMessage, UUID channelId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Save like into history.
     *
     * @param channelId the channel
     * @param message   the message
     * @param user      the user
     */
    @Override
    public void saveLikeIntoHistory(UUID channelId, Message message, UserLite user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Like message.
     *
     * @param channelId the channel
     * @param message   the message
     * @param user      the user
     */
    @Override
    public void likeMessage(UUID channelId, Message message, UserLite user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Save deletion into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channelId  the channel
     */
    @Override
    public void saveDeletionIntoHistory(Message oldMessage, Message newMessage, UUID channelId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Delete message.
     *
     * @param message          the message
     * @param channelId        the channel
     * @param deletedByCreator the deleted by creator
     */
    @Override
    public void deleteMessage(Message message, UUID channelId, boolean deletedByCreator) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return list User
     *
     * @return
     */
    @Override
    public List<UserLite> getUsers() {
        return dataController.getUserController().getLocalUserList().stream().map(User::getUserLite).collect(Collectors.toList());
    }

    /**
     * Update nickname.
     *
     * @param user        the user
     * @param channelId   the channel
     * @param newNickname the new nickname
     */
    @Override
    public void updateNickname(UserLite user, UUID channelId, String newNickname) {
        throw new UnsupportedOperationException();
    }

    /**
     * Save nickname into history.
     *
     * @param user        the user
     * @param channelId   the channel
     * @param newNickname the new nickname
     */
    @Override
    public void saveNicknameIntoHistory(UserLite user, UUID channelId, String newNickname) {
        throw new UnsupportedOperationException();
    }

    /**
     * Add user to channel.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void unbannedUserToChannel(UserLite user, UUID channelId) {
        Channel ownedChannel = dataController.getChannelController().searchChannelById(channelId);
        if (ownedChannel != null) {
            ownedChannel.addUser(user);
            dataController.getUserController().unbannedUserTochannel(user,channelId);
        }
    }

    @Override
    public void addUserToOwnedChannel(UserLite user, UUID channelId) {
        dataController.getChannelController().addUserToOwnedChannel(user,channelId);
    }

}
