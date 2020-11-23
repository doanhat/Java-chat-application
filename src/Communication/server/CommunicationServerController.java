package Communication.server;

import Communication.common.CommunicationController;
import Communication.common.NetworkWriter;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.UserDisconnectedMessage;
import common.interfaces.client.ICommunicationToData;
import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommunicationServerController extends CommunicationController {

    private final NetworkServer server;
    private IServerCommunicationToData dataServer;

    public CommunicationServerController() {
        super();

        server = new NetworkServer(this);
    }

    /* -------------------------------------------- Setup interfaces -------------------------------------------------*/

    /**
     * Installer les interfaces de Data Serveur
     * @param dataServerIface
     */
    public void setupInterfaces(IServerCommunicationToData dataServerIface) {
        this.dataServer = dataServerIface;
    }

    /* ---------------------------------------------- Core functionalities -------------------------------------------*/

    /**
     * Démarrer Communication Server
     */
    public void start() {
        server.start();
    }

    /**
     * Arreter Communication Server
     */
    public void stop() {
        taskManager.shutdown();

        try {
            server.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.err.println("Communication Server s'arrete!");
    }

    /**
     * Deconnecter un client
     * @param userID ID du client a deconnecter
     */
    @Override
    public void disconnect(UUID userID) {
        NetworkUser user = server.directory().getConnection(userID);

        if (user != null)
        {
            UserLite userInfo = user.getUserInfo();

            if (server.directory().deregisterClient(userID)) {
                System.err.println("Serveur déconnecte client " + userID);

                sendBroadcast(new UserDisconnectedMessage(userInfo), userInfo);
            }
            else {
                System.err.println("Serveur echoue de déconnecte client " + userID);
            }
        }
        else {
            System.err.println("Serveur echoue de déconnecte client " + userID + ", user n'est pas registré");
        }
    }

    /**
     * Envoyer un message réseau à un client
     * @param receiverID ID du client
     * @param message message réseau
     */
    public void sendMessage(UUID receiverID, NetworkMessage message) {
        NetworkUser receiver = server.directory().getConnection(receiverID);

        if (receiver != null) {
            server.sendMessage(receiver.preparePacket(message));
        }
        else {
            System.err.println("CommunicationServerController.sendMessage: receiver est null");
        }
    }

    /**
     * Envoyer un pacquet réseau
     * @param packet
     */
    public void sendMessage(NetworkWriter.DeliveryPacket packet) {
        server.sendMessage(packet);
    }

    /**
     * Liste des clients en-ligne
     * @return
     */
    public List<UserLite> onlineUsers() {
        return server.directory().onlineUsers();
    }

    /**
     * Broadcast messages aux tous les clients en-ligne
     * @param message
     * @param excludedUser utilisateur exclu qui ne recevra le message pour éviter renvoie de message à l'émetteur
     */
    public void sendBroadcast(NetworkMessage message, UserLite excludedUser) {
        List<UserLite> users = server.directory().onlineUsers();

        for(NetworkUser usr : server.directory().getConnections(users)) {
            if (usr.uuid() != excludedUser.getId()) {
                server.sendMessage(usr.preparePacket(message));
            }
        }
    }

    /**
     * Multicast messages aux tous les clients
     * @param receivers liste de recepteurs
     * @param message message réseau
     * @param excludedUser utilisateur exclu qui ne recevra le message pour éviter renvoie de message à l'émetteur
     */
    public void sendMulticast(List<UserLite> receivers, NetworkMessage message, UserLite excludedUser) {
        for(NetworkUser usr : server.directory().getConnections(receivers)) {
            if (usr.uuid() != excludedUser.getId()) {
                server.sendMessage(usr.preparePacket(message));
            }
        }
    }

    /* -------------------------------------- Connection Request handling --------------------------------------------*/

    /**
     * Liste des channels visible à un utilisateur
     * @param user
     * @return
     */
    public List<Channel> getUserChannels(UserLite user) {
        if (dataServer == null)
        {
            System.err.println("getUserChannels: Data Iface est null");
            return new ArrayList<>();
        }

        return dataServer.getVisibleChannels(user);
    }

    /**
     * Cherche un channel selon son ID
     * @param channelID UUID du channel
     * @return
     */
    public Channel getChannel(UUID channelID) {
        if (dataServer == null)
        {
            System.err.println("getChannel: Data Iface est null");
            return null;
        }

        // TODO INTEGRATION request Data to add a Channel getChannel(UUID channelID) methode
        return null;  // dataServer.getChannel(channelID);
    }

    /* -------------------------------------- Channel action Request handling ----------------------------------------*/

    /**
     * Demande Data server à ajouter un nouveau channel
     * @param channel
     * @param proprietary
     * @param publicChannel
     * @param requester
     * @return
     */
    public Channel requestCreateChannel(Channel channel,
                                        boolean proprietary,
                                        boolean publicChannel,
                                        UserLite requester) {
        if (dataServer == null)
        {
            System.err.println("requestCreateChannel: Data Iface est null");
            return null;
        }

        // TODO INTEGRATION request Data to fix return type to Channel in order to return only created channel or null
        //  to avoid exposing data and decrease run time complexity
        List<Channel> allChannels = dataServer.requestChannelCreation(channel, proprietary, publicChannel, requester);

        if (allChannels != null) {
            for (Channel c: allChannels) {
                if (c.getId() == channel.getId()) {
                    return c;
                }
            }
        }

        return null;
    }

    /**
     * Demande Data server à supprimer un channel
     * @param channel
     * @param requester
     * @return
     */
    public boolean requestDeleteChannel(Channel channel, UserLite requester) {
        if (dataServer == null)
        {
            System.err.println("requestDeleteChannel: Data Iface est null");
            return false;
        }

        // TODO INTEGRATION request Data to fix return type to Channel in order to return only boolean
        //  to avoid exposing data and decrease run time complexity
        List<Channel> allChannels = dataServer.requestChannelRemoval(channel, requester);

        if (allChannels != null) {
            for (Channel c: allChannels) {
                if (c.getId() == channel.getId()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Demande Data server à rejoindre un utilisateur à un channel
     * @param channel
     * @param user
     * @return
     */
    public boolean requestJoinChannel(Channel channel, UserLite user){
        if (dataServer == null)
        {
            System.err.println("requestJoinSharedChannel: Data Iface est null");
            return false;
        }

        // TODO INTEGRATION verify with Data what are the differences between requestAddUser and joinChannel
        // TODO INTEGRATION request Data return a boolean in requestAddUser and joinChannel for confirmation
        dataServer.joinChannel(channel, user);

        return true;
    }

    /**
     * Demande Data server à rejoindre un utilisateur à un channel proprietaire
     * @param channel
     * @param user
     * @return
     */
    public List<Message> requestJoinOwnedChannel(Channel channel, UserLite user){
        if (dataServer == null)
        {
            System.err.println("requestJoinOwnedChannel: Data Iface est null");
            return null;
        }
        // TODO: verify return type with data
        // TODO V2
        //return dataServer.joinChannel(channel, user);

        return null;
    }

    /* ----------------------------------------- Chat action handling ------------------------------------------------*/

    /**
     * Demande Data server à enregistrer un message
     * @param msg
     * @param channel
     * @param response
     */
    public void saveMessage (Message msg, Channel channel, Message response) {
        if (dataServer == null)
        {
            System.err.println("saveMessage: Data Iface est null");
            return;
        }

        dataServer.saveMessageIntoHistory(channel, msg, response);
    }
}
