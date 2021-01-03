package communication.messages.server_to_client.channel_access;

import communication.client.CommunicationClientController;
import communication.messages.abstracts.ServerToClientMessage;

import java.util.UUID;

/**
 * Cette classe permet d'indiquer au client que ça demande pour quitter un channel a été validé
 */

public class ValideUserLeftMessage extends ServerToClientMessage {
    private static final long    serialVersionUID = -85202337043190892L;
    private final        UUID    channelID;
    private final        UUID    user;
    private final        UUID    owner;
    private final        boolean isOwned;

    public ValideUserLeftMessage(UUID channelID, UUID user, UUID owner, boolean isOwned) {
        this.channelID = channelID;
        this.user      = user;
        this.owner     = owner;
        this.isOwned   = isOwned;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        if (isOwned && user.equals(owner)) {
            commClientController.dataClientHandler().removeAllJoinsPersonsToProprietaryChannel(channelID, owner);
        }
    }
}
