package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

public class TellOwnerToDeleteMessageMessage extends ServerToClientMessage {
    UUID channelID;
    Message message;
    Boolean deletedByCreator;

    public TellOwnerToDeleteMessageMessage(Message message, UUID channelID, Boolean deletedByCreator) {
        this.channelID = channelID;
        this.message = message;
        this.deletedByCreator = deletedByCreator;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyOwnerToDeleteMessage(message, channelID, deletedByCreator);
    }
}
