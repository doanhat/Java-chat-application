package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.ValidateDeletionChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.*;

/**
 * Demande de suppression d'un channel
 */
public class DeleteChannelMessage extends ClientToServerMessage {

    private final Channel channel;
    private final UserLite requester;

    public DeleteChannelMessage(Channel channel, UserLite requester) {
        this.channel = channel;
        this.requester = requester;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.requestDeleteChannel(channel, requester);
        commController.sendBroadcast(new ValidateDeletionChannelMessage(channel.getId()), requester);
    }
}
