package Data.client;

import Data.resourceHandle.FileHandle;
import Data.resourceHandle.FileType;
import Data.resourceHandle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;

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
        FileHandle fileHandler = new FileHandle(LocationType.client, FileType.channel);
        if (ownedChannel!=null) {
            List<Message> listMsg = ownedChannel.getMessages();
            //listMsg.add(message);
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

        }
        fileHandler.writeJSONToFile(ownedChannel.getId().toString(),ownedChannel);
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
