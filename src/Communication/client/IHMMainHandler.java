package Communication.client;

import common.interfaces.client.ICommunicationToIHMMain;
import common.sharedData.Channel;
import common.sharedData.ConnectionStatus;
import common.sharedData.UserLite;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IHMMainHandler {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private ICommunicationToIHMMain mainClient;

    /**
     * Installer l'interfaces de IHM Main
     *
     * @param mainIface interface de IHM Main
     */
    public void setICommunicationToIHMMain(ICommunicationToIHMMain mainIface) {
        mainClient = mainIface;
    }

    /* ------------------------------------- Connection Notifications handling ---------------------------------------*/

    /**
     * Notifier IHM Main que la connexion a été établie, en donnant les listes de utilisateurs en-lignes et channels visibles
     *
     * @param users    Liste des utilisateurs en ligne
     * @param channels Liste des channels visibles
     */
    public void notifyConnectionSuccess(List<UserLite> users, List<Channel> channels) {
        logger.log(Level.INFO, "Connecté au serveur");

        mainClient.setConnectionStatus(ConnectionStatus.CONNECTED);
        mainClient.setConnectedUsers(users);

        notifyVisibleChannels(channels);
    }

    /**
     * Notifier IHM Main que la connexion a été perdue
     */
    public void notifyLostConnection() {
        logger.log(Level.INFO, "Déconnecté au serveur");

        mainClient.setConnectionStatus(ConnectionStatus.LOST);
    }

    /**
     * Notifier IHM Main qu'un autre utilisateur est connecté
     *
     * @param newUser Nouvel utilisateur connecté que l'on notifie
     */
    public void notifyUserConnected(UserLite newUser) {
        logger.log(Level.FINE, "Communication --> IHM Main: new user connected: " + newUser.getNickName());
        mainClient.addConnectedUser(newUser);
    }

    /**
     * Notifier IHM Main qu'un autre utilisateur est déconnecté
     *
     * @param user Utilisateur déconnecté
     */
    public void notifyUserDisconnected(UserLite user) {
        mainClient.removeConnectedUser(user);
    }

    /**
     * Notifier IHM Main, Data qu'un channel vient d'etre visible au utilisateur local
     *
     * @param channel Channel a notifié
     */
    public void notifyVisibleChannel(Channel channel) {
        mainClient.channelAdded(channel);
    }

    /**
     * Notifier IHM Main, Data qu'une liste de channels vient d'etre visible au utilisateur local
     *
     * @param channels La liste de channels a notifié
     */
    public void notifyVisibleChannels(List<Channel> channels) {
        mainClient.channelAddedAll(channels);
    }

    /* -------------------------------- Channel actions notifications handling ---------------------------------------*/

    /**
     * Notifier IHM Main que l'action de création d'un channel a été accepté ou réfusé par serveur
     *
     * @param channel channel à notifier à passer à la notification
     * @param isCreated indique si le channel a été bien créé.
     */
    public void notifyChannelCreationResponse(Channel channel, boolean isCreated) {
        if (isCreated) {
            logger.log(Level.FINE, "Creation channel {} est accepté", channel.getId());
            mainClient.channelCreated(channel);
        }
        else {
            logger.log(Level.FINE, "Creation channel {} est refusé", channel.getId());
            //mainClient.channelCreationRefused(channel);
        }
    }
}
