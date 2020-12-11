package Communication.messages.client_to_server.channel_modification.shared_channels;

import java.util.logging.Level;
import java.util.logging.Logger;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.channel_modification.NewVisibleChannelMessage;
import Communication.messages.server_to_client.channel_modification.shared_channels.ChannelCreationResponseMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

/**
 * Cette classe correspond à un message indiquant la volonté de création d'un nouveau channel au serveur
 *
 */
public class CreateSharedChannelMessage extends ClientToServerMessage {

	private static final long serialVersionUID = 7561722469207475665L;
	private final UserLite sender;
    private final Channel  channel;
    private final boolean  isShared;
    private final boolean  isPublic;

    /**
     * Constructeur principal de la classe
     * @param sender Utilisateur qui souhaite la construction du canal
     * @param channel Canal à créer
     * @param isShared true si le canal doit être partagé
     * @param isPublic true si le canal est public, false si il est privé
     */
    public CreateSharedChannelMessage(UserLite sender,
                                      Channel channel,
                                      boolean isShared,
                                      boolean isPublic) {
        this.sender = sender;
        this.channel = channel;
        this.isShared = isShared;
        this.isPublic = isPublic;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel newChannel = commController.requestCreateChannel(channel, isShared, isPublic, sender);

        Logger logger = Logger.getLogger(this.getClass().getName());

        // NOTE: Same procedure for shared and proprietary channels,
        // shared channel is created by IHM main, proprietary channel is created by Data Client
        // But both need to register with Server by method requestCreateChannel()
        if (newChannel != null)
        {
            // Request Accepted
            logger.log(Level.INFO, "Serveur accepte la creation du channel {}" , newChannel.getId());

            commController.sendMessage(sender.getId(), new ChannelCreationResponseMessage(newChannel, true));

            NetworkMessage newChannelNotification = new NewVisibleChannelMessage(newChannel);

            if (newChannel.getVisibility() == Visibility.PUBLIC) {
                // broadcast public channel to other users
                commController.sendBroadcast(newChannelNotification, sender);
            }
            else {
                // multicast private channel to invited users
                commController.sendMulticast(channel.getJoinedPersons(), newChannelNotification);
            }
        }
        else {
            // Request Refused
        	logger.log(Level.WARNING, "Serveur réfuse la creation du channel {}" , channel.getId());

            commController.sendMessage(sender.getId(), new ChannelCreationResponseMessage(channel, false));
        }
    }
}
