package Communication.messages.abstracts;

import Communication.common.CommunicationController;

import java.io.Serializable;

/**
 * Classe abstraite des messages transitant sur le réseau.
 */
public abstract class NetworkMessage implements Serializable {

    public abstract void handle(CommunicationController commController);

    /**
     * Class embarqué pour associe handle fonction avec un Runnable
     */
    public static class Handler implements Runnable {
        private final NetworkMessage          message;
        private final CommunicationController commController;

        public Handler(NetworkMessage message, CommunicationController commController) {
            this.message        = message;
            this.commController = commController;
        }

        @Override
        public void run() {
            message.handle(commController);
        }
    }
}


