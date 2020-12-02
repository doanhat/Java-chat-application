package Communication.messages.client_to_server.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AdminAddedMessage;
import Communication.messages.server_to_client.ReceiveMessageMessage;
import Communication.messages.server_to_client.TellOwnerToAddAdminMessage;
import Communication.messages.server_to_client.TellOwnerToSaveMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.ChannelType;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

public class AddAdminSharedMessage extends ClientToServerMessage{

    private final UUID channelID;
    private final UserLite user;

    /**
     * Message de demande d'ajout d'admin sur le serveur.
     * @param user [UserLite] nouveau admin
     * @param channel [Channel] ID du channel
     */
    public AddAdminSharedMessage(UserLite user, Channel channel) {
        this.channelID  = channel.getId();
        this.user = user;
    }

    /**
     * Si le channel est partagé, enregistre la modification, et notifie tous les utilisateurs du channel
     * Si le channel est proprietaire, Notifie le propriétaire de la demande.
     * @param commController Controller du serveur
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        // Handle shared Channel
        Channel channel = commController.getChannel(channelID);

        if (channel != null)
        {
                //System.err.println("Channel n'est pas trouvé");

            return;
        }

            // Tell data server to save new admin
        commController.saveNewAdmin(channel, user);

        commController.sendMulticast(channel.getAcceptedPersons(),
                    new AdminAddedMessage(user, channelID),
                    null);
    }
}
