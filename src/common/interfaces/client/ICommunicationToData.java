package common.interfaces.client;

import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

/**
 * The interface Communication to data.
 */
public interface ICommunicationToData {
    /**
     * NOTE: Suggestion de Comm: utiliser seulement channelID pour diminuer la taille du paquet réseau,
     * ou une classe supplémentaire qui contient seulement les méta-données d'un channel
     * (un objet channel peut contient un objet de méta-données, et les contenues comme les message, user info, ...)
     */

    /**
     * Add visible channel.
     *
     * @param channelId the channel
     */
    void addVisibleChannel(UUID channelId);

    /**
     * User added to channel.
     *
     * @param user    the user
     * @param channelId the channel
     */
    void userAddedToChannel(UserLite user, UUID channelId);

    /**
     * Save new admin into history.
     *
     * @param user    the user
     * @param channelId the channel
     */
    void saveNewAdminIntoHistory(UserLite user, UUID channelId);

    /**
     * New admin.
     *
     * @param user    the user
     * @param channelId the channel
     */
    void newAdmin(UserLite user, UUID channelId);

    /**
     * Remove channel from list.
     *
     * @param channelId     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    void removeChannelFromList(UUID channelId, int duration, String explanation);

    /**
     * Ban user into history.
     *
     * @param user     the user
     * @param channelId  the channel
     * @param duration the duration
     */
    void banUserIntoHistory(UserLite user, UUID channelId, int duration);

    /**
     * Cancel ban of user into history.
     *
     * @param user    the user
     * @param channelId the channel
     */
    void cancelBanOfUserIntoHistory(User user, UUID channelId);


    /**
     * Delete user from channel.
     *
     * @param user        the user
     * @param channelId     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    void deleteUserFromChannel(UserLite user, UUID channelId, int duration, String explanation);

    /**
     * Gets history.
     *
     * @param channelId the channel
     * @return history
     */
    List<Message> getHistory(UUID channelId);

    /**
     * Save message into history.
     *
     * @param message  the message
     * @param channelId  the channel
     * @param response the response
     */
    void saveMessageIntoHistory(Message message, UUID channelId, Message response);

    /**
     * Receive message.
     *
     * @param message  the message
     * @param channelId  the channel
     * @param response the response
     */
    void receiveMessage(Message message, UUID channelId, Message response);

    /**
     * Save edition into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channelId    the channel
     */
    void saveEditionIntoHistory(Message oldMessage, Message newMessage, UUID channelId);

    /**
     * Edit message.
     *
     * @param message    the message
     * @param newMessage the new message
     * @param channelId    the channel
     */
    void editMessage (Message message, Message newMessage, UUID channelId);

    /**
     * Save like into history.
     *
     * @param channelId the channel
     * @param message the message
     * @param user    the user
     */
    void saveLikeIntoHistory(UUID channelId, Message message, UserLite user);

    /**
     * Like message.
     *
     * @param channelId the channel
     * @param message the message
     * @param user    the user
     */
    void likeMessage(UUID channelId, Message message, UserLite user);

    /**
     * Save deletion into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channelId    the channel
     */
    void saveDeletionIntoHistory(Message oldMessage, Message newMessage, UUID channelId);

    /**
     * Delete message.
     *
     * @param message          the message
     * @param channelId          the channel
     * @param deletedByCreator the deleted by creator
     */
    void deleteMessage(Message message, UUID channelId, boolean deletedByCreator);

    /**
     * Gets list users.
     *
     * @return the user
     */
    List<UserLite> getUsers();

    /**
     * Update nickname.
     *
     * @param user        the user
     * @param channelId     the channel
     * @param newNickname the new nickname
     */
    void updateNickname(UserLite user,UUID channelId, String newNickname);

    /**
     * Save nickname into history.
     *
     * @param user        the user
     * @param channelId     the channel
     * @param newNickname the new nickname
     */
    void saveNicknameIntoHistory(UserLite user,UUID channelId, String newNickname);

    /**
     * Add user to channel.
     *
     * @param user    the user
     * @param channelId the channel
     */
    void addUserToChannel(UserLite user, UUID channelId);

    /**
     * Disconnect user.
     *
     * @param user the user
     */
    void disconnectUser(UserLite user);

    /**
     * New connection user.
     *
     * @param user the user
     */
    void newConnectionUser(User user);
}

