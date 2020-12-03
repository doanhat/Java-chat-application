package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;

public class ValidateCreationChannelMessage extends ServerToClientMessage {

    private final Channel newChannel;

    public ValidateCreationChannelMessage(Channel newChannel) {
        this.newChannel = newChannel;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        System.err.println("Creation channel" + newChannel.getId() + " est accepté");
        commController.notifyChannelCreated(newChannel);
    }
}
