package common.interfaces.client;

import common.sharedData.Channel;
import javafx.scene.layout.Region;

import java.util.UUID;

/**
 * Interface fournie par le module IHMChannel pour le module IHMMain
 */
public interface IIHMMainToIHMChannel {
    /**
     * Initialise la view d'IHM-Channel et affiche le channel demander
     * Puis retourne le noeud racine de cette vue.
     * @param channel le channel à afficher
     * @return le noeud racine de la vue d'IHM-Channel
     */
    public Region initIHMChannelWindow(Channel channel);

    /**
     * Permet d'afficher le fil de discussion du channel.
     * Connecté l'utilisateur au channel si celui-ci ne l'était pas.
     * @param channelId l'identifiant du channel à afficher
     */
    public void viewChannel(Channel channel);
}