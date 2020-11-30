package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;

import java.util.UUID;

/**
 * Message de demande au client d'enregistrement d'un message particulier
 *
 */
public class TellOwnerToSaveMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -8527233704319089062L;
	private final Message message;
    private final UUID    channelID;
    private final Message response;

    public TellOwnerToSaveMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.saveMessage(message, channelID, response);
    }
}
