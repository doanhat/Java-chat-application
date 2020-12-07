package Communication.messages.client_to_server.chat_action.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.chat_action.proprietary_channels.TellOwnerToDeleteMessageMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.UUID;


public class DeleteMessagePropMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 55135287105711L;
    private final UUID channelID;
    private final UUID ownerID;
    private final Message message;
    private final Boolean deletedByCreator;

    public DeleteMessagePropMessage(Channel channel, Message message, Boolean deletedByCreator) {
        this.channelID  = channel.getId();
        this.ownerID = channel.getCreator().getId();
        this.message = message;
        this.deletedByCreator = deletedByCreator;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.sendMessage(ownerID, new TellOwnerToDeleteMessageMessage(message, channelID, deletedByCreator));
    }
}
