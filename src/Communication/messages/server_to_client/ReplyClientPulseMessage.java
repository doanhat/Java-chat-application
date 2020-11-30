package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.ClientPulseMessage;

/**
 * Classe de message de réponse du serveur au client à ses demandes de keepalive via {@link ClientPulseMessage}
 *
 */
public class ReplyClientPulseMessage extends ServerToClientMessage {

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.receiveServerHeartBeat();
    }
}