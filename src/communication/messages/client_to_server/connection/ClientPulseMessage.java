package communication.messages.client_to_server.connection;

import communication.messages.abstracts.ClientToServerMessage;
import communication.server.CommunicationServerController;

import java.util.UUID;

public class ClientPulseMessage extends ClientToServerMessage {

    private static final long serialVersionUID = -8159868108654492827L;
    private final        UUID userID;

    /**
     * Message keepAlise
     * @param userID    Utilisateur
     */
    public ClientPulseMessage(UUID userID) {
        this.userID = userID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.receiveClientPulse(userID);
    }
}