package common.interfaces.client;

import common.sharedData.Channel;

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
}
