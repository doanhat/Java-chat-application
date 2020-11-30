package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AdminAddedMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class NewAdminConfirmation extends ClientToServerMessage {

    private final UUID channelID;
    private final UserLite user;

    /**
     * Message de confirmation d'ajout d'admin sur l'application du createur.
     * @param user [UserLite] nouveau admin
     * @param channelID [UUID] ID du channel
     */
    public NewAdminConfirmation(UserLite user, UUID channelID) {
        this.channelID  = channelID;
        this.user = user;
    }

    /**
     * Notifie tous les utilisateurs du channel
     * @param commController Controller du serveur
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel != null)
        {
            //System.err.println("Channel n'est pas trouvé");

            return;
        }

        commController.sendMulticast(channel.getAcceptedPersons(),
                                     new AdminAddedMessage(user, channelID),
                                     null);
    }
}