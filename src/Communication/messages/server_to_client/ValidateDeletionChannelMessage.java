package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;

import java.util.UUID;

public class ValidateDeletionChannelMessage extends ServerToClientMessage {
    private UUID channel;

    public UUID getChannel() {
        return channel;
    }

    public void setChannel(UUID channel) {
        this.channel = channel;
    }

    public ValidateDeletionChannelMessage(UUID channel) {
        this.channel = channel;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyValidateDeletionChannel(channel);
    }

}