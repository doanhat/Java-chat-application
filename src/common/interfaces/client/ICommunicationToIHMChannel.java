package common.interfaces.client;


import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.List;

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
}