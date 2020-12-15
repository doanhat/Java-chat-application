package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;

import java.util.UUID;

/**
 * Cette classe permet d'indiquer au client que ça demande pour quitter un channel a été validé
 */

public class ValideUserQuitMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -85202337043190892L;
    private final UUID channelID;

    public ValideUserQuitMessage(UUID channelID) {
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
            commClientController.getIHMChannelToCommunication().quitChannel(channelID);
    }
}
