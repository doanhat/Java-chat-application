package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.JoinChannelResponseMessage;
import Communication.messages.server_to_client.channel_access.NewUserJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.UUID;

public class UserJoinedConfirmationMessage extends ClientToServerMessage {
    private static final long serialVersionUID = -223713673314704993L;
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
            // Even to own channel, we add in join list inside server because it's need it send is message
            commController.requestJoinChannel(channel, user);
            // send Acceptation back to sender
            commController.sendMessage(user.getId(),
                    new JoinChannelResponseMessage(channel, commController.channelConnectedUsers(channel), true));

            // Notifie les utilisateurs connectes au channel qu'un nouveau utilisateur les rejoins
            commController.sendMulticast(channel.getJoinedPersons(), new NewUserJoinChannelMessage(user, channel.getId()), user);
        }
        else
        {
            commController.sendMessage(user.getId(), new JoinChannelResponseMessage(null, null, false));
        }
    }
}
