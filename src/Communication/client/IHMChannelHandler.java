package Communication.client;

import common.interfaces.client.ICommunicationToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IHMChannelHandler {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private ICommunicationToIHMChannel channelClient;

    /**
     * Installer l'interfaces de IHM Channel
     *
     * @param channelIface Interface de IHMChannel
     */
    public void setICommunicationToIHMChannel(ICommunicationToIHMChannel channelIface) {
        channelClient = channelIface;
    }

    /* -------------------------------- Channel actions notifications handling ---------------------------------------*/

    /**
     * Notifier Data que la demande de rejoindre un channel a été accepté par serveur
     *
     * @param channel channel rejoint
     * @param activeUsers liste de users actifs d'un channels
     * @param isAccepted (boolean) réponse du serveur
     */
    public void notifyJoinChannelResponse(Channel channel, List<UserLite> activeUsers, boolean isAccepted) {
        if (isAccepted) {
            returnChannelHistory(channel, activeUsers);
        }
        else {
            logger.log(Level.FINE, "Join channel request est refusé");
        }
    }

    /**
     * Notifie Application client qu'un utilisateur vient de rejoindre un channel
     * @param user autre utilisateur connecté
     * @param channelID ID du channel
     */
    public void notifyUserJoinedChannel(UserLite user, UUID channelID) {
        logger.log(Level.FINE, user.getNickName() + " joined channel " + channelID);
        channelClient.addConnectedUser(channelID, user);
    }

    /**
     * Retire une personne connecté d'un channel
     *
     * @param channelID  identifiant unique (UUID) du channel quitté
     * @param userLite identifiant unique (UUID) de l'utilisateur qui est parti
     */
    public void notifyUserHasLeftChannel(UUID channelID, UserLite userLite) {
        channelClient.removeConnectedUser(channelID, userLite);
    }

    /**
     * Notifie Application client qu'un utilisateur vient d'etre autoriser dans un channel
     * @param user autre utilisateur connecté
     * @param channelID ID du channel
     */
    public void notifyUserAuthorizeChannel(UserLite user, UUID channelID) {
        logger.log(Level.FINE, user.getNickName() + " authorized channel " + channelID);
        channelClient.addAuthorizedUser(channelID, user);
    }

    /**
     * Retourne historique d'un channel
     * @param channel Channel
     * @param activeUsers liste des clients actifs du channel
     */
    public void returnChannelHistory(Channel channel, List<UserLite> activeUsers) {
        channelClient.displayChannelHistory(channel, channel.getMessages(), activeUsers);
    }
}
