package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

import java.util.UUID;

/**
 * Cette classe permet de prevenir les membres d'un channel qu'un autre membre est parti
 */

public class UserLeftChannelMessage extends ServerToClientMessage {
    private static final long     serialVersionUID = -8520233704392L;
    private final        UUID     channelID;
    private final        UserLite userLite;

    public UserLeftChannelMessage(UUID channelID, UserLite userLite) {
        this.channelID = channelID;
        this.userLite  = userLite;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.channelHandler().notifyUserHasLeftChannel(channelID, userLite);
    }
}
