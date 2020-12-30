package Communication.server;

import Communication.common.*;
import Communication.common.info_packages.BanUserPackage;
import Communication.common.info_packages.ChatPackage;
import Communication.common.info_packages.InfoPackage;
import Communication.common.info_packages.UpdateChannelPackage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.channel_access.*;
import Communication.messages.server_to_client.channel_modification.NewInvisibleChannelsMessage;
import Communication.messages.server_to_client.channel_modification.NewUserAuthorizeChannelMessage;
import Communication.messages.server_to_client.channel_modification.NewVisibleChannelMessage;
import Communication.messages.server_to_client.channel_modification.SendHistoryMessage;
import Communication.messages.server_to_client.channel_operation.ReceiveChannelOperationMessage;
import Communication.messages.server_to_client.connection.UserDisconnectedMessage;
import common.interfaces.server.IServerCommunicationToData;
import common.interfaces.server.IServerDataToCommunication;
import common.shared_data.Channel;
import common.shared_data.ChannelType;
import common.shared_data.UserLite;
import common.shared_data.Visibility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Classe principale de gestion des communications côté serveur. Cette classe implemente le design patern singleton
 */
public class CommunicationServerController extends CommunicationController {

    private final NetworkServer              server;
    private final Logger                     logger = Logger.getLogger(this.getClass().getName());
    private final IServerDataToCommunication commInterface;
    private       IServerCommunicationToData dataServer;

    public CommunicationServerController() {
        super();
        server        = new NetworkServer(this);
        commInterface = new CommunicationServerInterface(this);
    }

    /* -------------------------------------------- Setup interfaces -------------------------------------------------*/

    /**
     * Installer les interfaces de Data Serveur
     *
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

    /**
     * Deconnecter un client
     *
     * @param userID ID du client a deconnecter
     */
    @Override
    public void disconnect(UUID userID) {
        NetworkUser user = server.directory().getConnection(userID);

        if (user != null) {
            UserLite userInfo = user.getUserInfo();

            if (server.directory().deregisterClient(userID)) {
                logger.log(Level.INFO, "Serveur déconnecte client {}", userID);

                sendBroadcast(new UserDisconnectedMessage(userInfo), userInfo);

                List<Channel> userAuthorizedChannels = dataServer.getVisibleChannels(userInfo);

                for (Channel channel : userAuthorizedChannels) {
                    leaveChannel(channel.getId(), userInfo);
                }

                List<Channel> userProprietaryChannel = dataServer.disconnectOwnedChannel(userInfo);

                if (userProprietaryChannel != null && !userProprietaryChannel.isEmpty()) {
                    List<UUID> channelsID = new ArrayList<>();

                    for (Channel channel : userProprietaryChannel) {
                        channelsID.add(channel.getId());
                    }

                    // broadcast channels proprietaires invisibles aux autres utilisateurs
                    sendBroadcast(new NewInvisibleChannelsMessage(channelsID, "Propriétaire a quitté du channel"),
                                  userInfo);
                }
            }
            else {
                logger.log(Level.SEVERE, "Serveur à echoué à déconnecter le client");
            }
        }
    }

    /**
     * Notifie DirectoryFacilitator l'impulse du client
     *
     * @param userID ID de l'émetteur
     */
    public void receiveClientPulse(UUID userID) {
        server.directory().receivePulse(userID);
    }

    /**
     * Envoyer un message réseau à un client
     *
     * @param receiverID ID du client
     * @param message    message réseau
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
     *
     * @param packet paquet réseau
     */
    public void sendMessage(NetworkWriter.DeliveryPacket packet) {
        server.sendMessage(packet);
    }

    /**
     * Liste des clients en-ligne
     *
     * @return Liste des client qui sont en ligne
     */
    public List<UserLite> onlineUsers() {
        return server.directory().onlineUsers();
    }

