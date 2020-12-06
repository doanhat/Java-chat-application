package common.interfaces.client;

import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

/**
 * Interface fournie par le module IHMMain pour le module Communication
 */
public interface ICommunicationToIHMMain {
    /**
     * Informe IHMMain que le serceur a accepté la connexion de l'utilisateur.
     */
    public void connectionAccepted();

    /**
     * Informe IHMMain du statut de la connexion de l'utilisateur au serveur
     * @param status
     */
    public void setConnectionStatus(int status);

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
     * Informe qu'un channel vient d'être créer et le fourni
     * @param channel Le channel nouvellement créé
     */
    public void channelAdded(Channel channel);
}
