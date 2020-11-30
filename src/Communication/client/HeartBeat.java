package Communication.client;

import Communication.common.Parameters;
import Communication.messages.client_to_server.ClientPulseMessage;
import common.sharedData.UserLite;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Classe servant à gérer un keepalive entre le client et le serveur et d'informer le client en cas de rupture de communication avec le serveur
 *
 */
public class HeartBeat {

    private final CommunicationClientController commController;
    private Timer timer;
    private boolean serverAlive = false;
    private UUID userID;

    public HeartBeat(CommunicationClientController commController) {
        this.commController = commController;
    }
    /**
     * Démarrer le Hearthbeat en tant que l'utilisateur userID
     * @param userID identifiant unique permettant d'être certain de l'utilisateur effectuant la validation keepalive
     */
    public void start(UUID userID) {
        this.timer = new Timer();
        this.serverAlive = true;
        this.userID = userID;

        this.timer.schedule(new TimerTask(){
            @Override
            public void run() {
                if (serverAlive) {
                    commController.sendMessage(new ClientPulseMessage(userID));
                }
                else {
                    System.err.println("Server n'a pas répondu");
                    // TODO Inform controller of disconnection
                    commController.stop();
                }

                // reset to false and wait for server reply
                serverAlive = false;
            }
        }, Parameters.PULSE_INTERVAL, Parameters.PULSE_INTERVAL);
    }

    /**
     * Redémare le fonctionnement du heathbeat en l'arrêtant puis le redémarant en utilisant les fonctions {@link stop()} et {@link start(userID)}
     */
    public void restart() {
        stop();
        start(userID);
    }
    
    /**
     * Arrête le keepalive en utilisant les fonctions {@link Timer.cancel()} et {@link Timer.purge()}
     */
    public void stop() {
        timer.cancel();
        timer.purge();
    }

    /**
     * Fonction lancée à chaque réception de la réponse du serveur
     */
    public void handleServerReply() {
        System.err.println("HeartBeat recoit réponse du serveur");
        serverAlive = true;
    }
}

