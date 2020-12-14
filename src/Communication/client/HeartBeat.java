package Communication.client;

import java.util.Timer;
import java.util.TimerTask;

public class HeartBeat {

    private final CommunicationClientController commController;
    private Timer timer;
    private boolean serverAlive = false;

    public HeartBeat(CommunicationClientController commController) {
        this.commController = commController;
    }

    public void start() {
        timer = new Timer();
        serverAlive = true;

        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                if (serverAlive) {
                    // TODO Inform server that client is still alive by ClientPulseMessage
                    // commController.sendMessage();
                }
                else {
                    // TODO Inform controller of disconnection
                }

                // reset to false and wait for server reply
                serverAlive = false;
            }
        }, 1000);
    }

    public void restart() {
        stop();
        start();
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }

    public void handleServerReply() {
        serverAlive = true;
    }
}

