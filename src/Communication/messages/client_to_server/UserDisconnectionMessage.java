package Communication.messages.client_to_server;

import java.util.UUID;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;

/**
 * Ce message sert à indiquer au serveur un souhait de déconnexion normal.
 *
 */
public class UserDisconnectionMessage extends ClientToServerMessage {

	private static final long serialVersionUID = 2970712372437347267L;
	private final UUID userID;

    public UserDisconnectionMessage(UUID userID) {
        this.userID = userID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.disconnect(userID);
    }
}