package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;

public class ValidateCreationChannelMessage extends ServerToClientMessage {
    @Override
    protected void handle(CommunicationClientController commController) {
        // TODO notify Client that channel is created
    }
}
