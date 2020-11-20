package Communication.client;

import Communication.common.CommunicationController;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.UserConnectionMessage;
import common.interfaces.client.ICommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.UUID;

public class CommunicationClientController extends CommunicationController {

    private final NetworkClient client;
    private final ICommunicationToData dataClient;

    public CommunicationClientController(ICommunicationToData dataIface) {
        super();

        client = new NetworkClient(this);
        dataClient = dataIface;
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

    public void sendMessage(NetworkMessage message) {
        client.sendMessage(message);
    }

    public void notifyUserConnected(UserLite newUser) {
        // TODO verify ICommunicationToData User interfaces
        //dataClient.newConnectionUser(newUser);
    }

    public void notifyUserDisconnected(User user) {
        dataClient.disconnectUser(user);
    }

    public void notifyVisibleChannel(Channel channel) {
        dataClient.addVisibleChannel(channel);
    }

    public void notifyReceiveMessage (Message msg, Channel channel, Message response) {
        dataClient.receiveMessage(msg, channel, response);
    }

    @Override
    protected void disconnect(UUID user) {

    }
}
