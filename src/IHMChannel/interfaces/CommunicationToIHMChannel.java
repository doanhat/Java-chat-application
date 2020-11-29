package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.ICommunicationToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;

import java.util.List;

public class CommunicationToIHMChannel implements ICommunicationToIHMChannel {

    public CommunicationToIHMChannel(IHMChannelController controller){
        this.controller = controller;
    }

    /**
     * Méthode permettant de changer le nickname d'un utilisateur
     *
     * @param user l'utilisateur
     */
    @Override
    public void changeNickname(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Méthode permettant d'afficher l'historique des messages
     *
     * @param channel le channel concerné
     * @param history la liste des messages
     */
    @Override
    public void displayHistory(Channel channel, List<Message> history) {
        throw new UnsupportedOperationException("Not implemented yet");

    }

    @Override
    public void sendInvite(User sender, User receiver, Channel channel) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private IHMChannelController controller;

}
