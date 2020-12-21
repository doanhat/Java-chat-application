package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.NewUserJoinChannelMessage;
import Communication.messages.server_to_client.channel_modification.SendHistoryMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.UUID;

/**
 * Message envoyé par le client lorsque le client demande à rejoindre un channel particulier.
 */
public class AskToJoinSharedMessage extends ClientToServerMessage {

    private static final long serialVersionUID = -1923313673314704993L;
    private final UserLite sender;
    private final UUID channelID;

    public AskToJoinSharedMessage(UUID channelID, UserLite requester) {
        this.sender = requester;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        // NOTE: c'est pas nécessaire de filtrer la liste des utilisateurs connectes parce que j'ai implémenter le DF pour qu'il filtre déjà
        if (channel != null)
        {
            commController.requestJoinChannel(channel, sender);
        }
    }
}
