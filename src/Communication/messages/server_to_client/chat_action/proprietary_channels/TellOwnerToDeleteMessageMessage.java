package Communication.messages.server_to_client.chat_action.proprietary_channels;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.chat_action.proprietary_channels.ValideDeleteMessageMessage;
import common.sharedData.Message;

import java.util.UUID;

public class TellOwnerToDeleteMessageMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -287745780048758221L;
    private final UUID channelID;
    private final Message message;
    private final boolean deletedByCreator;

    public TellOwnerToDeleteMessageMessage(Message message, UUID channelID, boolean deletedByCreator) {
        this.channelID = channelID;
        this.message = message;
        this.deletedByCreator = deletedByCreator;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.dataClientHandler().deleteMessage(message, channelID, deletedByCreator);
        commClientController.sendMessage(new ValideDeleteMessageMessage(message, channelID, deletedByCreator));
    }
}
