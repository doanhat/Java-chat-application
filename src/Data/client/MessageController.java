package Data.client;

import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;

public class MessageController {
    public MessageController() {
    }

    public void saveMessageIntoHistory(Message message, Channel channel, Message response) {

    }

    /**
     * Receive message.
     *
     * @param message  the message
     * @param channel  the channel
     * @param response the response
     */
    public void receiveMessage(Message message, Channel channel, Message response) {

    }

    /**
     * Save edition into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channel    the channel
     */
    public void saveEditionIntoHistory(Message oldMessage, Message newMessage, Channel channel) {

    }

    /**
     * Edit message.
     *
     * @param message    the message
     * @param newMessage the new message
     * @param channel    the channel
     */
    public void editMessage(Message message, Message newMessage, Channel channel) {

    }

    /**
     * Save like into history.
     *
     * @param channel the channel
     * @param message the message
     * @param user    the user
     */
    public void saveLikeIntoHistory(Channel channel, Message message, User user) {

    }

    /**
     * Like message.
     *
     * @param channel the channel
     * @param message the message
     * @param user    the user
     */
    public void likeMessage(Channel channel, Message message, User user) {

    }

    /**
     * Save deletion into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channel    the channel
     */
    public void saveDeletionIntoHistory(Message oldMessage, Message newMessage, Channel channel) {

    }

    /**
     * Delete message.
     *
     * @param message          the message
     * @param channel          the channel
     * @param deletedByCreator the deleted by creator
     */
    public void deleteMessage(Message message, Channel channel, boolean deletedByCreator) {

    }
}
