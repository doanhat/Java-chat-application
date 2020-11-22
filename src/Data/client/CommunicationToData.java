package Data.client;

import common.interfaces.client.ICommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.List;

public class CommunicationToData implements ICommunicationToData {

    private DataClientController dataController;

    public CommunicationToData() {
        dataController = new DataClientController();
    }

    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    @Override
    public void addVisibleChannel(Channel channel) {

    }

    /**
     * User added to channel.
     *
     * @param user    the user
     * @param channel the channel
     */
    @Override
    public void userAddedToChannel(UserLite user, Channel channel) {

    }

    /**
     * Save new admin into history.
     *
     * @param user    the user
     * @param channel the channel
     */
    @Override
    public void saveNewAdminIntoHistory(UserLite user, Channel channel) {

    }

    /**
     * New admin.
     *
     * @param user    the user
     * @param channel the channel
     */
    @Override
    public void newAdmin(UserLite user, Channel channel) {

    }

    /**
     * Remove channel from list.
     *
     * @param channel     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    @Override
    public void removeChannelFromList(Channel channel, int duration, String explanation) {

    }

    /**
     * Ban user into history.
     *
     * @param user     the user
     * @param channel  the channel
     * @param duration the duration
     */
    @Override
    public void banUserIntoHistory(UserLite user, Channel channel, int duration) {

    }

    /**
     * Cancel ban of user into history.
     *
     * @param user    the user
     * @param channel the channel
     */
    @Override
    public void cancelBanOfUserIntoHistory(User user, Channel channel) {

    }

    /**
     * Delete user from channel.
     *
     * @param user        the user
     * @param channel     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    @Override
    public void deleteUserFromChannel(UserLite user, Channel channel, int duration, String explanation) {

    }

    /**
     * Gets history.
     *
     * @param channel the channel
     * @return history
     */
    @Override
    public List<Message> getHistory(Channel channel) {
        return null;
    }

    /**
     * Save message into history.
     *
     * @param message  the message
     * @param channel  the channel
     * @param response the response
     */
    @Override
    public void saveMessageIntoHistory(Message message, Channel channel, Message response) {

    }

    /**
     * Receive message.
     *
     * @param message  the message
     * @param channel  the channel
     * @param response the response
     */
    @Override
    public void receiveMessage(Message message, Channel channel, Message response) {

    }

    /**
     * Save edition into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channel    the channel
     */
    @Override
    public void saveEditionIntoHistory(Message oldMessage, Message newMessage, Channel channel) {

    }

    /**
     * Edit message.
     *
     * @param message    the message
     * @param newMessage the new message
     * @param channel    the channel
     */
    @Override
    public void editMessage(Message message, Message newMessage, Channel channel) {

    }

    /**
     * Save like into history.
     *
     * @param channel the channel
     * @param message the message
     * @param user    the user
     */
    @Override
    public void saveLikeIntoHistory(Channel channel, Message message, UserLite user) {

    }

    /**
     * Like message.
     *
     * @param channel the channel
     * @param message the message
     * @param user    the user
     */
    @Override
    public void likeMessage(Channel channel, Message message, UserLite user) {

    }

    /**
     * Save deletion into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channel    the channel
     */
    @Override
    public void saveDeletionIntoHistory(Message oldMessage, Message newMessage, Channel channel) {

    }

    /**
     * Delete message.
     *
     * @param message          the message
     * @param channel          the channel
     * @param deletedByCreator the deleted by creator
     */
    @Override
    public void deleteMessage(Message message, Channel channel, boolean deletedByCreator) {

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
     * @param channel     the channel
     * @param newNickname the new nickname
     */
    @Override
    public void updateNickname(UserLite user, Channel channel, String newNickname) {

    }

    /**
     * Save nickname into history.
     *
     * @param user        the user
     * @param channel     the channel
     * @param newNickname the new nickname
     */
    @Override
    public void saveNicknameIntoHistory(UserLite user, Channel channel, String newNickname) {

    }

    /**
     * Add user to channel.
     *
     * @param user    the user
     * @param channel the channel
     */
    @Override
    public void addUserToChannel(UserLite user, Channel channel) {

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
