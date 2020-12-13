package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;

import java.util.UUID;

/**
 * Cette classe permet d'indiquer au client que ça demande pour quitter un channel a été validé
 */

public class ValideUserLeftMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -85202337043190892L;
    private final UUID channelID;
    private final UUID user;
    private final UUID owner;
    private final boolean isOwned;

    public ValideUserLeftMessage(UUID channelID, UUID user, UUID owner, boolean isOwned) {
        this.channelID = channelID;
        this.user = user;
        this.owner = owner;
        this.isOwned = isOwned;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        // TODO interfation team'comment : WHY ???? Pourquoi quand un l'utilisateur se deconnecte d'un channel, si celui-ci n'est pas publique, il faut l'enlever ?????
//        // TODO INTEGRATION V2, Check if it do more than this
//        if (!isPublicChannel) {
//            commClientController.notifyInvisibleChannels(Collections.singletonList(channelID));
//        }
        if (isOwned && user.equals(owner)) {
            commClientController.removeAllJoinsPersonsToProprietaryChannel(channelID, owner);
        }
    }
}
