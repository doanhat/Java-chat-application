package Communication.server;

import Communication.common.CommunicationController;

public class CommunicationServerController extends CommunicationController {

    private final NetworkServer server;

    public CommunicationServerController(){
        server = new NetworkServer(this, 8080);
    }

    public void start() {
        server.start();
    }

}
