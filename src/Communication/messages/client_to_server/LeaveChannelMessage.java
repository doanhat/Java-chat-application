package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

public class LeaveChannelMessage extends ClientToServerMessage {
    UserLite userLite;
    Channel channel;

    public LeaveChannelMessage(UserLite userLite, Channel channel) {
        this.userLite = userLite;
        this.channel = channel;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        commServerController.notifyLeaveChannel(channel, userLite);
    }
}
