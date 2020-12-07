package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;

import java.util.Collections;
import java.util.UUID;

/**
 * Cette classe permet d'indiquer au client que ça demande pour quitter un channel a été validé
 */

public class ValideUserLeftMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -85202337043190892L;
    private final UUID channelID;
    private final boolean isPublicChannel;

    public ValideUserLeftMessage(UUID channelID, boolean isPublicChannel) {
        this.channelID = channelID;
        this.isPublicChannel = isPublicChannel;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        // TODO INTEGRATION V2, Check if it do more than this
        if (!isPublicChannel) {
            commClientController.notifyInvisibleChannels(Collections.singletonList(channelID));
        }
    }
}
