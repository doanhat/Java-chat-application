package Communication.messages.client_to_server;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AdminAddedMessage;
import Communication.messages.server_to_client.DeleteMessageMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.UUID;

/**
 * Envoie l'information au serveur que le message à été supprimé afin de la propager
 */

public class ValideDeleteMessageMessage extends ClientToServerMessage {
    UUID channelID;
    Message message;
    Boolean deleteByCreator;

    public UUID getChannelID() {
        return channelID;
    }

    public void setChannelID(UUID channelID) {
        this.channelID = channelID;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Boolean isDeleteByCreator() {
        return deleteByCreator;
    }

    public void setDeleteByCreator(Boolean deleteByCreator) {
        this.deleteByCreator = deleteByCreator;
    }

    public ValideDeleteMessageMessage(Message message, UUID channelID, Boolean deleteByCreator) {
        this.channelID = channelID;
        this.message = message;
        this.deleteByCreator = deleteByCreator;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel != null)
        {
            System.err.println("Channel n'est pas trouvé");
            return;
        }

        commController.sendMulticast(commController.getChannelConnectedUserList(channelID),
                new DeleteMessageMessage(message, channelID, deleteByCreator),
                null);
    }
}
