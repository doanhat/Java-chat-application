package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.AdminAddedMessage;
import Communication.messages.server_to_client.channel_access.AdminRemovedMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class AdminRemovedConfirmation extends ClientToServerMessage {

    private static final long serialVersionUID = -751564313314704L;
    private final UUID channelID;
    private final UserLite user;

    public AdminRemovedConfirmation(UserLite user, UUID channelID) {
        this.channelID  = channelID;
        this.user = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel == null)
        {
            return;
        }

        // TODO INTEGRATION V3: ask Data to remove admin in proprietary channel object on server to stay in sync with Owner
        commController.removeAdmin(channel, user);

        commController.sendMulticast(channel.getAcceptedPersons(), new AdminRemovedMessage(user, channelID));
    }
}