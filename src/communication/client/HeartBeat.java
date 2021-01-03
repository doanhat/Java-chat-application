package communication.client;


import communication.common.Parameters;
import communication.messages.client_to_server.connection.ClientPulseMessage;
import communication.messages.client_to_server.connection.UserConnectionMessage;
import common.shared_data.UserLite;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Classe servant à gérer un keepalive entre le client et le serveur et d'informer le client en cas de rupture de communication avec le serveur
 */
public class HeartBeat {

    private final CommunicationClientController commController;
    private final Logger                        logger      = Logger.getLogger(this.getClass().getName());
    private       Timer                         timer;
    private       boolean                       serverAlive = false;

    public HeartBeat(CommunicationClientController commController) {
        this.commController = commController;
    }

    /**
     * Démarrer le Hearthbeat en tant que l'utilisateur userID
     *
     * @param localUser 'utilisateur effectuant la validation keepalive
     */
    public void start(UserLite localUser) {
        if (timer != null) {
            stop();
        }

        this.timer       = new Timer();
        this.serverAlive = true;

        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (serverAlive) {
                    commController.sendMessage(new ClientPulseMessage(localUser.getId()));
                }
                else {
                    logger.log(Level.WARNING, "Server n'a pas répondu, tenter à reconnecter...");

                    // Inform controller of disconnection
                    commController.mainHandler().notifyLostConnection();

                    // Polling to Server to try to reconnect
                    commController.sendMessage(new UserConnectionMessage(localUser));
                }

                // reset to false and wait for server reply
                serverAlive = false;
            }
        }, Parameters.PULSE_INTERVAL, Parameters.PULSE_INTERVAL);
    }

    /**
     * Arrête le keepalive
     */
    public void stop() {
        timer.cancel();
        timer.purge();
    }

    /**
     * Fonction lancée à chaque réception de la réponse du serveur
     */
    public void handleServerReply() {
        logger.log(Level.FINE, "HeartBeat recoit réponse du serveur");

        serverAlive = true;
    }
}

