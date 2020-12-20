package common.interfaces.client;

import java.util.List;
import java.util.UUID;

import common.shared_data.Channel;

/**
 * Interface fournie par le module IHMMain pour le module Data
 */
public interface IDataToIHMMain {
    /**
     * Supprime le channel de la liste des channels visibles
     * @param channelID L'ID du channel à retirer
     */
    public void removeChannel(UUID channelID);

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
