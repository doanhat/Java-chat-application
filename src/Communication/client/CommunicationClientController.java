package Communication.client;

import Communication.common.CommunicationController;
import common.interfaces.client.ICommunicationToData;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.io.IOException;

public class CommunicationClientController extends CommunicationController {

    private final NetworkClient client;
    private final ICommunicationToData dataClient;

    public CommunicationClientController(ICommunicationToData dataIface) {
        super();

        client = new NetworkClient(this);
        dataClient = dataIface;
    }

    public void start(String ip, int port) {
        try {
            client.connect(ip, port);
            System.out.println("Connexion au server...");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        taskManager.shutdown();

        try {
            client.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyUserConnected(UserLite newUser) {
        //dataClient.newConnectionUser();
    }

    public void notifyVisibleChannel(Channel channel) {
        dataClient.addVisibleChannel(channel);
    }
}
