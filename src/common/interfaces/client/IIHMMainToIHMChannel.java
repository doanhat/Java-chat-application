package common.interfaces.client;

import common.sharedData.Channel;
import javafx.scene.layout.Region;

/**
 * Interface fournie par le module IHMChannel pour le module IHMMain
 */
public interface IIHMMainToIHMChannel {
    /**
     * Initialise la view d'IHM-Channel
     * Puis retourne le noeud racine de cette vue.
     * @return le noeud racine de la vue d'IHM-Channel
     */
    public Region initIHMChannelWindow();

    /**
     * Permet d'afficher le fil de discussion du channel.
     * Connecté l'utilisateur au channel si celui-ci ne l'était pas.
     * @param channel le channel à afficher
     */
    public void viewChannel(Channel channel);
}