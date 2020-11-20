package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.ReceiveMessageMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.*;

public class SendMessageMessage extends ClientToServerMessage {

    private Message message;
    private Channel channel;
    private Message response;

    public SendMessageMessage(Message message, Channel channel, Message response) {
        this.message = message;
        this.channel = channel;
        this.response = response;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        if (channel.getClass() == SharedChannel.class) {
            commController.requestSendMessage(message, channel, response);
            for (UserLite user: channel.getAcceptedPersons()) {
                commController.sendMessage(
                        user.getId(),
                        new ReceiveMessageMessage(message, channel, response));
            }
        }
    }
}
