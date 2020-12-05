package common.interfaces.client;

import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

import java.util.List;

/**
 * Cette classe offre les méthodes nécessaire par IHMChannel vis à vis de IHMMain
 */
public interface IIHMChannelToIHMMain {
    /**
     * Indique à IHMMain de revenir sur la page d'accueil
     */
    public void redirectToHomePage();

    /**
     * Indique à IHMMain que le channel de ferme un channel et de l'enlever dans la liste des channels disponibles
     * @param channel le channel à fermer
     */
    public void closeChannel(Channel channel);

    /**
     * Indique à IHMMain la liste des channels actuellement ouvert par l'utilisateur
     * @param openedChannels la liste des channels ouverts
     */
    public void setOpenedChannelsList(List<Channel> openedChannels);

    /**
     * Indique à IHMMain la channel actuellement visible par l'utilisateur
     * @param channel le channel actuellement visible
     */
    public void setCurrentVisibleChannel(Channel channel);

    /**
     * Retourne la liste des utilisateurs actuellement connectés aux serveurs
     * @return La liste des utilisateurs connectés. Renvoie une liste vide si aucun utilisateur n'est connecté
     */
    public List<UserLite> getConnectedUsersList();
}
