package communication.messages.server_to_client.connection;

import communication.client.CommunicationClientController;
import communication.messages.abstracts.ServerToClientMessage;
import communication.messages.client_to_server.connection.ClientPulseMessage;

/**
 * Classe de message de réponse du serveur au client à ses demandes de keepalive via {@link ClientPulseMessage}
 */
public class ReplyClientPulseMessage extends ServerToClientMessage {
    private static final long serialVersionUID = 4397161634935162347L;

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.receiveServerHeartBeat();
    }
}