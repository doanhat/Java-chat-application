package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

public class AdminAddedMessage extends ServerToClientMessage {

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
