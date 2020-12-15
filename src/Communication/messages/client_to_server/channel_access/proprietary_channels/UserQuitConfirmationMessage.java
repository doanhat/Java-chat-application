package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.UserQuitedChannelMessage;
import Communication.messages.server_to_client.channel_access.ValideUserQuitMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.util.UUID;

/**
 * Cette classe permet au propriétaire  d'informer qu'utilisateur a quitté un channel
 */
public class UserQuitConfirmationMessage extends ClientToServerMessage {
    private static final long serialVersionUID = -19233136521370473L;
    private final UUID channelID;
    private final UserLite userLite;

    public UserQuitConfirmationMessage(UserLite user, UUID channelID) {
        this.channelID  = channelID;
        this.userLite = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel ch = commController.getChannel(channelID);
        if(ch == null) {
            return;
        }

        commController.quitChannel(channelID, userLite);
        commController.sendMessage(userLite.getId(), new ValideUserQuitMessage(channelID, userLite));
        commController.sendMulticast(ch.getAuthorizedPersons(),
                new UserQuitedChannelMessage(channelID, userLite), userLite);

    }
}