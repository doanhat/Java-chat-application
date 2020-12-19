package common.interfaces.client;

import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.UUID;

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

    /**
     * Permet de récupérer la liste des utilisateurs connectés à un channel.
     * @param channelId id du channel dont on veut la liste des utilisateurs connectés
     * @return liste de UserLite des utilisateurs connectés
     */
    public List<UserLite> getConnectedUsers(UUID channelId);

    /**
     * Permet de quitter l'onglet d'un channel que l'utilisateur local a quitté.
     * @param channelId id du channel a fermer
     */
    public void removeChannel(UUID channelId);
}