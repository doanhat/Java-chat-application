package Communication.messages.server_to_client.chat_action;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;

import java.util.UUID;

public class ReceiveMessageMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -2578930171910220363L;
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
        commController.dataClientHandler().notifyReceiveMessage(message, channelID, response);
    }
}