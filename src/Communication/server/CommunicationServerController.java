package Communication.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import Communication.common.CommunicationController;
import Communication.common.NetworkWriter;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.channel_modification.NewInvisibleChannelsMessage;
import Communication.messages.server_to_client.connection.UserDisconnectedMessage;
import Communication.messages.server_to_client.channel_access.UserLeftChannelMessage;

import Communication.messages.server_to_client.channel_access.ValideUserLeftMessage;
import common.interfaces.server.IServerCommunicationToData;
import common.sharedData.*;

/**
 * Classe principale de gestion des communications côté serveur. Cette classe implemente le design patern singleton
 *
 */
public class CommunicationServerController extends CommunicationController {

	private final NetworkServer server;
	private IServerCommunicationToData dataServer;
	private final Logger logger = Logger.getLogger(this.getClass().getName());

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
				logger.log(Level.INFO, "Serveur déconnecte client {}" , userID);
				sendBroadcast(new UserDisconnectedMessage(userInfo), userInfo);


				List<Channel> userProprietaryChannel = dataServer.disconnectOwnedChannel(userInfo);

				if (userProprietaryChannel != null && !userProprietaryChannel.isEmpty()) {
					List<UUID> channelsID = new ArrayList<>();

					for (Channel channel: userProprietaryChannel) {
						channelsID.add(channel.getId());
					}

					// TODO INTEGRATION V2: review if it a broadcast or multicast under some specific rules
					// broadcast invisible channel
					sendBroadcast(new NewInvisibleChannelsMessage(channelsID), userInfo);
				}
			}
			else {
				logger.log(Level.SEVERE, "Serveur à echoué à déconnecter le client {}" , userID);
			}
		}
		else {
			logger.log(Level.WARNING, "le serveur à echoué à déconnecter le client {}, cet user n'est pas enregistré", userID);
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
			throw new IllegalArgumentException("CommunicationServerController.sendMessage: receiver est null");
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
	 */
	public void sendMulticast(List<UserLite> receivers, NetworkMessage message) {
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
			logger.log(Level.SEVERE, "getUserChannels: Data Iface est null");
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
			logger.log(Level.SEVERE,"getChannel: Data Iface est null");
			return null;
		}

		return dataServer.getChannel(channelID);
	}

	/* -------------------------------------- Channel action Request handling ----------------------------------------*/

	/**
	 * Demande Data server à ajouter un nouveau channel
	 * @param channel canal à ajouter
	 * @param isShared true si le canal est de type partagé
	 * @param isPublic true si le canal est public
	 * @param requester Utilisateur ayant demandé la création du canal
	 * @return Channel si le canal est autorisé à la création, null si c'est faux
	 */
	public Channel requestCreateChannel(Channel channel, boolean isShared, boolean isPublic, UserLite requester) {
		if (dataServer == null)
		{
			logger.log(Level.SEVERE, "requestCreateChannel: Data Iface est null");
			return null;
		}

		return dataServer.requestChannelCreation(channel, isShared, isPublic, requester);
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
			logger.log(Level.SEVERE, "requestDeleteChannel: Data Iface est null");
			return false;
		}

		return dataServer.requestChannelRemoval(channel.getId(), requester);
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
			logger.log(Level.SEVERE, "requestJoinSharedChannel: Data Iface est null");
			return false;
		}

		// TODO INTEGRATION V2 verify with Data what are the differences between requestAddUser and joinChannel
		dataServer.joinChannel(channel.getId(), user);

		return true;
	}

	public void leaveChannel(UUID channelID, UserLite userLite) {
		if (dataServer == null)
		{
			throw new NullPointerException("Data Interface est nulle");
		}

		Channel channel = dataServer.getChannel(channelID);

		// TODO INTEGRATION V2: ask data to implement leaveChannel() to remove user from shared and from proprietary channel,
		// since owner and server sync content of channel
		dataServer.leaveChannel(channel.getId(), userLite);

		sendMessage(userLite.getId(), new ValideUserLeftMessage(channelID, (channel.getVisibility() == Visibility.PUBLIC)));
		sendMulticast(channel.getAcceptedPersons(), new UserLeftChannelMessage(channel.getId(), userLite));
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
			logger.log(Level.SEVERE, "saveMessage: Data Iface est null");
			return;
		}

		dataServer.saveMessageIntoHistory(channel, msg, response);
	}

    /* ----------------------------------------- Chat action handling ------------------------------------------------*/

    public void sendInvite(UUID senderID, UUID receiverID, Message mess ) {
        UserLite receiver = server.directory().getConnection(receiverID).getUserInfo();
        UserLite sender = server.directory().getConnection(senderID).getUserInfo();

        // FIXME
        //dataServer.sendChannelInvitation(sender, receiver, mess);
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

		logger.log(Level.SEVERE, "new admin " + user.getNickName() + " added to channel " + channel.getId());

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

    public void requestAddUserToChannel(UserLite guest, Channel channel) {
		if (dataServer == null)
		{
			logger.log(Level.SEVERE, "requestAddUserToChannel: Data Iface est null");
		}

		// TODO INTEGRATION V2 verify with Data what are the differences between requestAddUser and joinChannel
        this.dataServer.requestAddUser(channel, guest);
    }

    public List<Message> getHistoryMessage(Channel channel, UserLite user){
        if(dataServer == null){
            System.err.println("saveNewAdmin: Data Iface est null");
            return null;
        }
        if (dataServer.checkAuthorization(channel, user)){
            List<Message> history = dataServer.getHistory(channel);
            if(channel.getType() == ChannelType.OWNED){
                //TODO


            }else{
                return dataServer.getHistory(channel);
            }
        }
        return null;
    }
    
    /**
     * Demande a dataserver à changer le nickname d'un utilisateur dans un canal
     * @param user utilisateur demandant le changement
     * @param channel canal dans lequel changer le nickname de l'utilisateur
     * @param newNickname nouveau nom d'utilisateur demandé
     * @throws NullPointerException Si l'interface de dataServer n'est pas acccessible.
     */
    public void requestNicknameChange(UserLite user, Channel channel, String newNickname){
    	 if (dataServer == null)
         {
           throw new NullPointerException("Data Interface est nulle");
         }
    	 else {
        	dataServer.updateNickname(channel, user, newNickname); 
         }
    }
}
