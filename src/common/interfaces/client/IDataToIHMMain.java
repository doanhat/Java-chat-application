package common.interfaces.client;

import java.util.List;
import common.sharedData.Channel;

/**
 * Interface fournie par le module IHMMain pour le module Data
 */
public interface IDataToIHMMain {
    /**
     * Supprime le channel de la liste des channels visibles
     * @param channel
     */
    public void removeChannel(Channel channel);

    /**
     * Ajoute le channel à la liste des channels visibles
     * @param channel Le channel à ajouter
     */
    public void addChannel(Channel channel);

    /**
     * Ajoute une liste de channels à la liste des channels visibles
     * @param channels La liste à ajouter
     */
    public void addAllChannels(List<Channel> channels);
}
