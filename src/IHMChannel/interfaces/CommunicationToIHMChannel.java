package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.ICommunicationToIHMChannel;
import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.User;
import common.shared_data.UserLite;
import javafx.application.Platform;

import java.io.IOException;
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
        channel.setMessages(history);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    controller.getChannelPageController().addOpenedChannel(channel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                controller.getChannelPageController().getChannelController(channel.getId()).setConnectedMembersList(connectedUsers);
                controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
            }
        });
    }

    @Override
    public void addConnectedUser(UUID channelId, UserLite user) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.getChannelPageController().getChannelController(channelId).addConnectedUser(user);
                controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
            }
        });
    }

    @Override
    public void removeConnectedUser(UUID channelId, UserLite user) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.getChannelPageController().getChannelController(channelId).removeConnectedUser(user);
                controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());
            }
        });
    }

    @Override
    public void leaveChannel(UUID channelID, UserLite user) {
        controller.getChannelPageController().leaveChannel(channelID, user);
        controller.getInterfaceToIHMMain().setOpenedChannelsList(controller.getOpenedChannelsList());

    }

    private IHMChannelController controller;

    @Override
    public void addAuthorizedUser(UUID channel, UserLite user){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //on transmet l'utilisateur à la liste d'utilisateurs connectés au channel de la vue ConnectedMembersList
                System.out.println("Utilisateur add : "+user.getNickName());
                controller.getChannelPageController().getChannelController(channel).getChannelMembersDisplay().getController().getConnectedMembersListDisplay().getController().addMemberToList(user);
            }
        });
    }

    @Override
    public void removeAuthorizedUser(UUID channel, UserLite user){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //on supprime l'utilisateur de la liste d'utilisateurs connectés au channel de la vue ConnectedMembersList

                controller.getChannelPageController().getChannelController(channel).getChannelMembersDisplay().getController().getConnectedMembersListDisplay().getController().removeMemberFromList(user);
                System.out.println("Utilisateur supp : "+user.getNickName());
            }
        });
    }

}
