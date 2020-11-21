package Communication.client;

import Communication.common.CommunicationController;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.UserConnectionMessage;
import common.interfaces.client.ICommunicationToData;
import common.interfaces.client.ICommunicationToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.UUID;

public class CommunicationClientController extends CommunicationController {

    private final NetworkClient client;
    private ICommunicationToData dataClient;
    private ICommunicationToIHMChannel channelClient;

    public CommunicationClientController() {
        super();

        client = new NetworkClient(this);
    }

    public boolean setupInterfaces(ICommunicationToData dataIface, ICommunicationToIHMChannel channelIface) {
        if (dataIface == null || channelIface == null) {
            return false;
        }

        setICommunicationData(dataIface);
        setICommunicationToIHMChannel(channelIface);

        return true;
    }

    public void setICommunicationData(ICommunicationToData dataIface) {
        dataClient = dataIface;
    }

    public void setICommunicationToIHMChannel(ICommunicationToIHMChannel channelIface) {
        channelClient = channelIface;
    }

    public void start(String ip, int port, UserLite user) {
        try {
            client.connect(ip, port);
            client.sendMessage(new UserConnectionMessage(user));

            System.out.println("Connexion au server...");
        }
        catch (IOException e) {
            disconnect(null);
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
        // TODO wait for ICommunicationToData to change User interfaces to UserLite
        //dataClient.newConnectionUser(newUser);
    }

    public void notifyUserDisconnected(UserLite user) {
        // TODO wait for ICommunicationToData to change User interfaces to UserLite
        //dataClient.disconnectUser(user);
    }

    public void notifyVisibleChannel(Channel channel) {
        dataClient.addVisibleChannel(channel);
    }

    @Override
    protected void disconnect(UUID user) {
        System.out.println("A IHM Main : je suis plus connecté");
        // TODO notify ICommunicationToIHMMain
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
