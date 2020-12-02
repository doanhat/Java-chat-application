package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

/**
* Cette classe permet de demander au server de quitter un channel
 */
public class LeaveChannelMessage extends ClientToServerMessage {
    UserLite userLite;
    UUID channel;

    public LeaveChannelMessage(UserLite userLite, UUID channelID) {
        this.userLite = userLite;
        this.channel = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        commServerController.notifyLeaveChannel(channel, userLite);
    }
}
