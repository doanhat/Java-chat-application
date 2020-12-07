package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Message;

import java.util.UUID;

public class ReceiveMessageMessage extends ServerToClientMessage {

    private final Message message;
    private final UUID    channelID;
    private final Message response;

    public ReceiveMessageMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        System.out.println("Test");
        commController.notifyReceiveMessage(message, channelID, response);
    }
}
