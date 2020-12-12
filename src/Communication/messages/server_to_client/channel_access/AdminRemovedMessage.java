package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

import java.util.UUID;

public class AdminRemovedMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -19021313562L;
    private final UserLite user;
    private final UUID channelID;

    public AdminRemovedMessage(UserLite user, UUID channelID){
        this.user = user;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyAdminRemoved(channelID, user);
    }
}