    /**
     * Broadcast messages aux tous les clients en-ligne
     *
     * @param message      message à envoyer à tous les client
     * @param excludedUser utilisateur exclu qui ne recevra le message pour éviter renvoie de message à l'émetteur
     */
    public void sendBroadcast(NetworkMessage message, UserLite excludedUser) {
        List<UserLite> users = server.directory().onlineUsers();

        for (NetworkUser usr : server.directory().getConnections(users)) {
            if (excludedUser != null) {
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
     *
     * @param receivers liste de recepteurs
     * @param message   message réseau
     */
    public void sendMulticast(List<UserLite> receivers, NetworkMessage message) {
        for (NetworkUser usr : server.directory().getConnections(receivers)) {
            server.sendMessage(usr.preparePacket(message));
        }
    }

    /**
     * Multicast messages aux tous les clients avec user exclut (principalement l'émetteur)
     *
     * @param receivers liste de recepteurs
     * @param message   message réseau
     * @param excluded  client exclut
     */
    public void sendMulticast(List<UserLite> receivers, NetworkMessage message, UserLite excluded) {
        sendMulticast(receivers.stream().filter(userLite -> !userLite.getId().equals(excluded.getId())).collect(Collectors.toList()),
                      message);
    }

    /* -------------------------------------- Connection Request handling --------------------------------------------*/

    /**
     * Liste des channels visible à un utilisateur
     *
     * @param user utilisateur dont on cherche à obtenir la liste des cannaux dans lequel il est présent
     * @return Liste des cannaux auquel l'utilisateur à accès
     */
    public List<Channel> getUserChannels(UserLite user) {
        return dataServer.getVisibleChannels(user);
    }

    /**
     * Cherche un channel selon son ID
     *
     * @param channelID UUID du channel
     * @return channel corresponde au ID
     */
    public Channel getChannel(UUID channelID) {
        return dataServer.getChannel(channelID);
    }

    /**
     * Passe avatar à Data Serveur
     *
     * @param user utilisateur associé avec avatar
     * @param encodedString avatar chiffré
     */
    public void setAvatar(UserLite user, String encodedString) {
        dataServer.saveAvatarToServer(user, encodedString);
    }

    /**
     * Recupère Chemin d'Avatar
     *
     * @param user utilisateur associé avec avatar
     */
    public String getAvatarPath(UserLite user) {
        return dataServer.getAvatarPath(user);
    }

    /* -------------------------------------- Channel action Request handling ----------------------------------------*/

    /**
     * Demande Data server à ajouter un nouveau channel
     *
     * @param channel   canal à ajouter
     * @param isShared  true si le canal est de type partagé
     * @param isPublic  true si le canal est public
     * @param requester Utilisateur ayant demandé la création du canal
     * @return Channel si le canal est autorisé à la création, null si c'est faux
     */
    public Channel requestCreateChannel(Channel channel, boolean isShared, boolean isPublic, UserLite requester) {
        return dataServer.requestChannelCreation(channel, isShared, isPublic, requester);
    }

    /**
     * Demande Data server à supprimer un canal
     *
     * @param channel   canal à supprimer
     * @param requester personne qui demande la supression du canal
     * @return <code>true</code> si le canal à été correctement supprimé
     * <code>false</code> sinon
     */
    public boolean requestDeleteChannel(Channel channel, UserLite requester) {
        return dataServer.requestChannelRemoval(channel.getId(), requester);
    }

    /**
     * Demande Data server à rejoindre un utilisateur à un channel
     *
     * @param channel cannal que l'utilisateur demande à rejoindre
     * @param user    utilisateur qui demande a rejoindre
     */
    public void requestJoinChannel(Channel channel, UserLite user) {
        if (dataServer.joinChannel(channel.getId(), user)) {
            // send Acceptation back to sender
            sendMessage(user.getId(),
                        new SendHistoryMessage(channel, channelConnectedUsers(channel)));

            // Notifie les utilisateurs connectes au channel qu'un nouveau utilisateur les rejoins
            sendMulticast(channel.getJoinedPersons(), new NewUserJoinChannelMessage(user, channel.getId()), user);
        }
    }

    /**
     * Demande de quitter d'un channel
     *
     * @param channelID ID du channel
     * @param userLite  demandeur
     */
    public void leaveChannel(UUID channelID, UserLite userLite) {
        Channel channel = dataServer.getChannel(channelID);

        // since owner and server sync content of channel
        dataServer.leaveChannel(channel.getId(), userLite);

        sendMessage(userLite.getId(),
                    new ValideUserLeftMessage(channelID,
                                              userLite.getId(),
                                              channel.getCreator().getId(),
                                              channel.getType() == ChannelType.OWNED));

        sendMulticast(channel.getJoinedPersons(), new UserLeftChannelMessage(channel.getId(), userLite));

        if (channel.getType() == ChannelType.OWNED && channel.getCreator().getId().equals(userLite.getId())) {
            // when owner leaves, channel become invisible
            if (channel.getVisibility() == Visibility.PUBLIC) {
                sendBroadcast(new NewInvisibleChannelsMessage(channel.getId(), "Proprietaire a quitté"),
                              userLite);
            }
            else {
                sendMulticast(channel.getAuthorizedPersons(),
                              new NewInvisibleChannelsMessage(channel.getId(), "Proprietaire a quitté"),
                              userLite);
            }
        }
    }

    public void changeChannelAccess(ChannelAccessRequest request, Channel channel, UserLite user) {
        switch (request) {
            case JOIN:
                requestJoinChannel(channel, user);
                break;
            case LEAVE:
                leaveChannel(channel.getId(), user);
                break;
            case INVITE:
                requestInviteUserToChannel(channel, user);
                break;
            case QUIT:
                quitChannel(channel, user, "Quittant du channel");
                break;
            default:
        }
    }

    /**
     * Demande de retirer un utilisateur d'un channel sur ça liste d'autorisation et sur la liste des utilisateurs connectées
     *
     * @param userLite utilisateur
     * @param channel  channel
     */
    public void quitChannel(Channel channel, UserLite userLite, String explanation) {
        dataServer.quitChannel(channel.getId(), userLite);
        dataServer.leaveChannel(channel.getId(), userLite);

        sendMessage(userLite.getId(),
                    new ValideUserQuitMessage(channel.getId(), userLite, explanation));

        sendMulticast(channel.getAuthorizedPersons(),
                      new UserQuitedChannelMessage(channel.getId(), userLite),
                      userLite);
    }

    /**
     * Demande d'ajouter un utilisateur à un channel
     *
     * @param guest   invitateur
     * @param channel channel
     */
    public void requestInviteUserToChannel(Channel channel, UserLite guest) {
        if (dataServer.requestAddUser(channel, guest)) {
            // send Invitation to guest
            sendMessage(guest.getId(), new NewVisibleChannelMessage(channel));

            // Notifie les utilisateurs connectes au channel qu'un nouveau utilisateur à été authorisé
            sendMulticast(channel.getJoinedPersons(),
                          new NewUserAuthorizeChannelMessage(guest, channel.getId()));
        }
    }

    public List<UserLite> channelConnectedUsers(Channel channel) {
        List<UserLite> activeUsers = new ArrayList<>();

        for (UserLite usr : channel.getJoinedPersons()) {
            NetworkUser user = server.directory().getConnection(usr.getId());

            if (user != null) {
                activeUsers.add(usr);
            }
        }

        return activeUsers;
    }

    /* ----------------------------------------- Chat action handling ------------------------------------------------*/

    public void handleChannelOperation(ChannelOperation operation, InfoPackage infoPackage) {
        Channel channel = getChannel(infoPackage.channelID);

        logger.log(Level.INFO, "Chat action: " + operation + " on channel " + infoPackage.channelID);

        if (channel == null || !dataServer.checkAuthorization(channel, infoPackage.user)) {
            logger.log(Level.WARNING, "Chat action: User n'est pas autorisé");

            return;
        }

        // Tell data server to save message for both shared and proprietary channels, in order to update active Channel on server
        switch (operation) {
            case SEND_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataServer.saveMessageIntoHistory(channel, castedPackage.message, castedPackage.messageResponseTo);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: SEND_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case EDIT_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataServer.editMessage(channel, castedPackage.editedMessage);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: EDIT_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case LIKE_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataServer.saveLikeIntoHistory(channel, castedPackage.message, castedPackage.user);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: LIKE_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case DELETE_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataServer.saveRemovalMessageIntoHistory(channel,
                                                             castedPackage.message,
                                                             castedPackage.user.getId()
                                                                               .equals(castedPackage.message.getAuthor()
                                                                                                            .getId()));
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: DELETE_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case EDIT_NICKNAME:
                dataServer.updateNickname(channel, infoPackage.user, infoPackage.nickname);
                break;
            case ADD_ADMIN:
                dataServer.saveNewAdminIntoHistory(channel, infoPackage.user);
                break;
            case REMOVE_ADMIN:
                dataServer.requestRemoveAdmin(channel.getId(), infoPackage.user.getId());
                break;
            case BAN_USER:
                if (infoPackage instanceof BanUserPackage) {
                    BanUserPackage castedPackage = (BanUserPackage) infoPackage;

                    dataServer.banUserFromChannel(castedPackage.getUserToBan(),
                                                  castedPackage.getEndDate(),
                                                  castedPackage.isPermanent(),
                                                  castedPackage.getExplanation(),
                                                  channel.getId());

                    // Force banned user to quit channel
                    quitChannel(channel, castedPackage.getUserToBan(), "Vous etes banni au channel");
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: BAN_USER contient mauvais BanUserPackage");
                }

                break;
            case UNBAN_USER:
                if (infoPackage instanceof BanUserPackage) {
                    BanUserPackage castedPackage = (BanUserPackage) infoPackage;

                    dataServer.cancelUsersBanFromChannel(channel, castedPackage.getUserToBan());

                    requestInviteUserToChannel(channel, castedPackage.getUserToBan());
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: UNBAN_USER contient mauvais BanUserPackage");
                }

                break;
            case UPDATE_CHANNEL:
                if (infoPackage instanceof UpdateChannelPackage) {
                    UpdateChannelPackage castedPackage = (UpdateChannelPackage) infoPackage;

                    updateChannel(channel, castedPackage);

                    return;
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: UPDATE_CHANNEL contient mauvais UpdateChannelPackage");
                }

                break;
            default:
                logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
        }

        sendMulticast(channel.getJoinedPersons(), new ReceiveChannelOperationMessage(operation, infoPackage));
    }

    private void updateChannel(Channel channel, UpdateChannelPackage packet) {
        Visibility oldVisibility = channel.getVisibility();

        dataServer.updateChannel(packet.channelID,
                                 packet.user.getId(),
                                 packet.name,
                                 packet.description,
                                 packet.visibility);

        if (packet.visibility != null && oldVisibility != packet.visibility) {
            // informe les utilisateurs qui ne sont pas encore autorisés sur le changement sur la visibilité
            List<UserLite> notAuthorizedUsers = new ArrayList<>(onlineUsers());

            if (notAuthorizedUsers.removeAll(channel.getAuthorizedPersons())) {
                if (packet.visibility == Visibility.PUBLIC) {
                    // channel devient publique
                    sendMulticast(notAuthorizedUsers, new NewVisibleChannelMessage(getChannel(packet.channelID)));
                }
                else {
                    // channel devient privé
                    sendMulticast(notAuthorizedUsers,
                                  new NewInvisibleChannelsMessage(packet.channelID, "Channel devient privé"));
                }
            }
        }

        if (getChannel(packet.channelID).getVisibility() == Visibility.PUBLIC) {
            sendBroadcast(new ReceiveChannelOperationMessage(ChannelOperation.UPDATE_CHANNEL, packet), null);
        }
        else {
            sendMulticast(channel.getAuthorizedPersons(),
                          new ReceiveChannelOperationMessage(ChannelOperation.UPDATE_CHANNEL, packet));
        }
    }
}
