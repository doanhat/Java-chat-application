package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.propietary_channels.TellOwnerToAddAdminMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.UUID;

public class AddAdminPropMessage extends ClientToServerMessage{

    private final UUID channelID;
    private final UUID channelCreatorID;
    private final UserLite user;

    /**
     * Message de demande d'ajout d'admin sur le serveur.
     * @param user [UserLite] nouveau admin
     * @param channel [Channel] ID du channel
     */
    public AddAdminPropMessage(UserLite user, Channel channel) {
        this.channelID  = channel.getId();
        this.channelCreatorID = channel.getCreator().getId();
        this.user = user;
    }

    /**
     * Si le channel est partagé, enregistre la modification, et notifie tous les utilisateurs du channel
     * Si le channel est proprietaire, Notifie le propriétaire de la demande.
     * @param commController Controller du serveur
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        //Handle proprietary channel. Tell Owners to add admins
        commController.sendMessage(channelCreatorID, new TellOwnerToAddAdminMessage(user, channelID));
    }
}
