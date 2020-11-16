package Communication.server;

import Communication.common.CommunicationController;
import Communication.messages.abstracts.NetworkMessage;
import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class CommunicationServerController extends CommunicationController {

    private final NetworkServer server;
    private final IServerCommunicationToData dataServer;

    public CommunicationServerController(IServerCommunicationToData dataIface) {
        super();

        server = new NetworkServer(this);
        dataServer = dataIface;
    }

    public void start() {
        server.start();
    }

    public void stop() {
        taskManager.shutdown();

        try {
            server.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(UUID receiver, NetworkMessage message) {
        server.sendMessage(server.directory().getConnection(receiver).preparePacket(message));
    }

    public List<Channel> getUserChannels(UserLite user) {
        return dataServer.requestUserChannelList(user);
    }

    public List<UserLite> onlineUsers() {
        return server.directory().onlineUsers();
    }

    public Channel requestCreateChannel(Channel channel, boolean proprietary, boolean publicChannel, UserLite requester) {
        // TODO request Data to add missing interface

        return null;
    }
}
