package IHMChannel.interfaces;

import common.interfaces.client.IDataToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;

public class DataToIHMChannel implements IDataToIHMChannel{

    /**
     * Permet de traiter la suppression du channel actuellement ouvert sur l'interface.
     * @param channel channel supprimé
     */
    @Override
    public void openChannelDeleted(Channel channel) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Permet d'ajouter un administrateur pour un channel.
     *
     * @param user    user qui devient admin
     * @param channel channel pour lequel on a ajouté un admin
     */
    @Override
    public void addNewAdmin(User user, Channel channel) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Quand l'utilisateur se fait kické d'un channel, retire le channel en question de sa liste de channels.
     * Ce retrait peut, comme un kick, être temporaire.
     *
     * @param channel     channel à retirer
     * @param duration    durée du kick
     * @param explanation motif du kick
     */
    @Override
    public void removeChannelFromList(Channel channel, int duration, String explanation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Notification pour les membres restants d'un channel qu'un utilisateur a été kické.
     *
     * @param user        utilisateur kické
     * @param channel     channel concerné
     * @param duration    durée du kick
     * @param explanation motif du kick
     */
    @Override
    public void userBanNotification(User user, Channel channel, int duration, String explanation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Notifie d'un retour d'un utilisateur précédemment kické.
     *
     * @param user    user revenu sur le channel
     * @param channel channel sur lequel user est revenu
     */
    @Override
    public void userBanCancelledNotification(User user, Channel channel) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Permet la réception d'un message sur un channel.
     *
     * @param message    message reçu
     * @param channel    channel sur lequel le message a été reçu
     * @param responseTo message parent
     */
    @Override
    public void receiveMessage(Message message, Channel channel, Message responseTo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Permet la modification d'un message.
     *
     * @param message    message d'origine
     * @param newMessage nouveau message
     * @param channel    channel sur lequel le message a été modifié
     */
    @Override
    public void editMessage(Message message, Message newMessage, Channel channel) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Permet de liker un message.
     *
     * @param channel channel du message
     * @param message message liké
     * @param user    utilisateur à l'origine du like
     */
    @Override
    public void likeMessage(Channel channel, Message message, User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Permet la suppression d'un message.
     *
     * @param message          message supprimé
     * @param channel          channel sur lequel le message a été supprimé
     * @param deletedByCreator booléen indiquant si la suppression a été faite par le propriétaire du message
     */
    @Override
    public void deleteMessage(Message message, Channel channel, boolean deletedByCreator) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
