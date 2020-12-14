package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

import java.util.UUID;

public class UserDisconnectionMessage extends ClientToServerMessage {

    private final UUID userID;

    public UserDisconnectionMessage(UUID userID) {
        this.userID = userID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // TODO handle client disconnection
        commController.disconnect(userID);
    }
}