package Data.client;

import Data.resourceHandle.FileHandle;
import Data.server.UserListController;
import common.interfaces.client.ICommunicationToData;
import common.sharedData.*;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommunicationToData implements ICommunicationToData {
    private final DataClientController dataController;

    public CommunicationToData(DataClientController dataClientController) {
        this.dataController = dataClientController;
    }

    /**
     * Add visible channel.
     *
     * @param channelId the channel
     */
    @Override
    public void addVisibleChannel(UUID channelId) {
    }

    /**
     * User added to channel.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void userAddedToChannel(UserLite user, UUID channelId) {

    }

    /**
     * Save new admin into history.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void saveNewAdminIntoHistory(UserLite user, UUID channelId) {

    }

    /**
     * New admin.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void newAdmin(UserLite user, UUID channelId) {

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

    }

    /**
     * Cancel ban of user into history.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void cancelBanOfUserIntoHistory(User user, UUID channelId) {

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
     * @param message   the message
     * @param channelId the channel
     * @param response  the response
     */
    @Override
    public void saveMessageIntoHistory(Message message, UUID channelId, Message response) {
        if (message.getId().toString().equals("")) {
            message.setId(UUID.randomUUID());
        }
        int responseAdded = 0;
        FileHandle fileHandler = new FileHandle();
        List<OwnedChannel> listOwnedChannel = fileHandler.readJSONFileToList("ownedChannel", OwnedChannel.class);
        if (!listOwnedChannel.isEmpty()) {
            for (OwnedChannel oCh : listOwnedChannel) {
                if (oCh.getId().toString().equals(channelId.toString())) {
                    List<Message> listMsg = oCh.getMessages();
                    //listMsg.add(message);
                    if (listMsg.isEmpty()){
                        listMsg.add(message);
                    } else {
                        for (Message msg : listMsg) {
                            if (msg.getId().toString().equals(response.getId().toString())) {
                                msg.addAnswers(message);
                                responseAdded++;
                            }
                        }
                        if (responseAdded == 0){
                            listMsg.add(message);
                        }
                    }
                }
            }
        }
        fileHandler.writeJSONToFile("ownedChannel",listOwnedChannel);
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
        dataController.getMessageController().receiveMessage(message,channelId,response);
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

    }

    /**
     * Return list User
     *
     * @return
     */
    @Override
    public List<UserLite> getUsers() {
        return new UserListController().getConnectedUsers();
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

    }

    /**
     * Add user to channel.
     *
     * @param user      the user
     * @param channelId the channel
     */
    @Override
    public void addUserToChannel(UserLite user, UUID channelId) {
        dataController.getUserController().addUserToChannel(user,channelId);
    }

}
