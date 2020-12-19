package Communication.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import Communication.common.*;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.channel_access.propietary_channels.TellOwnerUserInvitedMessage;
import Communication.messages.server_to_client.channel_modification.NewInvisibleChannelsMessage;
import Communication.messages.server_to_client.chat_action.ReceiveChatMessage;
import Communication.messages.server_to_client.connection.UserDisconnectedMessage;
import Communication.messages.server_to_client.channel_access.UserLeftChannelMessage;

import Communication.messages.server_to_client.channel_access.ValideUserLeftMessage;
import common.interfaces.server.IServerCommunicationToData;
import common.interfaces.server.IServerDataToCommunication;
import common.shared_data.*;

/**
 * Classe principale de gestion des communications côté serveur. Cette classe implemente le design patern singleton
 *
 */
public class CommunicationServerController extends CommunicationController {

	private final NetworkServer server;
	private IServerCommunicationToData dataServer;
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private  final IServerDataToCommunication commInterface;

	public CommunicationServerController() {
		super();
		server = new NetworkServer(this);
		commInterface = new CommunicationServerInterface(this);
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
		taskManager = new TaskManager();
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

	public IServerDataToCommunication getDataToCommunication() {
		return commInterface;
	}

	
	public IServerCommunicationToData getDataServer() {
		return dataServer;
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

				List<Channel> userAuthorizedChannels = dataServer.getVisibleChannels(userInfo);

				for (Channel channel: userAuthorizedChannels) {
					leaveChannel(channel.getId(), userInfo);
				}

				List<Channel> userProprietaryChannel = dataServer.disconnectOwnedChannel(userInfo);

				if (userProprietaryChannel != null && !userProprietaryChannel.isEmpty()) {
					List<UUID> channelsID = new ArrayList<>();

					for (Channel channel: userProprietaryChannel) {
						channelsID.add(channel.getId());
					}

					// broadcast invisible channel
					sendBroadcast(new NewInvisibleChannelsMessage(channelsID), userInfo);
				}
			}
			else {
				logger.log(Level.SEVERE, "Serveur à echoué à déconnecter le client");
			}
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
			logger.log(Level.WARNING, "Serveur envoie message client deconnecte " + receiverID);
		}
	}

	/**
	 * Envoyer un pacquet réseau
	 * @param packet paquet réseau
	 */
	public void sendMessage(NetworkWriter.DeliveryPacket packet) {
		server.sendMessage(packet);
	}

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
			if (excludedUser != null){
				if (!usr.uuid().equals(excludedUser.getId())) {
					server.sendMessage(usr.preparePacket(message));
				}
			}
			else {
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

	/**
	 * Multicast messages aux tous les clients avec user exclut (principalement l'émetteur)
	 * @param receivers liste de recepteurs
	 * @param message message réseau
	 * @param excluded client exclut
	 */
	public void sendMulticast(List<UserLite> receivers, NetworkMessage message, UserLite excluded) {
		sendMulticast(receivers.stream().filter(
				userLite -> !userLite.getId().equals(excluded.getId())).collect(Collectors.toList()),
				message);
	}

	/* -------------------------------------- Connection Request handling --------------------------------------------*/

	/**
	 * Liste des channels visible à un utilisateur
	 * @param user utilisateur dont on cherche à obtenir la liste des cannaux dans lequel il est présent
	 * @return Liste des cannaux auquel l'utilisateur à accès
	 */
	public List<Channel> getUserChannels(UserLite user) {
		return dataServer.getVisibleChannels(user);
	}

	/**
	 * Cherche un channel selon son ID
	 * @param channelID UUID du channel
	 * @return channel corresponde au ID
	 */
	public Channel getChannel(UUID channelID) {
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
		return dataServer.requestChannelRemoval(channel.getId(), requester);
	}

	/**
	 * Demande Data server à rejoindre un utilisateur à un channel
	 * @param channel cannal que l'utilisateur demande à rejoindre
	 * @param user utilisateur qui demande a rejoindre
	 */
	public void requestJoinChannel(Channel channel, UserLite user){
		dataServer.joinChannel(channel.getId(), user);
	}

	/**
	 * Demande de quitter d'un channel
	 * @param channelID ID du channel
	 * @param userLite demandeur
	 */
	public void leaveChannel(UUID channelID, UserLite userLite) {
		Channel channel = dataServer.getChannel(channelID);

		// since owner and server sync content of channel
		dataServer.leaveChannel(channel.getId(), userLite);

		sendMessage(userLite.getId(), new ValideUserLeftMessage(channelID, userLite.getId(), channel.getCreator().getId(), channel.getType() == ChannelType.OWNED));
		sendMulticast(channel.getJoinedPersons(), new UserLeftChannelMessage(channel.getId(), userLite));

		if (channel.getType() == ChannelType.OWNED && channel.getCreator().getId().equals(userLite.getId())) {
			// when owner leaves, channel become invisible
			if (channel.getVisibility() == Visibility.PUBLIC) {
				sendBroadcast(new NewInvisibleChannelsMessage(channel.getId()), userLite);
			}
			else {
				sendMulticast(channel.getAuthorizedPersons(), new NewInvisibleChannelsMessage(channel.getId()), userLite);
			}
		}
	}

	/**
	 * Demande d'ajouter un utilisateur à un channel
	 * @param guest invitateur
	 * @param channel channel
	 */
	public void requestInviteUserToChannel(UserLite guest, Channel channel) {
		dataServer.requestAddUser(channel, guest);

		if (channel.getType() == ChannelType.OWNED) {
			// Tell owner uer invited
			sendMessage(channel.getCreator().getId(), new TellOwnerUserInvitedMessage(guest, channel.getId()));
		}
	}

	public List<UserLite> channelConnectedUsers(Channel channel) {
		List<UserLite> activeUsers = new ArrayList<>();

		for (UserLite usr: channel.getJoinedPersons()) {
			NetworkUser user = server.directory().getConnection(usr.getId());

			if (user != null) {
				activeUsers.add(usr);
			}
		}

		return activeUsers;
	}

	/* ----------------------------------------- Chat action handling ------------------------------------------------*/


	public void handleChat(ChannelOperation operation, InfoPackage infoPackage) {
		Channel channel = getChannel(infoPackage.channelID);

		logger.log(Level.INFO, "Chat action: " + operation + " on channel " + infoPackage.channelID);

		// Tell data server to save message for both shared and proprietary channels, in order to update active Channel on server
		switch (operation) {
			case SEND_MESSAGE:
				dataServer.saveMessageIntoHistory(channel, infoPackage.message, infoPackage.messageResponseTo);
				break;
			case EDIT_MESSAGE:
				dataServer.editMessage(channel, infoPackage.editedMessage);
				break;
			case LIKE_MESSAGE:
				dataServer.saveLikeIntoHistory(channel, infoPackage.message, infoPackage.user);
				break;
			case DELETE_MESSAGE:
				dataServer.saveRemovalMessageIntoHistory(channel,
														 infoPackage.message,
														 infoPackage.user.getId().equals(infoPackage.message.getAuthor().getId()));
				break;
			case EDIT_NICKNAME:
				dataServer.updateNickname(channel, infoPackage.user, infoPackage.nickname);
				break;
			case ADD_ADMIN:
				dataServer.saveNewAdminIntoHistory(channel, infoPackage.user);
				break;
			case REMOVE_ADMIN:
				// TODO INTEGRATION V3 Tell data server to remove admin
				break;
			default:
				logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
		}

		sendMulticast(channel.getJoinedPersons(), new ReceiveChatMessage(operation, infoPackage));
	}
}
