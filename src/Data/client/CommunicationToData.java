package Data.client;

import common.interfaces.client.ICommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

public class CommunicationToData implements ICommunicationToData {
    private DataClientController dataController;

    public CommunicationToData(DataClientController dataClientController) {
        this.dataController = dataClientController;
    }

    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    @Override
    public void addVisibleChannel(Channel channel) {
        dataController.getChannelController().addVisibleChannel(channel);
    }

    /**
     * User added to channel.
     *
     * @param user    the user
     * @param channelId the channel
     */
    @Override
    public void userAddedToChannel(UserLite user, UUID channelId) {
        dataController.getChannelController().userAddedToChannel(user, channelId);
    }

    /**
     * Save new admin into history.
     *
     * @param user    the user
     * @param channelId the channel
     */
    @Override
    public void saveNewAdminIntoHistory(UserLite user, UUID channelId) {

    }

    /**
     * New admin.
     *
     * @param user    the user
     * @param channelId the channel
     */
    @Override
    public void newAdmin(UserLite user, UUID channelId) {

    }

    /**
     * Remove channel from list.
     *
     * @param channelId     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    @Override
    public void removeChannelFromList(UUID channelId, int duration, String explanation) {

    }

    /**
     * Ban user into history.
     *
     * @param user     the user
     * @param channelId  the channel
     * @param duration the duration
     */
    @Override
    public void banUserIntoHistory(UserLite user, UUID channelId, int duration) {

    }

    /**
     * Cancel ban of user into history.
     *
     * @param user    the user
     * @param channelId the channel
     */
    @Override
    public void cancelBanOfUserIntoHistory(User user, UUID channelId) {

    }

    /**
     * Delete user from channel.
     *
     * @param user        the user
     * @param channelId     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    @Override
    public void deleteUserFromChannel(UserLite user, UUID channelId, int duration, String explanation) {

    }

    /**
     * Gets history.
     *
     * @param channelId the channel
     * @return history
     */
    @Override
    public List<Message> getHistory(UUID channelId) {
        return null;
    }

    /**
     * Save message into history.
     *
     * @param message  the message
     * @param channelId  the channel
     * @param response the response
     */
    @Override
    public void saveMessageIntoHistory(Message message, UUID channelId, Message response) {

    }

    /**
     * Receive message.
     *
     * @param message  the message
     * @param channelId  the channel
     * @param response the response
     */
    @Override
    public void receiveMessage(Message message, UUID channelId, Message response) {

    }

    /**
     * Save edition into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channelId    the channel
     */
    @Override
    public void saveEditionIntoHistory(Message oldMessage, Message newMessage, UUID channelId) {

    }

    /**
     * Edit message.
     *
     * @param message    the message
     * @param newMessage the new message
     * @param channelId    the channel
     */
    @Override
    public void editMessage(Message message, Message newMessage, UUID channelId) {

    }

    /**
     * Save like into history.
     *
     * @param channelId the channel
     * @param message the message
     * @param user    the user
     */
    @Override
    public void saveLikeIntoHistory(UUID channelId, Message message, UserLite user) {

    }

    /**
     * Like message.
     *
     * @param channelId the channel
     * @param message the message
     * @param user    the user
     */
    @Override
    public void likeMessage(UUID channelId, Message message, UserLite user) {

    }

    /**
     * Save deletion into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channelId    the channel
     */
    @Override
    public void saveDeletionIntoHistory(Message oldMessage, Message newMessage, UUID channelId) {

    }

    /**
     * Delete message.
     *
     * @param message          the message
     * @param channelId          the channel
     * @param deletedByCreator the deleted by creator
     */
    @Override
    public void deleteMessage(Message message, UUID channelId, boolean deletedByCreator) {

    }

    /**
     * Return list User
     * @return
     */
    @Override
    public List<UserLite> getUsers() {
        return null;
    }

    /**
     * Update nickname.
     *
     * @param user        the user
     * @param channelId     the channel
     * @param newNickname the new nickname
     */
    @Override
    public void updateNickname(UserLite user, UUID channelId, String newNickname) {

    }

    /**
     * Save nickname into history.
     *
     * @param user        the user
     * @param channelId     the channel
     * @param newNickname the new nickname
     */
    @Override
    public void saveNicknameIntoHistory(UserLite user, UUID channelId, String newNickname) {

    }

    /**
     * Add user to channel.
     *
     * @param user    the user
     * @param channelId the channel
     */
    @Override
    public void addUserToChannel(UserLite user, UUID channelId) {

    }

    /**
     * Disconnect user.
     *
     * @param user the user
     */
    @Override
    public void disconnectUser(UserLite user) {

    }

    /**
     * New connection user.
     *
     * @param user the user
     */
    @Override
    public void newConnectionUser(User user) {

    }
}
