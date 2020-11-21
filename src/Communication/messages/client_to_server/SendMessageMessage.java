package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.ReceiveMessageMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.*;

import java.util.UUID;

public class SendMessageMessage extends ClientToServerMessage {

    private Message message;
    private UUID channelID;
    private Message response;

    public SendMessageMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // TODO get Channel from server using channelID
        Channel channel = null;

        if (channel.getClass() == SharedChannel.class) {
            commController.requestSendMessage(message, channel, response);

            for (UserLite user: channel.getAcceptedPersons()) {
                commController.sendMessage(user.getId(),
                                           new ReceiveMessageMessage(message, channelID, response));
            }
        }
    }
}
