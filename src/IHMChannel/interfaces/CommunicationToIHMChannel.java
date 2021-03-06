package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.ICommunicationToIHMChannel;
import common.shared_data.*;
import javafx.application.Platform;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

    public class CommunicationToIHMChannel implements ICommunicationToIHMChannel {

    public CommunicationToIHMChannel(IHMChannelController controller){
        this.controller = controller;
    }

    /**
     *  Méthode permettant d'annuler le ban d'un utilisateur d'un channel d'id channelID.
     * @param kick Classe contenant les informations lié au kick
     * @param channelID id du channel concerné
     */
    public void banOfUserCancelledNotification(Kick kick, UUID channelID){
        controller.getChannelPageController().getChannelController(channelID).removeKick(kick);
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
        channel.setMessages(history);
        Platform.runLater(() -> {
                try {
                    controller.getChannelPageController().addOpenedChannel(channel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for(UserLite user : connectedUsers){
                    String nickname = channel.getNickNames().get(user.getId().toString());
                    if(nickname != null){
                        user.setNickName(nickname);
                    }
                }
                controller.getChannelPageController().getChannelController(channel.getId()).setConnectedMembersList(connectedUsers);
                controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
            });
    }

    @Override
    public void addConnectedUser(UUID channelId, UserLite user) {
        Platform.runLater(() -> {
                controller.getChannelPageController().getChannelController(channelId).addConnectedUser(user);
                controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
            });
    }

    @Override
    public void removeConnectedUser(UUID channelId, UserLite user) {
        Platform.runLater(() -> {
                controller.getChannelPageController().getChannelController(channelId).removeConnectedUser(user);
                controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
            });
    }

    @Override
    public void leaveChannel(UUID channelID, UserLite user) {
        Platform.runLater(() -> {
                controller.getChannelPageController().leaveChannel(channelID, user);
                controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
        });
    }


        private IHMChannelController controller;

    @Override
    public void addAuthorizedUser(UUID channel, UserLite user) {
        Platform.runLater(() -> {
                try {
                    controller.getChannelPageController().getChannelController(channel).addUser(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    @Override
    public void removeAuthorizedUser(UUID channel, UserLite user) {
        Platform.runLater(() -> {
                try {
                    controller.getChannelPageController().getChannelController(channel).removeUserAuthorization(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }

}
