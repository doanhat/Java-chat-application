package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.AdminRemovedMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class RemoveAdminSharedMessage extends ClientToServerMessage {

    private static final long serialVersionUID = -192331367334854993L;
    private final UUID channelID;
    private final UserLite user;

    public RemoveAdminSharedMessage(UserLite user, Channel channel) {
        this.channelID  = channel.getId();
        this.user = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // Handle shared Channel
        Channel channel = commController.getChannel(channelID);

        if (channel == null)
        {
            return;
        }

        commController.removeAdmin(channel, user);

        commController.sendMulticast(channel.getAcceptedPersons(), new AdminRemovedMessage(user, channelID));
    }
}