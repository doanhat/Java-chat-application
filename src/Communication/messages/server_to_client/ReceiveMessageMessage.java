package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.Message;

import java.util.UUID;

public class ReceiveMessageMessage extends ServerToClientMessage {

    private final Message message;
    private final UUID    channelID;
    private final Message response;

    /**
     * Message indiquant qu'un nouveau message est arrivé dans un channel
     * @param message
     * @param channelID
     * @param response
     */
    public ReceiveMessageMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    /**
     *Notifie le controller du nouveau message
     * @param commController
     */
    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyReceiveMessage(message, channelID, response);
    }
}
