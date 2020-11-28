package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AdminAddedMessage;
import Communication.messages.server_to_client.ReceiveMessageMessage;
import Communication.messages.server_to_client.TellOwnerToSaveMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.ChannelType;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

public class AddAdminMessage extends ClientToServerMessage{

    private final UUID channelID;
    private final UserLite user;

    /**
     * Message de demande d'ajout d'admin sur le serveur.
     * @param user
     * @param channelID
     */
    public AddAdminMessage(UserLite user, UUID channelID) {
        this.channelID  = channelID;
        this.user = user;
    }

    /**
     * Si le channel est partagé, enregistre la modification, et notifie tous les utilisateurs du channel
     * Si le channel est proprietaire, Notifie le propriétaire de la demande.
     * @param commController Controller du serveur
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        // Handle shared Channel
        if (channel.getType() == ChannelType.SHARED) {
            // Tell data server to save new admin
            commController.saveNewAdmin(channel, user);

            commController.sendMulticast(channel.getAcceptedPersons(),
                    new AdminAddedMessage(user, channelID),
                    null);
        }
        else {
            //commController.sendMessage(channel.getCreator().getId(), new TellOwnerToAddAdminMessage(user, channelID));
        }
    }

}
