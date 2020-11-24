package Communication.client;

import Communication.common.CommunicationController;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.UserConnectionMessage;
import Communication.messages.client_to_server.UserDisconnectionMessage;
import common.interfaces.client.*;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class CommunicationClientController extends CommunicationController {

    private static CommunicationClientController instance = null;

    private final NetworkClient client;
    private ICommunicationToData dataClient;
    private ICommunicationToIHMMain mainClient;
    private ICommunicationToIHMChannel channelClient;

    private CommunicationClientController() {
        super();
        client = new NetworkClient(this);
    }

    /**
     * Recuperer singleton de CommunicationClientController
     * @return
     */
    public static CommunicationClientController instance() {
        if (instance == null) {
            instance = new CommunicationClientController();
        }

        return instance;
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

            System.err.println("Connexion au server...");
        }
        catch (IOException e) {
            System.err.println("Echec de connexion au server!");

            disconnect(null);
        }
    }

    /**
     * Arreter Communication Client Controller, annuler tous les threads actifs
     */
    public void stop() {
        taskManager.shutdown();

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

        System.err.println("Communication Controller déconnecté");

        stop();
    }

    /**
     * Envoyer un message réseau au serveur
     * @param message
     */
    public void sendMessage(NetworkMessage message) {
        client.sendMessage(message);
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

        setICommunicationData(dataIface);
        setICommunicationToIHMMain(mainIface);
        setICommunicationToIHMChannel(channelIface);

        return true;
    }

    /**
     * Installer l'interfaces de Data
     * @param dataIface
     */
    public void setICommunicationData(ICommunicationToData dataIface) {
        dataClient = dataIface;
    }

    /**
     * Installer l'interfaces de IHM Main
     * @param mainIface
     */
    public void setICommunicationToIHMMain(ICommunicationToIHMMain mainIface) {
        mainClient = mainIface;
    }

    /**
     * Installer l'interfaces de IHM Channel
     * @param channelIface
     */
    public void setICommunicationToIHMChannel(ICommunicationToIHMChannel channelIface) {
        channelClient = channelIface;
    }

    /* ------------------------------------- Connection Notifications handling ---------------------------------------*/

    /**
     * Notifier IHM Main que la connexion a été établie, en donnant les listes de utilisateurs en-lignes et channels visibles
     * @param users
     * @param channels
     */
    public void notifyConnectionSuccess(List<UserLite> users, List<Channel> channels) {
        System.err.println("Connecté au serveur");

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

    /**
     * Notifier IHM Main qu'un autre utilisateur est connecté
     * @param newUser
     */
    public void notifyUserConnected(UserLite newUser) {
        if (mainClient == null)
        {
            System.err.println("notifyUserConnected: IHMMain Iface est null");
            return;
        }

        mainClient.addConnectedUser(newUser);
    }

    /**
     * Notifier IHM Main qu'un autre utilisateur est déconnecté
     * @param user
     */
    public void notifyUserDisconnected(UserLite user) {
        if (mainClient == null)
        {
            System.err.println("notifyUserDisconnected: IHMMain Iface est null");
            return;
        }

        mainClient.removeConnectedUser(user);
    }

    /**
     * Notifier IHM Main, Data qu'un channel vient d'etre visible au utilisateur local
     * @param channel
     */
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

    /* -------------------------------- Channel actions notifications handling ---------------------------------------*/

    /**
     * Notifier IHM Main que l'action de création d'un channel a été accepté par serveur
     * @param channel
     */
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

    /**
     * Notifier IHM Main que l'action de création d'un channel a été refusé par serveur
     * @param channel
     */
    public void notifyCreationChannelRefused(Channel channel) {
        if (mainClient == null)
        {
            System.err.println("notifyCreationChannelRefused: IHMMain Iface est null");
            return;
        }

        // TODO INTEGRATION request IHM Main to add channelCreationRefused(Channel) method to ICommunicationToIHMMain interface
        //mainClient.channelCreationRefused(channel);
    }

    /**
     * Notifier Data que la demande de rejoindre un channel a été accepté par serveur
     * @param user
     * @param channelID
     */
    public void notifyAcceptedToJoinChannel (UserLite user, UUID channelID) {
        if (dataClient == null)
        {
            System.err.println("notifyAcceptedToJoinChannel: Data Iface est null");
            return;
        }
        // TODO INTEGRATION verify with data what is the difference between userAddedToChannel and addUserToChannel
        dataClient.userAddedToChannel(user, channelID);
    }

    /**
     * Notifier Data que la demande de rejoindre un channel a été refusé par serveur
     * @param user
     * @param channelID
     */
    public void notifyRefusedToJoinChannel(UserLite user, UUID channelID) {
        if (dataClient == null)
        {
            System.err.println("notifyRefusedToJoinChannel: Data Iface est null");
            return;
        }
        // TODO INTEGRATION request data to add a method userRefusedToJoinChannel(UserLite, UUID channelID) to handle request refused
        //dataClient.userRefusedToJoinChannel(user, channelID);
    }

    /**
     * Notifier Data qu'un autre utilisateur a rejoint un channel
     * @param user
     * @param channelID
     */
    public void notifyNewUserAddedToJoinChannel (UserLite user, UUID channelID) {
        if (dataClient == null)
        {
            System.err.println("notifyNewUserAddedToJoinChannel: Data Iface est null");
            return;
        }

        // TODO INTEGRATION verify with data what is the difference between userAddedToChannel and addUserToChannel
        dataClient.addUserToChannel(user, channelID);
    }


    /* ---------------------------------------- Chat Message Handling ------------------------------------------------*/
    /**
     * Notifier Data l'arrivée d'un message de chat
     * @param msg
     * @param channelID
     * @param response
     */
    public void notifyReceiveMessage (Message msg, UUID channelID, Message response) {
        if (dataClient == null)
        {
            System.err.println("notifyReceiveMessage: Data Iface est null");
            return;
        }

        dataClient.receiveMessage(msg, channelID, response);
    }

    /**
     * Déclencher Data de faire l'action de sauvegarde d'un message
     * @param msg
     * @param channelID
     * @param response
     */
    public void saveMessage(Message msg, UUID channelID, Message response) {
        if (dataClient == null)
        {
            System.err.println("saveMessage: Data Iface est null");
            return;
        }

        dataClient.saveMessageIntoHistory(msg, channelID, response);
    }
}
