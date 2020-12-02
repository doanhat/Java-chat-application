package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;

import java.util.UUID;

/**
 * Indique au utilisateur qu'un message à été supprimé
 */
public class DeleteMessageMessage extends ServerToClientMessage {
    Message message;
    UUID channelUUID;
    Boolean deleteByCreator;

    public DeleteMessageMessage(Message message, UUID channelID, Boolean deleteByCreator) {
        this.message = message;
        this.channelUUID = channelID;
        this.deleteByCreator = deleteByCreator;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyDeletedMessage(message, channelUUID, deleteByCreator);
    }
}
