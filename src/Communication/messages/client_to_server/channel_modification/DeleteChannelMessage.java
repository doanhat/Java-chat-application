package Communication.messages.client_to_server.channel_modification;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_modification.NewInvisibleChannelsMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.*;

import java.util.UUID;

public class DeleteChannelMessage extends ClientToServerMessage {

    private static final long serialVersionUID = -22469207475665L;
    private final UUID channelID;
    private final UserLite requester;

    public DeleteChannelMessage(UUID channelID, UserLite requester) {
        this.channelID = channelID;
        this.requester = requester;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel == null) {
            return;
        }

        if (commController.requestDeleteChannel(channel, requester)) {
            // TODO INTEGRATION V2: verify sequence of delete proprietary channel
            if (channel.getType() == ChannelType.OWNED) {
                // TODO Send request delete channel to Owner
            }

            if (channel.getVisibility() == Visibility.PUBLIC) {
                commController.sendBroadcast(new NewInvisibleChannelsMessage(channel.getId()), requester);
            }
            else {
                commController.sendMulticast(channel.getAcceptedPersons(), new NewInvisibleChannelsMessage(channel.getId()));
            }
        }

    }
}