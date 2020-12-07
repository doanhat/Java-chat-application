package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.JoinChannelResponseMessage;
import Communication.messages.server_to_client.channel_access.NewUserJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class UserJoinedConfirmationMessage extends ClientToServerMessage {
    private final UUID channelID;
    private final UserLite user;

    public UserJoinedConfirmationMessage(UserLite user, UUID channelID) {
        this.channelID  = channelID;
        this.user = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // NOTE proprietary channels are already sync on server
        Channel channel = commController.getChannel(channelID);

        if (channel != null)
        {
            // send Acceptation back to sender
            commController.sendMessage(user.getId(), new JoinChannelResponseMessage(user, channel, true));

            // Notifie les utilisateurs connectes au channel qu'un nouveau utilisateur les rejoins
            commController.sendMulticast(channel.getAcceptedPersons(), new NewUserJoinChannelMessage(user, channel.getId()));
        }
        else
        {
            commController.sendMessage(user.getId(), new JoinChannelResponseMessage(user, null, false));
        }
    }
}
