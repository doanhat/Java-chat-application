package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

import java.util.UUID;

public class LeaveSharedChannelMessage extends ClientToServerMessage {
    private final UserLite userLite;
    private final UUID channel;

    public LeaveSharedChannelMessage(UserLite userLite, UUID channelID) {
        this.userLite = userLite;
        this.channel = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        commServerController.leaveChannel(channel, userLite);
    }
}