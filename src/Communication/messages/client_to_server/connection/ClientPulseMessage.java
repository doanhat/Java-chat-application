package Communication.messages.client_to_server.connection;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Message servant à la verification de l'état du serveura un instant donné
 */
public class ClientPulseMessage extends ClientToServerMessage {

	private static final long serialVersionUID = -8159868108654492827L;
	private final UUID userID;
    private int nbSequence;

    public ClientPulseMessage(UUID userID) {
        this.userID = userID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.receiveClientPulse(userID);
    }
}