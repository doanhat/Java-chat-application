package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.ICommunicationToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.io.IOException;
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
     * Méthode permettant d'obtenir la dernière version d'un channel,
     * sa dernière liste de messages ainsi que sa dernière liste d'uilisateurs connectés.
     *  @param channel le channel concerné
     *  @param history la liste des messages
     *  @param connectedUsers la liste des utilisateurs connectés
     */
    @Override
    public void displayChannelHistory(Channel channel, List<Message> history, List<UserLite> connectedUsers) {
        try {
            controller.getChannelPageController().addOpenedChannel(channel);
            controller.getChannelPageController().getChannelController(channel.getId()).setConnectedMembersList(connectedUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IHMChannelController controller;

}
