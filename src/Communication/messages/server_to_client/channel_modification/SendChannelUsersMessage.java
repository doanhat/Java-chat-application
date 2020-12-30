package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

import java.util.List;
import java.util.UUID;

public class SendChannelUsersMessage extends ServerToClientMessage {

    private static final long           serialVersionUID = -164319089062L;
    private final        UUID           channelID;
    private final        List<UserLite> activeUsers;

    public SendChannelUsersMessage(UUID channelID, List<UserLite> activeUsers) {
        this.channelID   = channelID;
        this.activeUsers = activeUsers;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.mainHandler().deliverChannelUsersList(channelID, activeUsers);
    }
}
