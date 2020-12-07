package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.ValidateDeletionChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.*;

import java.util.UUID;

/**
 * Demande de suppression d'un channel
 */
public class DeleteChannelMessage extends ClientToServerMessage {

    private final UUID channelID;
    private final UserLite requester;

    public DeleteChannelMessage(UUID channelID, UserLite requester) {
        this.channelID = channelID;
        this.requester = requester;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel == null) {
            return;
        }

        commController.requestDeleteChannel(channel, requester);
        commController.sendBroadcast(new ValidateDeletionChannelMessage(channel.getId()), requester);
    }
}
