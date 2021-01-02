package Communication.messages.client_to_server.channel_modification.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.channel_modification.NewVisibleChannelMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.Kick;
import common.shared_data.UserLite;
import common.shared_data.Visibility;

import java.util.ArrayList;
import java.util.List;

public class SendProprietaryChannelsMessage extends ClientToServerMessage {

    private static final long          serialVersionUID = 7561720469207475665L;
    private final        UserLite      owner;
    private final        List<Channel> proprietaryChannels;

    public SendProprietaryChannelsMessage(UserLite owner, List<Channel> proprietaryChannels) {
        this.owner               = owner;
        this.proprietaryChannels = proprietaryChannels;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // NOTE: Same method requestCreateChannel() for shared and proprietary channels to be registered by Server
        for (Channel channel : proprietaryChannels) {
            boolean isPublicChannel   = channel.getVisibility().equals(Visibility.PUBLIC);
            Channel registeredChannel = commController.requestCreateChannel(channel, false, isPublicChannel, owner);

            if (registeredChannel != null) {
                // Even to own channel, we add to join list inside server because it's need it send is message
                commController.requestJoinChannel(registeredChannel, owner);

                NetworkMessage newChannelNotification = new NewVisibleChannelMessage(registeredChannel);

                List<UserLite> connectedUsers;

                if (isPublicChannel) {
                    connectedUsers = new ArrayList<>(commController.onlineUsers());
                }
                else {
                    connectedUsers = new ArrayList<>(channel.getAuthorizedPersons());
                }

                for (Kick kick : channel.getKicked()) {
                    connectedUsers.removeIf(u -> u.getId().equals(kick.getUser().getId()));
                }

                System.out.println("Broadcast proprietary channel to " + connectedUsers);

                commController.sendMulticast(channel.getAuthorizedPersons(), newChannelNotification, owner);
            }
        }
    }
}
