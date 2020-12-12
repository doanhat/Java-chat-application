package data.client;

import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.User;

import java.util.List;
import java.util.UUID;

public class MessageController extends Controller{
    public MessageController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
    }

    public void saveMessageIntoHistory(Message message, Channel ownedChannel, Message response) {
        if (message.getId().toString().equals("")) {
            message.setId(UUID.randomUUID());
        }
        int responseAdded = 0;
        FileHandle fileHandler = new FileHandle(LocationType.CLIENT, FileType.CHANNEL);
        if (ownedChannel!=null) {
            List<Message> listMsg = ownedChannel.getMessages();
            if (response==null){
                ownedChannel.addMessage(message);
            } else {
                for (Message msg : listMsg) {
                    if (msg.getId().toString().equals(response.getId().toString())) {
                        msg.addAnswers(message);
                        responseAdded++;
                    }
                }

                if (responseAdded == 0) {
                    ownedChannel.addMessage(message);
                }
            }

            fileHandler.writeJSONToFile(ownedChannel.getId().toString(),ownedChannel);
        }
    }

    /**
     * Receive message.
     *  @param message  the message
     * @param channel  the channel
     * @param response the response
     */
    public void receiveMessage(Message message, Channel channel, Message response) {
        channelClient.receiveMessage(message,channel.getId(),response);
    }

    /**
     * Save edition into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channel    the channel
     */
    public void saveEditionIntoHistory(Message oldMessage, Message newMessage, Channel channel) {
        throw new UnsupportedOperationException();
    }

    /**
     * Edit message.
     *
     * @param message    the message
     * @param newMessage the new message
     * @param channel    the channel
     */
    public void editMessage(Message message, Message newMessage, Channel channel) {
        throw new UnsupportedOperationException();
    }

    /**
     * Save like into history.
     *
     * @param channel the channel
     * @param message the message
     * @param user    the user
     */
    public void saveLikeIntoHistory(Channel channel, Message message, User user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Like message.
     *
     * @param channel the channel
     * @param message the message
     * @param user    the user
     */
    public void likeMessage(Channel channel, Message message, User user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Save deletion into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channel    the channel
     */
    public void saveDeletionIntoHistory(Message oldMessage, Message newMessage, Channel channel) {
        throw new UnsupportedOperationException();
    }

    /**
     * Delete message.
     *
     * @param message          the message
     * @param channel          the channel
     * @param deletedByCreator the deleted by creator
     */
    public void deleteMessage(Message message, Channel channel, boolean deletedByCreator) {
        throw new UnsupportedOperationException();
    }
}
