package communication.messages.abstracts;

import communication.client.CommunicationClientController;
import communication.common.CommunicationController;

/**
 * Classe abstraite depuis laquelle sont étendu les messages devant aller du serveur aux clients.
 *
 * @see NetworkMessage
 */
public abstract class ServerToClientMessage extends NetworkMessage {
    @Override
    public void handle(CommunicationController communicationController) {
        handle((CommunicationClientController) communicationController);
    }

    protected abstract void handle(CommunicationClientController commClientController);
}
