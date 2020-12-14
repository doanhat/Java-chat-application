package common.interfaces.client;

import common.shared_data.Channel;
import common.shared_data.ConnectionStatus;
import common.shared_data.UserLite;

import java.util.List;

/**
 * Interface fournie par le module IHMMain pour le module Communication
 */
public interface ICommunicationToIHMMain {

    /**
     * Informe IHMMain du statut de la connexion de l'utilisateur au serveur
     * @param status
     */
    public void setConnectionStatus(ConnectionStatus status);

    /**
     * Transmet a IHMMain la liste des utilisateurs actuellement connectés sur le serveur
     * @param users Liste des utilisateurs connectés
     */
    public void setConnectedUsers(List<UserLite> users);

    /**
     * Ajoute un utilisateur connecté au serveur
     * @param user L'utilisateur concerné
     */
    public void addConnectedUser(UserLite user);

    /**
     * Supprime un utilisateur qui n'est plus connecté au serveur
     * @param user L'utilisateur concerné
     */
    public void removeConnectedUser(UserLite user);

    /**
     * Informe le créateur d'un channel que celui-ci vient d'être créé.
     * @param channel Le channel créé
     */
    public void channelCreated(Channel channel);

    /**
     * Ajoute le channel à la liste des channels visibles
     * Par exemple pour informer qu'un channel vient d'être créé
     * @param channel Le channel à ajouter
     */
    public void channelAdded(Channel channel);

    /**
     * Ajoute une liste de channels à la liste des channels visibles
     * Par exemple lors de la création d'un channel ou lors de l'initialisation
     * @param channels La liste à ajouter
     */
    public void channelAddedAll(List<Channel> channels);
}
