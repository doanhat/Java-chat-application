package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.ReceiveMessageMessage;
import Communication.messages.server_to_client.TellOwnerToSaveMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.*;

import java.util.UUID;

public class SendMessageMessage extends ClientToServerMessage {

    private final Message message;
    private final UUID    channelID;
    private final Message response;

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

            NetworkMessage tellOwnersMessage = new TellOwnerToSaveMessage(message, channelID, response);

            for (UserLite user: channel.getAcceptedPersons()) {
                commController.sendMessage(user.getId(), tellOwnersMessage);
            }
        }

        /**
         * TODO INTEGRATION Verify with Data if this is an if else situation where TellOwnerToSaveMessage
         * can replace ReceiveMessageMessage in case of shared Channel
         */
        NetworkMessage forwardedMessage = new ReceiveMessageMessage(message, channelID, response);

        for (UserLite user: channel.getAcceptedPersons()) {
            commController.sendMessage(user.getId(), forwardedMessage);
        }
    }
}
