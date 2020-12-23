package data.client;

import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.User;
import common.shared_data.UserLite;
import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;

import java.util.List;
import java.util.UUID;

public class MessageController extends Controller {
    public MessageController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
    }

    private FileHandle fileHandler = new FileHandle(LocationType.CLIENT, FileType.CHANNEL);
    public void saveMessageIntoHistory(Message message, Channel ownedChannel, Message response) {
        if (message.getId().toString().equals("")) {
            message.setId(UUID.randomUUID());
        }
        int responseAdded = 0;
        if (ownedChannel != null) {
            List<Message> listMsg = ownedChannel.getMessages();
            if (response == null) {
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

            fileHandler.writeJSONToFile(ownedChannel.getId().toString(), ownedChannel);
        }
    }

    /**
     * Receive message.
     *
     * @param message  the message
     * @param channel  the channel
     * @param response the response
     */
    public void receiveMessage(Message message, Channel channel, Message response) {
        channelClient.receiveMessage(message, channel.getId(), response);
    }

    /**
     * Save edition into history.
     *
     * @param oldMessage the old message
     * @param newMessage the new message
     * @param channelId    the channel
     */
    public void saveEditionIntoHistory(Message oldMessage, Message newMessage, UUID channelId) {
        Channel channel = this.channelClient.getChannel(channelId);
        if (channel != null) {
            List<Message> listMsg = channel.getMessages();
            for (Message msg : listMsg) {
                if(msg.getId().equals(oldMessage.getId())) {
                    msg.setEdited(true);
                    msg.setMessage(newMessage.getMessage());
                    break;
                }
            }
            fileHandler.writeJSONToFile(channel.getId().toString(), channel);
        }
    }

    /**
     * Edit message.
     *
     * @param message    the message
     * @param newMessage the new message
     * @param channelId    the channel ID
     */
    public void editMessage(Message message, Message newMessage, UUID channelId) {
        //Commentaire à supprimer une fois le changement effectué dans IDataToIHMChannel
        channelClient.editMessage(message, newMessage, channelId);
    }

    /**
     * Save like into history.
     *
     * @param ownedChannel the ownedChannel
     * @param message      the message
     * @param user         the user
     */
    public void saveLikeIntoHistory(Channel ownedChannel, Message message, UserLite user) {
        if (ownedChannel != null) {
            List<Message> listMsg = ownedChannel.getMessages();
            for (Message msg : listMsg) {
                if (msg.getId().toString().equals(message.getId().toString())) {
                    msg.addLike(user);
                }
            }
            fileHandler.writeJSONToFile(ownedChannel.getId().toString(), ownedChannel);
        }
    }

    /**
     * Like message.
     *
     * @param channel the channel
     * @param message the message
     * @param user    the user
     */
    public void likeMessage(Channel channel, Message message, User user) {
        if (channel != null) {
            List<Message> listMsg = channel.getMessages();
            for (Message msg : listMsg) {
                if (msg.getId().toString().equals(message.getId().toString())) {
                    msg.addLike(user);
                }
            }
            fileHandler.writeJSONToFile(channel.getId().toString(), channel);
        }
    }

    /**
     * Save deletion into history.
     *
     * @param message          the message
     * @param channelId        the channel ID
     * @param deletedByCreator the boolean that indicates if the message is deleted by its creator or not
     */
    public void saveDeletionIntoHistory(Message message, UUID channelId, boolean deletedByCreator) {
        Channel channel = this.channelClient.getChannel(channelId);

        if (channel != null) {
            List<Message> listMsg = channel.getMessages();
            for (Message msg : listMsg) {
                if (msg.equals(message)) {
                    if (deletedByCreator) {
                        msg.setDeletedByUser(true);
                    } else {
                        msg.setDeletedByAdmin(true);
                    }
                    break;
                }
            }
            channel.setMessages(listMsg);
            //fileHandler.deleteJSONFile(channel.getId().toString());
            fileHandler.writeJSONToFile(channel.getId().toString(), channel);
        }
    }

    /**
     * Delete message.
     *
     * @param message          the message
     * @param channelID        the channel ID
     * @param deletedByCreator the deleted by creator
     */
    public void deleteMessage(Message message, UUID channelID, boolean deletedByCreator) {
        //La ligne ci-dessous est commentée en attendant que IHM-Channel modifie l'interface IDataToIHMChannel
        saveDeletionIntoHistory(message, channelID, deletedByCreator);

        channelClient.deleteMessage(message, channelID, deletedByCreator);

    }
}
