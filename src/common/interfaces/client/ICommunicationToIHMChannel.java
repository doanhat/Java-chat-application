package common.interfaces.client;


import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.User;
import common.shared_data.UserLite;

import java.util.List;
import java.util.UUID;

/**
 * Interface que IHM Channel fournit à Communication
 */
public interface ICommunicationToIHMChannel {

    /**
     * Méthode permettant de changer le nickname d'un utilisateur
     * @param user l'utilisateur
     * @param channel channel concerné par la mise à jour de nickname
     */
    void changeNickname(UserLite user, UUID channel);

    /**
     * Méthode permettant d'afficher l'historique des messages
     * @param channel le channel concerné
     * @param history la liste des messages
     * @param connectedUsers
     */
    void displayChannelHistory(Channel channel, List<Message> history, List<UserLite> connectedUsers);

    /**
     * Méthode permettant de signaler à tous les utilisateurs d'un channel qu'un nouveau membre s'est connecté.
     * Déclenche l'affichage du nickname de ce nouveau membre dans la liste à droite.
     * @param channelId id du channel concerné
     * @param user user qui vient de se connecter
     */
    void addConnectedUser(UUID channelId, UserLite user);

    /**
     * Méthode permettant de signaler à tous les utilisateurs d'un channel qu'un membre s'est déconnecté.
     * Déclenche le retrait de l'affichage du nickname de ce membre dans la liste à droite.
     * @param channelId id du channel concerné
     * @param user user qui vient de se déconnecter
     */
    void removeConnectedUser(UUID channelId, UserLite user);

    /**
     * Ajouter un membre à la liste des utilisateurs autorisés du channel
     * @param channel channel concerné par l'ajout
     * @param user utilisateur ajouté
     */
    void addAuthorizedUser(UUID channel, UserLite user);

    /**
     * Supprimer un membre de la liste des utilisateurs autorisés du channel
     * @param channel channel concerné par la suppression
     * @param user utilisateur supprimé
     */
    void removeAuthorizedUser(UUID channel, UserLite user);

    /**
     *Méthode permettant de signaler à tous les utilisateurs d'un channel qu'un membre a quitté le channel
     * Déclenche une notification informant que le user a quitté le channel
     * @param ChannelId id du channel concerné
     * @param user user qui quitte le channel
     */
    void leaveChannel(UUID ChannelId, UserLite user);

}