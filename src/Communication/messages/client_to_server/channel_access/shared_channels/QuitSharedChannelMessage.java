package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.ValideUserQuitMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.UserLite;

import java.util.UUID;

public class QuitSharedChannelMessage extends ClientToServerMessage {
    private final UserLite userLite;
    private final UUID channel;

    public QuitSharedChannelMessage(UserLite userLite, UUID channelID) {
        this.userLite = userLite;
        this.channel = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        commServerController.quitChannel(channel, userLite);
        commServerController.sendMessage(userLite.getId(), new ValideUserQuitMessage(channel));
    }
}