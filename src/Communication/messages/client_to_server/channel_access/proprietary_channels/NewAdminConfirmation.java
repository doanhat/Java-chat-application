package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.AdminAddedMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class NewAdminConfirmation extends ClientToServerMessage {

    private final UUID channelID;
    private final UserLite user;

    public NewAdminConfirmation(UserLite user, UUID channelID) {
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

        commController.sendMulticast(channel.getAcceptedPersons(), new AdminAddedMessage(user, channelID));
    }
}