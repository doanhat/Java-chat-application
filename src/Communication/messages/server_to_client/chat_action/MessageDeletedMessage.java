package Communication.messages.server_to_client.chat_action;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Message;

import java.util.UUID;

/**
 * Indique au utilisateur qu'un message à été supprimé
 */
public class MessageDeletedMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -8527233704319742L;
    private final Message message;
    private final UUID channelUUID;
    private final boolean deleteByCreator;

    public MessageDeletedMessage(Message message, UUID channelID, Boolean deleteByCreator) {
        this.message = message;
        this.channelUUID = channelID;
        this.deleteByCreator = deleteByCreator;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyDeletedMessage(message, channelUUID, deleteByCreator);
    }
}
