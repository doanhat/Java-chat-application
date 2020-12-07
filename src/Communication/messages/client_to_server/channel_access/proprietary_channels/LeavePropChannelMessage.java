package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.propietary_channels.TellOwnerUserJoinedMessage;
import Communication.messages.server_to_client.channel_access.propietary_channels.TellOwnerUserLeftMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

import java.util.UUID;

/**
* Cette classe permet de demander au server de quitter un channel
 */
public class LeavePropChannelMessage extends ClientToServerMessage {
    private static final long serialVersionUID = -1923373314704993L;
    private final UserLite userLite;
    private final UUID channelID;
    private final UserLite owner;

    public LeavePropChannelMessage(UserLite userLite, UUID channelID, UserLite owner) {
        this.userLite = userLite;
        this.channelID = channelID;
        this.owner = owner;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        commServerController.sendMessage(owner.getId(), new TellOwnerUserLeftMessage(userLite, channelID));
    }
}
