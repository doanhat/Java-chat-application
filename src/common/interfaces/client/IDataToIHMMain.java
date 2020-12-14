package common.interfaces.client;

import java.util.List;
import common.sharedData.Channel;

/**
 * Interface fournie par le module IHMMain pour le module Data
 */
public interface IDataToIHMMain {
    /**
     * Supprime le channel de l'interface
     * @param channel
     */
    public void removeChannel(Channel channel);

    /**
     * Ajoute le channel à la liste des channels de l'interface
     * @param channel
     */
    public void addChannelToList(Channel channel);

    /**
     * Met à jour l'affichage de la liste des channels
     * @param channelList
     */
    public void updateListChannel(List<Channel> channelList);

}
