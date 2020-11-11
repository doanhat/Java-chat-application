package Communication.client;

import Communication.common.CommunicationController;
import Communication.common.NetworkMessage;
import Communication.common.NetworkMessageTest;

import java.io.IOException;

public class CommunicationClientController extends CommunicationController {

    private NetworkClient client;

    public CommunicationClientController() {
        client = new NetworkClient(this);
    }

    public void start(String ip, int port) {
        try {
            client.connexion(ip, port);
            System.out.println("Connexion au server...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
