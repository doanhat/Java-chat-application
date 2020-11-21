package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.Message;

public class ReceiveMessageMessage extends ServerToClientMessage {

    private Message message;
    private Channel channel;
    private Message response;

    public ReceiveMessageMessage(Message message, Channel channel, Message response) {
        this.message = message;
        this.channel = channel;
        this.response = response;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyReceiveMessage(message, channel, response);
    }
}
