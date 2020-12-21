package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

import java.util.Arrays;
import java.util.UUID;

/**
 * Cette classe permet d'indiquer au client que ça demande pour quitter un channel a été validé
 */

public class ValideUserQuitMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -85202337043190892L;
    private final UUID channelID;
    private final UserLite user;

    public ValideUserQuitMessage(UUID channelID, UserLite user) {
        this.channelID = channelID;
        this.user = user;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.dataClientHandler().requestQuitChannel(channelID, user);
        commClientController.dataClientHandler().notifyInvisibleChannels(Arrays.asList(channelID));
    }
}
