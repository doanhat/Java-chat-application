package Communication.messages.abstracts;

import Communication.common.CommunicationController;
import Communication.server.CommunicationServerController;

import java.util.logging.Logger;

/**
 * Classe abstraite depuis laquelle sont étendu les messages devant aller des clients au serveur.
 * @see NetworkMessage
 *
 */
public abstract class ClientToServerMessage extends NetworkMessage {
    protected final transient Logger logger = Logger.getLogger(this.getClass().getName());
    
    @Override
    public void handle(CommunicationController communicationController) {
        handle((CommunicationServerController) communicationController);
    }

    protected abstract void handle(CommunicationServerController commClientController);
}
