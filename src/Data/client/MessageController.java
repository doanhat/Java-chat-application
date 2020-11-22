package Data.client;

import Data.resourceHandle.FileHandle;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.OwnedChannel;
import common.sharedData.User;

import java.util.List;
import java.util.UUID;

public class MessageController extends Controller{
    public MessageController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
    }

    public void saveMessageIntoHistory(Message message, UUID channelId, Message response) {
        if (message.getId().toString().equals("")) {
            message.setId(UUID.randomUUID());
        }
        int responseAdded = 0;
        FileHandle fileHandler = new FileHandle();
        List<OwnedChannel> listOwnedChannel = fileHandler.readJSONFileToList("ownedChannels", OwnedChannel.class);
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
        fileHandler.writeJSONToFile("ownedChannels",listOwnedChannel);
    }

    /**
     * Receive message.
     *  @param message  the message
     * @param channelId  the channel
     * @param response the response
     */
    public void receiveMessage(Message message, UUID channelId, Message response) {
        //channelClient.receiveMessage(message,channelId,response);
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
