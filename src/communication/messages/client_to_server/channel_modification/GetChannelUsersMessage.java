package communication.messages.client_to_server.channel_modification;

import communication.messages.abstracts.ClientToServerMessage;
import communication.messages.server_to_client.channel_modification.SendChannelUsersMessage;
import communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.UUID;

public class GetChannelUsersMessage extends ClientToServerMessage {

    private static final long     serialVersionUID = 769207475665L;
    private final        UUID     channelID;
    private final        UserLite sender;

    /**
     * Message vers le serveur demandant l'envoi du contenu actuel d'un channel
     * @param channelID Channel concerne
     * @param sender Utilisateur faisant la demande
     */
    public GetChannelUsersMessage(UUID channelID, UserLite sender) {
        this.channelID = channelID;
        this.sender    = sender;
    }

    @Override
    protected void handle(CommunicationServerController commController) {

        // NOTE : Server should have the list of all active channels, proprietary and shared
        Channel channel = commController.getChannel(channelID);

        if (channel != null) {
            commController.sendMessage(sender.getId(),
                                       new SendChannelUsersMessage(channelID,
                                                                   commController.channelConnectedUsers(channel)));
        }
    }
}
