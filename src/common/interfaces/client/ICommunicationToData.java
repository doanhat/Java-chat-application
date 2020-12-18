package common.interfaces.client;

import common.shared_data.*;

import java.util.List;
import java.util.UUID;

/**
 * The interface Communication to data.
 */
public interface ICommunicationToData {
    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    //void createChannel(Channel channel);

    // TODO INTEGRATION V3: remove unused method, cet méthode est appelelé chez Channel, pas Data
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
     * Delete user from Connected users list of channel.
     *
     * @param user        the user
     * @param channelId     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    void removeUserFromJoinedUserChannel(UserLite user, UUID channelId, int duration, String explanation);


    /**
     * Delete all user from Connected users list of channel.
     *
     * @param channelId     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    void removeAllUserFromJoinedUserChannel(UUID channelId, int duration, String explanation);

    /**
     * Delete user from Authorized users list of channel.
     *
     * @param user        the user
     * @param channelId     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    void removeUserFromAuthorizedUserChannel(UserLite user, UUID channelId, int duration, String explanation);

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
     * @param message the message
     * @param channelId  the channel ID
     * @param deletedByCreator the boolean that indicates if the message is deleted by its creator or not
     */
    void saveDeletionIntoHistory(Message message, UUID channelId, boolean deletedByCreator);

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
     * Unban user
     *
     * @param user    the user
     * @param channelId the channel
     */
    void unbannedUserToChannel(UserLite user, UUID channelId);

    /**
     * Add user to JoinedUsers in Owned channel
     * @param user the user
     * @param channelId channel ID
     */
    void addUserToOwnedChannel(UserLite user, UUID channelId);

    /**
     * Add user to authorizedUsers in Owned Channel
     * @param user the user
     * @param channelId channel ID
     */
    void inviteUserToOwnedChannel(UserLite user, UUID channelId);

    /**
     * Méthode pour mettre à jour les informations d'un channel dans la liste des channels
     *
     * @param channelID l'identificateur du channel concerné
     * @param userID l'identificateur qui veut faire les changes sur le channel
     * @param name nouvel nom du channel, mettre à null si pas besoin de le changer
     * @param description nouvelle description du channel, mettre à null si pas besoin de la changer
     * @param visibility nouvelle visibilité du channel, mettre à null si pas besoin de la changer
     * */
    void updateChannel(UUID channelID, UUID userID, String name, String description, Visibility visibility);
}

