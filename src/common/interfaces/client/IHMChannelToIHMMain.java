package common.interfaces.client;

import common.sharedData.Channel;

/**
 * Les actions de redirection entre l'application et les channels
 * @author Emna
 */
public interface IHMChannelToIHMMain {
    /**
     * revenir vers l'interface de base
     * @return void
     */
    void redirectToHomePage();
    /**
     * quitter le channel en cours
     * @return void
     */
    void closeChannel(Channel channel);
}
