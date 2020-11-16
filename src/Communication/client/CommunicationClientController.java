package Communication.client;

import Communication.common.CommunicationController;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.UserConnectionMessage;
import common.interfaces.client.ICommunicationToData;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.UUID;

public class CommunicationClientController extends CommunicationController {

    private final NetworkClient client;
    private ICommunicationToData dataClient;

    public CommunicationClientController() {
        super();

        client = new NetworkClient(this);
    }

    public void start(String ip, int port, UserLite user) {
        try {
            client.connect(ip, port);
            client.sendMessage(new UserConnectionMessage(user));

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

    public void setICommunicationData(ICommunicationToData iCommunicationDataImpl) {
        dataClient = iCommunicationDataImpl;
    }

    public void sendMessage(NetworkMessage message) {
        client.sendMessage(message);
    }

    public void notifyUserConnected(UserLite newUser) {
        // TODO verify ICommunicationToData User interfaces
        //dataClient.newConnectionUser(newUser);
    }

    public void notifyUserDisconnected(UserLite user) {
        // TODO Change ICommunicationToData User to Userlite interfaces
        //dataClient.disconnectUser(user);
    }

    public void notifyVisibleChannel(Channel channel) {
        dataClient.addVisibleChannel(channel);
    }

    @Override
    protected void disconnect(UUID user) {
        System.out.println("A IHM Main : je suis plus co");
        // TODO ICommunicationToIHMMain
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
