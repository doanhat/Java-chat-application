package Communication.messages.abstracts;

import Communication.common.CommunicationController;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Classe abstraite des messages transitant sur le réseau.
 *
 */
public abstract class NetworkMessage implements Serializable {

    protected final transient Logger logger = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = -7165721578275332325L;

	public abstract void handle(CommunicationController commController);

    /**
     * Class embarqué pour associe handle fonction avec un Runnable
     */
    public static class Handler implements Runnable {
        private NetworkMessage message;
        private CommunicationController commController;

        public Handler(NetworkMessage message, CommunicationController commController) {
            this.message = message;
            this.commController = commController;
        }

        @Override
        public void run() {
            message.handle(commController);
        }
    }
}


