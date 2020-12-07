package Communication.messages.client_to_server.channel_modification.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.channel_modification.NewVisibleChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.List;

public class SendProprietaryChannelsMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 7561720469207475665L;
    private final UserLite owner;
    private final List<Channel> proprietaryChannels;

    public SendProprietaryChannelsMessage(UserLite owner, List<Channel> proprietaryChannels) {
        this.owner = owner;
        this.proprietaryChannels = proprietaryChannels;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // NOTE: Same method requestCreateChannel() for shared and proprietary channels to be registered by Server
        for (Channel channel : proprietaryChannels) {
            boolean isPublicChannel = channel.getVisibility() == Visibility.PUBLIC;
            Channel registeredChannel = commController.requestCreateChannel(channel, false, isPublicChannel, owner);

            if (registeredChannel != null && isPublicChannel) {
                NetworkMessage newChannelNotification = new NewVisibleChannelMessage(registeredChannel);

                commController.sendBroadcast(newChannelNotification, owner);
            }
        }
    }
}
