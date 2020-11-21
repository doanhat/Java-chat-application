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
import java.util.List;
import java.util.UUID;

public class CommunicationClientController extends CommunicationController {

    private final NetworkClient client;
    private ICommunicationToData dataClient;
    private ICommunicationToIHMMain mainClient;
    private ICommunicationToIHMChannel channelClient;

    public CommunicationClientController() {
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

    public void notifyConnectionSuccess(List<UserLite> users, List<Channel> channels) {
        if (mainClient == null)
        {
            System.err.println("notifyConnectionSuccess: IHMMain Iface est null");
            return;
        }

        mainClient.connectionAccepted();
        mainClient.setConnectedUsers(users);

        for (Channel channel: channels) {
            notifyVisibleChannel(channel);
        }
    }

    public void notifyUserConnected(UserLite newUser) {
        if (mainClient == null)
        {
            System.err.println("notifyUserConnected: IHMMain Iface est null");
            return;
        }

        mainClient.addConnectedUser(newUser);
    }

    public void notifyUserDisconnected(UserLite user) {
        if (mainClient == null)
        {
            System.err.println("notifyUserDisconnected: IHMMain Iface est null");
            return;
        }

        mainClient.removeConnectedUser(user);
    }

    public void notifyChannelCreated(Channel channel) {
        if (mainClient == null)
        {
            System.err.println("notifyChannelCreated: IHMMain Iface est null");
            return;
        }

        mainClient.channelCreated(channel);

        // TODO INTEGRATION verify with Data if new created Channel is control by Data Client and fill missing sequence diagram
        //dataClient.addVisibleChannel(channel);
    }

    public void notifyVisibleChannel(Channel channel) {
        if (mainClient == null)
        {
            System.err.println("notifyVisibleChannel: IHMMain Iface est null");
            return;
        }

        // TODO INTEGRATION request data addVisibleChannel receive Channel as parameter
        //dataClient.addVisibleChannel(channel);
        // TODO INTEGRATION Verify workflow between Comm, Data, Main to avoid redundancy
        mainClient.channelAdded(channel);

        // TODO handle propriety Channel
    }

    public void notifyReceiveMessage (Message msg, UUID channelID, Message response) {
        if (dataClient == null)
        {
            System.err.println("notifyReceiveMessage: Data Iface est null");
            return;
        }

        dataClient.receiveMessage(msg, channelID, response);
    }

    @Override
    public void disconnect(UUID user) {
        System.err.println("A IHM Main : je suis plus connecté");

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
