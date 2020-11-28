package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.NewVisibleChannelMessage;
import Communication.messages.server_to_client.RefuseCreationChannelMessage;
import Communication.messages.server_to_client.ValidateCreationChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.List;

public class CreateChannelMessage extends ClientToServerMessage {

    private final UserLite sender;
    private final Channel  channel;
    private final boolean  proprietaryChannel;
    private final boolean  publicChannel;

    /**
     * Message de création d'un nouveau channel
     * @param sender
     * @param channel
     * @param proprietary
     * @param publicChannel
     */
    public CreateChannelMessage(UserLite sender,
                                Channel channel,
                                boolean proprietary,
                                boolean publicChannel) {
        this.sender = sender;
        this.channel = channel;
        this.proprietaryChannel = proprietary;
        this.publicChannel = publicChannel;
    }

    /**
     * Effectue la demande de création du channel, puis avertit tous les utilisateurs de sa présence si il est public.
     * Genere un message a l'expediteur l'informant de la création.
     * @param commController
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        Channel newChannel = commController.requestCreateChannel(channel, proprietaryChannel, publicChannel, sender);

        if (newChannel != null)
        {
            // Request Accepted
            System.err.println("Serveur accepte la creation du channel" + channel.getId());

            // broadcast public channel to other users
            if (newChannel.getVisibility() == Visibility.PUBLIC) {
                NetworkMessage newChannelNotification = new NewVisibleChannelMessage(newChannel);

                commController.sendBroadcast(newChannelNotification, sender);
            }

            // return acceptation message to requester
            commController.sendMessage(sender.getId(), new ValidateCreationChannelMessage(newChannel));
        }
        else {
            // Request Refused
            System.err.println("Serveur réfuse la creation du channel" + channel.getId());

            commController.sendMessage(sender.getId(), new RefuseCreationChannelMessage(channel));
        }
    }
}
