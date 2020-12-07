package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AdminAddedMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class AddAdminSharedMessage extends ClientToServerMessage{

    private final UUID channelID;
    private final UserLite user;

    public AddAdminSharedMessage(UserLite user, Channel channel) {
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

        // Tell data server to save new admin
        commController.saveNewAdmin(channel, user);

        commController.sendMulticast(channel.getAcceptedPersons(), new AdminAddedMessage(user, channelID));
    }
}
