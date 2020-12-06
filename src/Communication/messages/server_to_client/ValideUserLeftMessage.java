package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

/**
 * Cette classe permet d'indiquer au client que ça demande pour quitter un channel a été validé
 */

public class ValideUserLeftMessage extends ServerToClientMessage {
    UUID channelID;

    public void setChannelID(UUID channelID) {
        this.channelID = channelID;
    }

    public UUID getChannelID() {
        return channelID;
    }

    public ValideUserLeftMessage(UUID channelID) {
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyValidateDeletionChannel(channelID);
    }
}
