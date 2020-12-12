package common.interfaces.client;


import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.User;

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
     */
    void displayHistory(Channel channel, List<Message> history);
}