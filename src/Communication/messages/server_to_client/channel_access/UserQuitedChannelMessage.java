package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

import java.util.UUID;

/**
 * Cette classe permet de prevenir les membres d'un channel qu'un autre membre est parti
 */

public class UserQuitedChannelMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -8524233704392L;
    private final UUID channelID;
    private final  UserLite userLite;

    public UserQuitedChannelMessage(UUID channelID, UserLite userLite) {
        this.channelID = channelID;
        this.userLite = userLite;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.channelHandler().notifyUserHasLeftChannel(channelID, userLite);
        commClientController.channelHandler().hasQuitedChannel(channelID, userLite);
    }
}
