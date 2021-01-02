package Communication.messages.client_to_server.channel_modification;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_modification.NewInvisibleChannelsMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.ChannelType;
import common.shared_data.UserLite;
import common.shared_data.Visibility;

import java.util.UUID;

public class DeleteChannelMessage extends ClientToServerMessage {

    private static final long     serialVersionUID = -22469207475665L;
    private final        UUID     channelID;
    private final        UserLite requester;

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
            if (channel.getVisibility() == Visibility.PUBLIC) {
                commController.sendBroadcast(new NewInvisibleChannelsMessage(channel.getId(), "Channel a été supprimé"), null);
            }
            else {
                commController.sendMulticast(channel.getAuthorizedPersons(),
                                             new NewInvisibleChannelsMessage(channel.getId(), "Channel a été supprimé"));
            }
        }
        else {
            System.err.println("Channel is not deleted");
        }

    }
}
