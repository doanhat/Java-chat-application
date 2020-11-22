package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;

import java.util.UUID;

public class TellOwnerToSaveMessage extends ServerToClientMessage {

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
