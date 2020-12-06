package Communication.server;

import Communication.common.CommunicationController;
import Communication.common.NetworkWriter;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.UserDisconnectedMessage;
import Communication.messages.server_to_client.UserLeftChannelMessage;
import Communication.messages.server_to_client.ValideUserLeftMessage;
import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe principale de gestion des communications côté serveur. Cette classe implemente le design patern singleton
 *
 */
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
     * @param dataServerIface interface de dataserver
     */
    public void setIServerCommunicationToData(IServerCommunicationToData dataServerIface) {
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
     * Notifie DirectoryFacilitator l'impulse du client
     * @param userID ID de l'émetteur
     */
    public void receiveClientPulse(UUID userID) {
        server.directory().receivePulse(userID);
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
     * @param packet paquet réseau
     */
    public void sendMessage(NetworkWriter.DeliveryPacket packet) {
        server.sendMessage(packet);
    }

    // FIXME: remove excluded user from function parameters
    /**
     * Liste des clients en-ligne
     * @return Liste des client qui sont en ligne
     */
    public List<UserLite> onlineUsers() {
        return server.directory().onlineUsers();
    }

    /**
     * Broadcast messages aux tous les clients en-ligne
     * @param message message à envoyer à tous les client
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
            server.sendMessage(usr.preparePacket(message));
        }
    }

    /* -------------------------------------- Connection Request handling --------------------------------------------*/

    /**
     * Liste des channels visible à un utilisateur
     * @param user utilisateur dont on cherche à obtenir la liste des cannaux dans lequel il est présent
     * @return Liste des cannaux auquel l'utilisateur à accès
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

        return dataServer.getChannel(channelID);
    }

    /* -------------------------------------- Channel action Request handling ----------------------------------------*/

    /**
     * Demande Data server à ajouter un nouveau channel
     * @param channel canal à ajouter
     * @param proprietary true si le canal est de type propriétaire
     * @param publicChannel true si le canal est public false si il est privé
     * @param requester Utilisateur ayant demandé la création du canal
     * @return Channel si le canal est autorisé à la création, null si c'est faux
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
     * Demande Data server à supprimer un canal
     * @param channel canal à supprimer
     * @param requester personne qui demande la supression du canal
     * @return <code>true</code> si le canal à été correctement supprimé
     * 		   <code>false</code> sinon
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
     * @param channel cannal que l'utilisateur demande à rejoindre
     * @param user utilisateur qui demande a rejoindre
     * @return <code>true</code> si l'utilisateur à bien rejoint le channel
     *         <code>false</code> si il n'a pas pu le rejoindre 
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
     * Demande Data server à rejoindre un utilisateur à un canal proprietaire
     * @implNote Cette methode n'est pas encore implementée correctement
     * @param channel cannal à rejoindre
     * @param user utilisateur à rejoindre
     * @return Liste des messages du canal
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

    /**
     * Demande à Data server de retire un utilisateur d'un canal
     * @param channelID cannal à quitter
     * @param userLite utilisateur voulant partir
     */
    public void notifyLeaveChannel(UUID channelID, UserLite userLite) {
        if (dataServer == null)
        {
            System.err.println("requestJoinSharedChannel: Data Iface est null");
            return;
        }
        Channel ch = dataServer.getChannel(channelID);
        dataServer.leaveChannel(ch, userLite);

        sendMessage(userLite.getId(), new ValideUserLeftMessage(channelID));

        sendMulticast(ch.getAcceptedPersons(), new UserLeftChannelMessage(channelID, userLite), userLite);
    }

    /* ----------------------------------------- Chat action handling ------------------------------------------------*/

    /**
     * Demande Data server à enregistrer un message
     * @param msg message à enregistrer
     * @param channel canal du message
     * @param response message auquel le nouveau message est une réponse
     */
    public void saveMessage (Message msg, Channel channel, Message response) {
        if (dataServer == null)
        {
            System.err.println("saveMessage: Data Iface est null");
            return;
        }

        dataServer.saveMessageIntoHistory(channel, msg, response);
    }

    public void SendInvite(UUID senderID, UUID receiverID, Message mess ) {
        UserLite receiver = server.directory().getConnection(receiverID).getUserInfo();
        UserLite sender = server.directory().getConnection(senderID).getUserInfo();

        dataServer.sendChannelInvitation(sender, receiver, mess.getMessage());

    }

    /**
     * Methode qui signale a Data d'ajouter un nouvel admin sur un channel partage
     * @param user Utilisateur devenant admin
     * @param channel Channel ou l'utilisateur devient admin
     */
    public void saveNewAdmin(Channel channel, UserLite user) {
        if (dataServer == null)
        {
            System.err.println("saveNewAdmin: Data Iface est null");
            return;
        }

        System.err.println("new admin " + user.getNickName() + " added to channel " + channel.getId());
        dataServer.saveNewAdminIntoHistory(channel, user);
    }

    public void deleteMessage(Message message, Channel channel, Boolean deleteByCreator){
        if (dataServer == null)
        {
            System.err.println("saveNewAdmin: Data Iface est null");
            return;
        }

        System.err.println("Message " + message.getId() + " deleted on channel " + channel.getId());
        dataServer.saveRemovalMessageIntoHistory(channel, message, deleteByCreator);
    }

    public List<UserLite> getChannelConnectedUserList(UUID channelID){
        //TODO
        return null;
    }

    public void notifyInviteChannel(UserLite guest, Channel ch) {
        this.dataServer.requestAddUser(ch, guest);
    }
}
