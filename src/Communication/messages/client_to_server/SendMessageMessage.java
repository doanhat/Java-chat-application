package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.ReceiveMessageMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.*;

import java.util.UUID;

public class SendMessageMessage extends ClientToServerMessage {

    private Message message;
    private UUID    channelID;
    private Message response;

    public SendMessageMessage(Message message, UUID channelID, Message response) {
        this.message    = message;
        this.channelID  = channelID;
        this.response   = response;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        // Handle shared Channel
        if (channel.getClass() == SharedChannel.class) {
            // Tell data server to save message
            commController.saveMessage(message, channel, response);

            // TODO Tell owner to save message
        }

        NetworkMessage forwardedMessage = new ReceiveMessageMessage(message, channelID, response);

        for (UserLite user: channel.getAcceptedPersons()) {
            commController.sendMessage(user.getId(), forwardedMessage);
        }
    }
}
