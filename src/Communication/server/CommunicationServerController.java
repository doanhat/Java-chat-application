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

    public void sendMessage(UUID receiverID, NetworkMessage message) {
        NetworkUser receiver = server.directory().getConnection(receiverID);

        if (receiver != null) {
            server.sendMessage(receiver.preparePacket(message));
        }
    }

    public void setupInterfaces(IServerCommunicationToData dataIface) {
        this.dataServer = dataIface;
    }

    public List<Channel> getUserChannels(UserLite user) {
        return dataServer.getVisibleChannels(user);
    }

    public Channel getChannel(UUID channelID) {
        // TODO INTEGRATION request Data to add a Channel getChannel(UUID channelID) methode
        return null;  // dataServer.getChannel(channelID);
    }

    public List<UserLite> onlineUsers() {
        return server.directory().onlineUsers();
    }

    public Channel requestCreateChannel(Channel channel,
                                        boolean proprietary,
                                        boolean publicChannel,
                                        UserLite requester) {
        // TODO INTEGRATION request Data to fix return type to Channel
        // return requestChannelCreation(channel, proprietary, publicChannel, requester);
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

    public boolean requestJoinChannel(Channel channel, UserLite user){
        if (dataServer == null)
        {
            System.err.println("requestJoinSharedChannel: Data Iface est null");
            return false;
        }

        // TODO INTEGRATION verify with Data what are the differences between requestAddUser and joinChannel
        // TODO INTEGRATION verify with Data return a boolean in requestAddUser and joinChannel for confirmation
        dataServer.requestAddUser(channel, user);

        return true;
    }

    public List<Message> requestJoinOwnedChannel(Channel channel, UserLite user){
        // TODO: verify return type with data
        //return dataServer.joinChannel(channel, user);

        return null;
    }

    @Override
    public void disconnect(UUID user) {
        UserLite userlite = server.directory().getConnection(user).getUserInfo();

        server.directory().deregisterClient(user);
        sendBroadcast(new UserDisconnectedMessage(userlite));
    }

    public void saveMessage (Message msg, Channel channel, Message response) {
        data.saveMessageIntoHistory(msg, channel.getId(), response);
    }

    public void SendInvite(UUID senderID, UUID receiverID, Message mess ) {
        UserLite receiver = server.directory().getConnection(receiverID).getUserInfo();
        UserLite sender = server.directory().getConnection(senderID).getUserInfo();

        dataServer.sendChannelInvitation(sender, receiver, mess.getMessage());

    }
}
