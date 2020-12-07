package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.JoinChannelResponseMessage;
import Communication.messages.server_to_client.channel_access.NewUserJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

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
        if (channel != null && commController.requestJoinChannel(channel, sender))
        {
            // send Acceptation back to sender
            commController.sendMessage(sender.getId(), new JoinChannelResponseMessage(sender, channel, true));

            // Notifie les utilisateurs connectes au channel qu'un nouveau utilisateur les rejoins
            commController.sendMulticast(channel.getAcceptedPersons(), new NewUserJoinChannelMessage(sender, channelID));
        }
        else {
            // send Refusal back to sender
            commController.sendMessage(sender.getId(), new JoinChannelResponseMessage(sender, null, false));
        }
    }
}
