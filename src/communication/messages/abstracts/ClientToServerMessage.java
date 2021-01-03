package communication.messages.abstracts;

import communication.common.CommunicationController;
import communication.server.CommunicationServerController;

/**
 * Classe abstraite depuis laquelle sont étendu les messages devant aller des clients au serveur.
 *
 * @see NetworkMessage
 */
public abstract class ClientToServerMessage extends NetworkMessage {

    @Override
    public void handle(CommunicationController communicationController) {
        handle((CommunicationServerController) communicationController);
    }

    protected abstract void handle(CommunicationServerController commClientController);
}
