package data.client;

import common.interfaces.client.ICommunicationToData;
import common.shared_data.*;
import javafx.application.Platform;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommunicationToData implements ICommunicationToData {
    private final DataClientController dataController;

    public CommunicationToData(DataClientController dataClientController) {
        this.dataController = dataClientController;
    }

    /**
     * Save new admin into history.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void saveNewAdminIntoHistory(UserLite user, UUID channelId) {
        dataController.getChannelController().saveNewAdminIntoHistory(user, channelId);
    }

    /**
     * New admin.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void newAdmin(UserLite user, UUID channelId) {
        Platform.runLater(() -> dataController.getChannelController().newAdmin(user, channelId));
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
        Channel ownedChannel = dataController.getChannelController().searchChannelById(channelId);

        if (ownedChannel != null &&
            ownedChannel.getCreator().getId().equals(dataController.getUserController().getLocalUser().getId())) {
            dataController.getChannelController().removeChannel(channelId);
        }

        Platform.runLater(() -> dataController.getChannelController().removeChannelFromList(channelId, duration, explanation));
    }

    @Override
    public void banUserIntoHistory(UserLite user, LocalDate endDate, Boolean isPermanent, String explanation, UUID channelId) {
        Date date = null;

        if (endDate != null) {
            date = java.util.Date.from(endDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        }

        dataController.getChannelController().banUserIntoHistory(user,channelId,date,isPermanent,explanation);
    }

    /**
     * Cancel ban of user into history.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void cancelBanOfUserIntoHistory(UserLite user, UUID channelId) {
        dataController.getChannelController().cancelBanOfUserIntoHistory(user,channelId);
    }

    @Override
    public void removeUserFromJoinedUserChannel(UserLite user, UUID channelId, int duration, String explanation) {
        dataController.getChannelController().removeUserFromJoinedUserChannel(user, channelId);
    }

    @Override
    public void removeAllUserFromJoinedUserChannel(UUID channelId, int duration, String explanation) {
        dataController.getChannelController().removeAllUserFromJoinedUserChannel(channelId);
    }

    @Override
    public void banUser(UserLite user, LocalDate endDate, Boolean isPermanent, String explanation, UUID channelId) {
        dataController.getChannelController().removeUserFromAuthorizedUserChannel(user,channelId);
        Platform.runLater(() -> dataController.getChannelController().getChannelClient().userBanNotification(user,channelId, endDate, explanation));
    }


    @Override
    public void removeUserFromAuthorizedUserChannel(UserLite user, UUID channelId) {
        dataController.getChannelController().removeUserFromAuthorizedUserChannel(user, channelId);
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
            dataController.getMessageController().receiveMessage(message, ownedChannel, response);
        }
        else {
            System.out.println("channel is null");
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
        this.dataController.getMessageController().saveEditionIntoHistory(oldMessage, newMessage, channelId);
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
        this.dataController.getMessageController().editMessage(message, newMessage, channelId);
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
        Channel ownedChannel = dataController.getChannelController().searchChannelById(channelId);
        if (ownedChannel != null) {
            dataController.getMessageController().saveLikeIntoHistory(
                    ownedChannel,
                    message,
                    user);
        }
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
        Channel channel = dataController.getChannelController().searchChannelById(channelId);
        if(channel!=null && message!=null && user!=null && channel.userInChannel(user.getId()) && channel.messageInChannel(message.getId())){
            message.addLike(user);
            saveLikeIntoHistory(channelId,message,user);
        }
        dataController.getChannelController().getChannelClient().likeMessage(channelId, message, user);
    }

    /**
     * Save deletion into history.
     *
     * @param message          the message
     * @param channelId        the channel ID
     * @param deletedByCreator the boolean that indicates if the message is deleted by its creator or not
     */
    @Override
    public void saveDeletionIntoHistory(Message message, UUID channelId, boolean deletedByCreator) {
        this.dataController.getMessageController().saveDeletionIntoHistory(message, channelId, deletedByCreator);
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
        this.dataController.getMessageController().deleteMessage(message, channelId, deletedByCreator);
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
        dataController.getChannelController().updateNickname(user,channelId,newNickname);
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
        dataController.getChannelController().saveNicknameIntoHistory(user,channelId,newNickname);
    }

    /**
     * Add user to channel.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void unbannedUserToChannel(UserLite user, UUID channelId) {
        dataController.getUserController().unbannedUserTochannel(user, channelId);
    }

    @Override
    public void addUserToOwnedChannel(UserLite user, UUID channelId) {
        dataController.getChannelController().addUserToOwnedChannel(user, channelId);
    }

    @Override
    public void inviteUserToOwnedChannel(UserLite user, UUID channelId) {
        dataController.getChannelController().userInvitedToChannel(user, channelId);
    }

    @Override
    public void requestRemoveAdmin(UUID channelID, UserLite admin) {
        Channel ownedChannel = dataController.getChannelController().searchChannelById(channelID);
        if (ownedChannel != null && ownedChannel.userIsAdmin(admin.getId()) && !ownedChannel.getCreator().getId().equals(admin.getId())) {
            ownedChannel.removeAdmin(admin.getId());
            dataController.getChannelController().saveRemoveAdminIntoHistory(channelID);
        }
        Platform.runLater(() -> dataController.getChannelController().removeAdmin(admin, channelID));
    }

    @Override
    public void updateChannel(UUID channelId, UUID userID, String name, String description, Visibility visibility) {
        Platform.runLater(() -> dataController.getChannelController().getChannelClient().modifyChannel(channelId, name, description, visibility));
    }

    @Override
    public void updateChannelIntoHistory(UUID channelID, UUID userID, String name, String description, Visibility visibility) {
        dataController.getChannelController().updateChannel(channelID, userID, name, description, visibility);
    }


}
