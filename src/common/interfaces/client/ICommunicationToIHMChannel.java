package common.interfaces.client;


import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

/**
 * Interface que IHM Channel fournit à Communication
 */
public interface ICommunicationToIHMChannel {

    /**
     * Méthode permettant de changer le nickname d'un utilisateur
     * @param user l'utilisateur
     */
    void changeNickname(User user);

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
     *Méthode permettant de signaler à tous les utilisateurs d'un channel qu'un membre a quitté le channel
     * Déclenche une notification informant que le user a quitté le channel
     * @param ChannelId id du channel concerné
     * @param user user qui quitte le channel
     */
    void leaveChannel(UUID ChannelId, UserLite user);

}