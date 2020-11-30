package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Ce message sert à indiquer au serveur un souhait de déconnexion normal.
 *
 */
public class UserDisconnectionMessage extends ClientToServerMessage {

    private final UUID userID;

    public UserDisconnectionMessage(UUID userID) {
        this.userID = userID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.disconnect(userID);
    }
}