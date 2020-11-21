package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.UUID;

public class ReceiveMessageMessage extends ServerToClientMessage {

    private Message message;
    private UUID    channelID;
    private Message response;

    public ReceiveMessageMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyReceiveMessage(message, channelID, response);
    }
}
