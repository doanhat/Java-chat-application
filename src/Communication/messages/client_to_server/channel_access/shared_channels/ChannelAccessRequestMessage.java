package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.common.ChannelAccessRequest;
import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.propietary_channels.TellOwnerAccessRequestMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.ChannelType;
import common.shared_data.UserLite;

import java.util.UUID;

public class ChannelAccessRequestMessage extends ClientToServerMessage {

    private static final long serialVersionUID = -1923313673314704993L;
    private final ChannelAccessRequest request;
    private final UserLite sender;
    private final UUID channelID;

    public ChannelAccessRequestMessage(ChannelAccessRequest request, UUID channelID, UserLite requester) {
        this.request = request;
        this.sender = requester;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel != null)
        {
            if (channel.getType() == ChannelType.OWNED && !channel.getCreator().getId().equals(sender.getId())) {
                commController.sendMessage(channel.getCreator().getId(), new TellOwnerAccessRequestMessage(request, sender, channelID));
            }
            else {
                commController.changeChannelAccess(request, channel, sender);
            }
        }
    }
}
