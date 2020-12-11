package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.ICommunicationToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;
import common.sharedData.UserLite;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            channel.setMessages(history);
            controller.getChannelPageController().addOpenedChannel(channel);
            controller.getChannelPageController().getChannelController(channel.getId()).setConnectedMembersList(connectedUsers);
            controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addConnectedUser(UUID channelId, UserLite user) {
        controller.getChannelPageController().getChannelController(channelId).addConnectedUser(user);
        controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
    }

    @Override
    public void removeConnectedUser(UUID channelId, UserLite user) {
        controller.getChannelPageController().getChannelController(channelId).removeConnectedUser(user);
        controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
    }

    @Override
    public void leaveChannel(UUID channelID, UserLite user) {
        controller.getChannelPageController().leaveChannel(channelID, user);
        controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());

    }

    private IHMChannelController controller;

}
