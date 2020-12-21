package Communication.messages.client_to_server.channel_access;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.UserQuitedChannelMessage;
import Communication.messages.server_to_client.channel_access.ValideUserQuitMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.UUID;

/**
 * Cette classe permet de quitter un channel privé partagé
 */
public class QuitChannelMessage extends ClientToServerMessage {
    private static final long serialVersionUID = -1923313673314074993L;
    private final UserLite userLite;
    private final UUID channelID;

    public QuitChannelMessage(UserLite userLite, UUID channelID) {
        this.userLite = userLite;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        Channel channel = commServerController.getChannel(channelID);

        if(channel == null) {
            return;
        }

        commServerController.quitChannel(channelID, userLite);
        commServerController.sendMessage(userLite.getId(), new ValideUserQuitMessage(channelID, userLite));
        commServerController.sendMulticast(channel.getAuthorizedPersons(), new UserQuitedChannelMessage(channelID, userLite), userLite);
    }
}