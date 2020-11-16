package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.AcceptationMessage;
import Communication.messages.server_to_client.ValidateCreationChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.List;

public class CreateChannelMessage extends ClientToServerMessage {

    private UserLite sender;
    private Channel channel;
    private boolean proprietaryChannel;
    private boolean publicChannel;

    // TODO: verify with DATA to agree on the organization of data structure
    public CreateChannelMessage(UserLite sender,
                                Channel channel,
                                boolean proprietary,
                                boolean publicChannel) {
        this.sender = sender;
        this.channel = channel;
        this.proprietaryChannel = proprietary;
        this.publicChannel = publicChannel;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel newChannel = commController.requestCreateChannel(channel, proprietaryChannel, publicChannel, sender);

        if (newChannel != null)
        {
            // return acceptation message to requester
            commController.sendMessage(sender.getId(), new ValidateCreationChannelMessage());

            if (newChannel.getVisibility() == Visibility.PUBLIC) {
                // TODO init ChannelVisibleMessage
                NetworkMessage newChannelNotification = null;

                List<UserLite> onlineUsers = commController.onlineUsers();

                for (UserLite otherUser: onlineUsers) {
                    commController.sendMessage(otherUser.getId(), newChannelNotification);
                }
            }

        }
    }
}
