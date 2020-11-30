package Communication.client;

import Communication.common.CommunicationController;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.generic.UserConnectionMessage;
import Communication.messages.client_to_server.generic.UserDisconnectionMessage;
import common.interfaces.client.*;
import common.sharedData.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationClientController extends CommunicationController {

    private final NetworkClient client;
    private final HeartBeat heart;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private ICommunicationToData dataClient;
    private ICommunicationToIHMMain mainClient;
    private ICommunicationToIHMChannel channelClient;
    private CommunicationClientInterface commInterface;


    public CommunicationClientController() {
        super();
        client = new NetworkClient(this);
        heart  = new HeartBeat(this);
        commInterface = new CommunicationClientInterface(this);
    }


    /* ---------------------------------------------- Core functionalities -------------------------------------------*/

    /**
     * Démarrer Communication Client Controller par se connecter au serveur
     * @param ip
     * @param port
     * @param user
     */
    public void start(String ip, int port, UserLite user) {
        try {
            client.connect(ip, port);
            client.sendMessage(new UserConnectionMessage(user));
            heart.start(user.getId());

            logger.log(Level.INFO, "Connexion au server...");
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Echec de connexion au server!");
            
            disconnect(null);
        }
    }

    /**
     * Arreter Communication Client Controller, annuler tous les threads actifs
     */
    public void stop() {
        taskManager.shutdown();
        heart.stop();

        try {
            client.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wrapper de stop()
     * @param user
     */
    @Override
    public void disconnect(UUID user) {
        sendMessage(new UserDisconnectionMessage(user));

        logger.log(Level.INFO, "Communication Controller déconnecté");

        stop();
    }

    /**
     * Envoyer un message réseau au serveur
     * @param message
     */
    public void sendMessage(NetworkMessage message) {
        client.sendMessage(message);
    }

    /**
     * Recevoir la réponse du server
     */
    public void receiveServerHeartBeat() {
        heart.handleServerReply();
    }

    /* -------------------------------------------- Setup interfaces -------------------------------------------------*/

    /**
     * Installer les interfaces de Data, IHM Main et IHM Channel
     * @param dataIface
     * @param mainIface
     * @param channelIface
     * @return
     */
    public boolean setupInterfaces(ICommunicationToData dataIface,
                                   ICommunicationToIHMMain mainIface,
                                   ICommunicationToIHMChannel channelIface) {
        if (dataIface == null || mainIface == null || channelIface == null) {
            return false;
        }

        setICommunicationToData(dataIface);
        setICommunicationToIHMMain(mainIface);
        setICommunicationToIHMChannel(channelIface);

        return true;
    }

    /**
     * Installer l'interfaces de Data
     * @param dataIface interface de Data
     */
    public void setICommunicationToData(ICommunicationToData dataIface) {
        dataClient = dataIface;
    }

    /**
     * Installer l'interfaces de IHM Main
     * @param mainIface interface de IHM Main
     */
    public void setICommunicationToIHMMain(ICommunicationToIHMMain mainIface) {
        mainClient = mainIface;
    }

    /**
     * Installer l'interfaces de IHM Channel
     * @param channelIface Interface de IHMChannel
     */
    public void setICommunicationToIHMChannel(ICommunicationToIHMChannel channelIface) {
        channelClient = channelIface;
    }

    public IDataToCommunication getDataToCommunication() {
        return (IDataToCommunication) commInterface;
    }

    public IIHMChannelToCommunication getIHMChannelToCommunication() {
        return (IIHMChannelToCommunication) commInterface;
    }

    public IIHMMainToCommunication getIHMMainToCommunication() {
        return (IIHMMainToCommunication) commInterface;
    }

    public CommunicationClientInterface getCommunicationClientInterface(){
        return  commInterface;
    }

    /* ------------------------------------- Connection Notifications handling ---------------------------------------*/

    /**
     * Notifier IHM Main que la connexion a été établie, en donnant les listes de utilisateurs en-lignes et channels visibles
     * @param users Liste des utilisateurs en ligne
     * @param channels Liste des channels visibles
     */
    public void notifyConnectionSuccess(List<UserLite> users, List<Channel> channels) {
        logger.log(Level.INFO, "Connecté au serveur");

        if (mainClient == null)
        {
        	throw new NullPointerException( "IHMMain Iface est null");
        }

        /**
         * Donnee test pour l'integ
         */


        mainClient.connectionAccepted();
        mainClient.setConnectedUsers(users);
        //channelClient.setConnectedUsers(users); //TODO Activer cette methode quand channel l'aura dans son interface

        for (Channel channel: channels) {
            notifyVisibleChannel(channel);
        }
    }

    /**
     * Notifier IHM Main qu'un autre utilisateur est connecté
     * @param newUser Nouvel utilisateur connecté que l'on notifie
     */
    public void notifyUserConnected(UserLite newUser) {
        if (mainClient == null)
        {
        	throw new NullPointerException( "IHMMain Iface est null");
        }

        mainClient.addConnectedUser(newUser);
        //channelClient.addConnectedUser(newUser); //TODO Activer cette methode quand channel l'aura dans son interface
    }

    /**
     * Notifier IHM Main qu'un autre utilisateur est déconnecté
     * @param user Utilisateur déconnecté
     */
    public void notifyUserDisconnected(UserLite user) {
        if (mainClient == null)
        {
        	throw new NullPointerException( "IHMMain Iface est null");
        }

        mainClient.removeConnectedUser(user);
        //channelClient.removeConnectedUser(user); //TODO Activer cette methode quand channel l'aura dans son interface
    }

    /**
     * Notifier IHM Main, Data qu'un channel vient d'etre visible au utilisateur local
     * @param channel Channel a notifié
     */
    public void notifyVisibleChannel(Channel channel) {
        if (mainClient == null)
        {
        	throw new NullPointerException( "IHMMain Iface est null");
        }

//        // TODO INTEGRATION request data addVisibleChannel receive Channel as parameter : (REMARQUE INTEG, CETTE LIGNE RAJOUTE DE LA REDONDANCE) QUE FAIRE??
        /**
         * TODO: IF DATA DOESN'T IMPLEMENT a ChannelList, please delete the line "dataClient.addVisibleChannel(channel)" in next integration
         */
//        dataClient.addVisibleChannel(channel);
        mainClient.channelAdded(channel);

        // TODO handle propriety Channel
    }

    /* -------------------------------- Channel actions notifications handling ---------------------------------------*/

    /**
     * Notifier IHM Main que l'action de création d'un channel a été accepté par serveur
     * @param channel channel à notifier à passer à la notification
     */
    public void notifyChannelCreated(Channel channel) {
        if (mainClient == null)
        {
        	throw new NullPointerException( "IHMMain Iface est null");
        }

        mainClient.channelCreated(channel);

        // TODO INTEGRATION verify with Data if new created Channel is control by Data Client and fill missing sequence diagram
        //dataClient.addVisibleChannel(channel);
    }

    /**
     * Notifier IHM Main que l'action de création d'un channel a été refusé par serveur
     * @param channel channel à notifier à passer à la notification
     */
    public void notifyCreationChannelRefused(Channel channel) {
        if (mainClient == null)
        {
        	
        }

        // TODO INTEGRATION request IHM Main to add channelCreationRefused(Channel) method to ICommunicationToIHMMain interface
        //mainClient.channelCreationRefused(channel);
    }

    /**
     * Notifier Data que la demande de rejoindre un channel a été accepté par serveur
     * @param user Utilisateur qui cherche a rejoindre le channel
     * @param channelID identifiant unique (UUID) du channel
     */
    public void notifyAcceptedToJoinChannel (UserLite user, UUID channelID) {
        if (dataClient == null)
        {
        	throw new NullPointerException( "Data Iface est null");
        }
        // TODO INTEGRATION verify with data what is the difference between userAddedToChannel and addUserToChannel
        dataClient.userAddedToChannel(user, channelID);
    }

    /**
     * Notifier Data que la demande de rejoindre un channel a été refusé par serveur
     * @param user Utilisateur qui cherche a rejoindre le channel
     * @param channelID  identifiant unique (UUID) du channel
     */
    public void notifyRefusedToJoinChannel(UserLite user, UUID channelID) {
        if (dataClient == null)
        {
        	throw new NullPointerException( "Data Iface est null");
        }
        // TODO INTEGRATION request data to add a method userRefusedToJoinChannel(UserLite, UUID channelID) to handle request refused
        //dataClient.userRefusedToJoinChannel(user, channelID);
    }

    /**
     * Notifier Data qu'un autre utilisateur a rejoint un channel
     * @param user Utilisateur qui a rejoint le channel
     * @param channelID identifiant unique (UUID) du channel
     */
    public void notifyNewUserAddedToJoinChannel (UserLite user, UUID channelID) {
        if (dataClient == null)
        {
        	throw new NullPointerException( "Data Iface est null");
        }

        // TODO INTEGRATION verify with data what is the difference between userAddedToChannel and addUserToChannel
        dataClient.addUserToChannel(user, channelID);
    }


    /* ---------------------------------------- Chat Message Handling ------------------------------------------------*/
    
    /**
     * Notifier Data l'arrivée d'un message de chat
     * @param msg message reçu
     * @param channelID  identifiant unique (UUID) du channel dans lequel le message à été reçu
     * @param response Message auquel ce message à répondu
     */
    public void notifyReceiveMessage (Message msg, UUID channelID, Message response) {
        if (dataClient == null)
        {
        	throw new NullPointerException( "Data Iface est null");
        }

        dataClient.receiveMessage(msg, channelID, response);
    }

    /**
     * Déclencher Data de faire l'action de sauvegarde d'un message
     * @param msg message reçu
     * @param channelID identifiant unique (UUID) du channel dans lequel le message à été reçu
     * @param response Message auquel ce message à répondu
     */
    public void saveMessage(Message msg, UUID channelID, Message response) {
        if (dataClient == null)
        {
        	throw new NullPointerException( "Data Iface est null");
        }
        dataClient.saveMessageIntoHistory(msg, channelID, response);
    }

    public void notifyTellOwnerToAddAdmin(UserLite user, UUID channel) {
        if (dataClient == null)
        {
            System.err.println("notifyAddNewAdmin: Data Iface est null");
            return;
        }

        dataClient.saveNewAdminIntoHistory(user, channel);
        // TODO AdminAddedMessage
    }

    public void notifyValidateDeletionChannel(UUID channel) {
        if (dataClient == null)
        {
            System.err.println("notifyAddNewAdmin: Data Iface est null");
            return;
        }
        dataClient.removeChannelFromList(channel, 0, "Channel supprimé");
        //TODO check deleteChannel
    }
    public List<Message> requestHistory (UUID channelID){
        if (dataClient == null)
        {
            System.err.println("notifyNewUserAddedToJoinChannel: Data Iface est null");
            return null;
        }
        return dataClient.getHistory(channelID);
    }


    public void notifyUserHasLeftChannel(Channel channel, UserLite userLite) {
        //Ask to data
        //dataClient.leaveChannel(channel, userLite)
        dataClient.deleteUserFromChannel(userLite, channel.getId(), 0, "Leave");
        if (channel.getType() != ChannelType.OWNED && channel.getCreator().getId() == client.getUUID()) {
            // FIXME
            // sendMessage(new ValideUserLeftMessage(channel, userLite, dataClient.getMembers(channel.getId())));
        }
    }

    /* ---------------------------------------- Admin access right Handling ------------------------------------------*/
    /**
     * Avertit Data de l'ajout d'un nouvel admin
     * @param channelID [UUID] Channel ou un admin est ajoute
     * @param user [UserLite] Utilisateur devenant admin
     */
    public void notifyNewAdminAdded(UUID channelID, UserLite user) {
        if (dataClient == null)
        {
            System.err.println("notifyNewAdminAdded: Data Iface est null");
            return;
        }

        System.err.println("new admin " + user.getNickName() + " added to channel " + channelID);
        dataClient.newAdmin(user, channelID);
    }

    /**
     * Avertit Owner d'ajouter un nouvel admin au channel proprietaire
     * @param channelID [UUID] Channel ou un admin est ajoute
     * @param user [UserLite] Utilisateur devenant admin
     */
    public void saveNewAdmin(UUID channelID, UserLite user) {
        if (dataClient == null)
        {
            System.err.println("saveNewAdmin: Data Iface est null");
            return;
        }

        System.err.println("request owner to add admin " + user.getNickName() + " to channel " + channelID);
        dataClient.saveNewAdminIntoHistory(user, channelID);
    }
}
