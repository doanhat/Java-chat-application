package Communication.client;

import Communication.common.Parameters;
import Communication.messages.client_to_server.generic.ClientPulseMessage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class HeartBeat {

    private final CommunicationClientController commController;
    private Timer timer;
    private boolean serverAlive = false;
    private UUID userID;

    public HeartBeat(CommunicationClientController commController) {
        this.commController = commController;
    }

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

    public void restart() {
        stop();
        start(userID);
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }

    public void handleServerReply() {
        System.err.println("HeartBeat recoit réponse du serveur");
        serverAlive = true;
    }
}

