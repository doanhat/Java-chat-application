package Communication.server;

import Communication.common.CommunicationController;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.UserDisconnectedMessage;
import common.interfaces.client.ICommunicationToData;
import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class CommunicationServerController extends CommunicationController {

    private final NetworkServer server;
    private IServerCommunicationToData dataServer;
    private ICommunicationToData data;

    public CommunicationServerController() {
        super();

        server = new NetworkServer(this);
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

    public void setupInterfaces(IServerCommunicationToData dataServerIface, ICommunicationToData dataIface) {
        this.dataServer = dataServerIface;
        this.data = dataIface;
    }

    public void sendMessage(UUID receiver, NetworkMessage message) {
        server.sendMessage(server.directory().getConnection(receiver).preparePacket(message));
    }

    public void setupInterfaces(IServerCommunicationToData dataIface) {
        this.dataServer = dataIface;
    }

    public List<Channel> getUserChannels(UserLite user) {
        return dataServer.getVisibleChannels(user);
    }

    public List<UserLite> onlineUsers() {
        return server.directory().onlineUsers();
    }

    public Channel requestCreateChannel(Channel channel, boolean proprietary, boolean publicChannel, UserLite requester) {
        // TODO request Data to add missing interface

        return null;
    }

    /**
     * Broadcast messages aux tous les clients enligne
     * @param message
     */
    public void sendBroadcast(NetworkMessage message) {
        for(NetworkUser usr : server.directory().getAllConnections()){
            server.sendMessage(usr.preparePacket(message));
        }
    }

    public void requestJoinSharedChannel(Channel channel, UserLite user){
        dataServer.requestAddUser(channel, user);
    }

    public List<Message> requestJoinOwnedChannel(Channel channel, UserLite user){
        // TODO: verify return type with data
        //return dataServer.joinChannel(channel, user);

        return null;
    }

    @Override
    protected void disconnect(UUID user) {
        UserLite userlite = server.directory().getConnection(user).getUserInfo();

        server.directory().deregisterClient(user);
        sendBroadcast(new UserDisconnectedMessage(userlite));
    }

    public void requestSendMessage (Message msg, Channel channel, Message response) {
        data.saveMessageIntoHistory(msg, channel.getId(), response);
    }
}
