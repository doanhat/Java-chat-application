package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;

public class RefuseCreationChannelMessage extends ServerToClientMessage {

    private final Channel refusedChannel;

    public RefuseCreationChannelMessage(Channel refusedChannel) {
        this.refusedChannel = refusedChannel;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyCreationChannelRefused(refusedChannel);
    }
}