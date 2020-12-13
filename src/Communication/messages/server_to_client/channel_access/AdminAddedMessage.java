package Communication.messages.server_to_client.channel_access;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

import java.util.UUID;

public class AdminAddedMessage extends ServerToClientMessage {

    private static final long serialVersionUID = -19089062L;
    private final UserLite user;
    private final UUID channelID;

    public AdminAddedMessage(UserLite user, UUID channelID){
        this.user = user;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyNewAdminAdded(channelID, user);
    }
}
