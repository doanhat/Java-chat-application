package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.propietary_channels.TellOwnerUserQuitMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.UserLite;

import java.util.UUID;

/**
* Cette classe permet de demander au server de quitter un channel
 */
public class QuitPropChannelMessage extends ClientToServerMessage {
    private static final long serialVersionUID = -1923373314704993L;
    private final UserLite userLite;
    private final UUID channelID;
    private final UserLite owner;

    public QuitPropChannelMessage(UserLite userLite, UUID channelID, UserLite owner) {
        this.userLite = userLite;
        this.channelID = channelID;
        this.owner = owner;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        if (userLite.getId().equals(owner.getId())) {
            // owner leave channel
            commServerController.quitChannel(channelID, userLite);
        }
        else {
            commServerController.sendMessage(owner.getId(), new TellOwnerUserQuitMessage(userLite, channelID));
        }
    }
}
