package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.TellOwnerToDeleteMessageMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Demande de suppression d'un message prop
 */
public class DeleteMessagePropMessage extends ClientToServerMessage {
    private final UUID channelID;
    private final UUID channelCreatorID;
    private final Message message;
    private final Boolean deletedByCreator;

    public DeleteMessagePropMessage(UserLite user, Channel channel, Message message, Boolean deletedByCreator) {
        this.channelID  = channel.getId();
        this.channelCreatorID = channel.getCreator().getId();
        this.message = message;
        this.deletedByCreator = deletedByCreator;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        //Handle proprietary channel. Tell Owners to add admins
        commController.sendMessage(channelCreatorID, new TellOwnerToDeleteMessageMessage(message, channelID, deletedByCreator));
    }
}
