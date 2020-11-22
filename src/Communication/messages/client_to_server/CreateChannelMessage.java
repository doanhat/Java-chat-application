package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.NewVisibleChannelMessage;
import Communication.messages.server_to_client.ValidateCreationChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.List;

public class CreateChannelMessage extends ClientToServerMessage {

    private UserLite sender;
    private Channel  channel;
    private boolean  proprietaryChannel;
    private boolean  publicChannel;

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

        // Request Accepted
        if (newChannel != null)
        {
            // broadcast public channel to other users
            if (newChannel.getVisibility() == Visibility.PUBLIC) {
                NetworkMessage newChannelNotification = new NewVisibleChannelMessage(newChannel);

                commController.sendBroadcast(newChannelNotification, sender);
            }

            // return acceptation message to requester
            commController.sendMessage(sender.getId(), new ValidateCreationChannelMessage(newChannel));
        }
    }
}
