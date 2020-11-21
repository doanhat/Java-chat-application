package Communication.client;

import Communication.common.CommunicationController;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.UserConnectionMessage;
import common.interfaces.client.ICommunicationToData;

import common.interfaces.client.ICommunicationToIHMChannel;
import common.interfaces.client.ICommunicationToIHMMain;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.UUID;

public class CommunicationClientController extends CommunicationController {

    private final NetworkClient client;
    private ICommunicationToData dataClient;
    private ICommunicationToIHMMain mainClient;
    private ICommunicationToIHMChannel channelClient;

    public CommunicationClientController(ICommunicationToData dataIface) {
        super();

        client = new NetworkClient(this);
    }

    public boolean setupInterfaces(ICommunicationToData dataIface,
                                   ICommunicationToIHMMain mainIface,
                                   ICommunicationToIHMChannel channelIface) {
        if (dataIface == null || mainIface == null || channelIface == null) {
            return false;
        }

        setICommunicationData(dataIface);
        setICommunicationToIHMMain(mainIface);
        setICommunicationToIHMChannel(channelIface);

        return true;
    }

    public void setICommunicationData(ICommunicationToData dataIface) {
        dataClient = dataIface;
    }

    public void setICommunicationToIHMMain(ICommunicationToIHMMain mainIface) {
        mainClient = mainIface;
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

    public void notifyUserDisconnected(UserLite user) {
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
        System.out.println("A IHM Main : je suis plus connecté");
        // TODO notify ICommunicationToIHMMain

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
