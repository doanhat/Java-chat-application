package Communication.messages.client_to_server.connection;

import java.util.UUID;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;

public class ClientPulseMessage extends ClientToServerMessage {

	private static final long serialVersionUID = -8159868108654492827L;
	private final UUID userID;

    public ClientPulseMessage(UUID userID) {
        this.userID = userID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.receiveClientPulse(userID);
    }
}