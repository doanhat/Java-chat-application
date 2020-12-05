package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AdminAddedMessage;
import Communication.messages.server_to_client.DeleteMessageMessage;
import Communication.messages.server_to_client.TellOwnerToDeleteMessageMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Demande de suppression d'un message sur un channel partagé
 */

public class DeleteMessageSharedMessage extends ClientToServerMessage {
    private final UUID channelID;
    private final Message message;
    private final Boolean deleteByCreator;

    public DeleteMessageSharedMessage(Channel channel, Message message, Boolean deleteByCreator) {
        this.channelID = channel.getId();
        this.message = message;
        this.deleteByCreator = deleteByCreator;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // Handle shared Channel
        Channel channel = commController.getChannel(channelID);
        if (channel != null) {
            System.err.println("Channel n'est pas trouvé");
            return;
        }


        commController.deleteMessage(message, channel, deleteByCreator);

        commController.sendMulticast(commController.getChannelConnectedUserList(channelID),
                new DeleteMessageMessage(message, channelID, deleteByCreator),
                null);

    }
}

