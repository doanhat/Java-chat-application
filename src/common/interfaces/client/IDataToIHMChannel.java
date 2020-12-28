package common.interfaces.client;

import common.shared_data.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public interface IDataToIHMChannel {
    /**
     * Permet de traiter la suppression du channel actuellement ouvert sur l'interface.
     * @param channelId id du channel supprimé
     */
    void openChannelDeleted(UUID channelId);

    /**
     * Permet d'ajouter un administrateur pour un channel.
     * @param user user qui devient admin
     * @param channelId id du channel pour lequel on a ajouté un admin
     */
    void addNewAdmin(UserLite user, UUID channelId) throws IOException;

    void removeAdmin(UserLite user, UUID channelId) throws IOException;

    /**
     * Quand l'utilisateur se fait kické d'un channel, retire le channel en question de sa liste de channels.
     * Ce retrait peut, comme un kick, être temporaire.
     * @param channelID id du channel à retirer
     * @param duration durée du kick
     * @param explanation motif du kick
     */
    void removeChannelFromList(UUID channelID, int duration, String explanation);

    /**
     * Notification pour les membres restants d'un channel qu'un utilisateur a été kické.
     * @param user utilisateur kické
     * @param channelId id du channel concerné
     * @param duration durée du kick
     * @param explanation motif du kick
     */
    void userBanNotification(UserLite user, UUID channelId, LocalDate duration, String explanation);

    /**
     * Notifie d'un retour d'un utilisateur précédemment kické.
     * @param user user revenu sur le channel
     * @param channelId id du channel sur lequel user est revenu
     */
    void userBanCancelledNotification(UserLite user, UUID channelId);

    /**
     * Permet la réception d'un message sur un channel.
     * @param message message reçu
     * @param channelId id du channel sur lequel le message a été reçu
     * @param responseTo message parent
     */
    void receiveMessage(Message message, UUID channelId, Message responseTo);

    /**
     * Permet la modification d'un message.
     * @param message message d'origine
     * @param newMessage nouveau message
     * @param channelId id du channel sur lequel le message a été modifié
     */
    void editMessage(Message message, Message newMessage, UUID channelId);

    /**
     * Permet de liker un message.
     * @param channelId id du channel du message
     * @param message message liké
     * @param user utilisateur à l'origine du like
     */
    void likeMessage(UUID channelId, Message message, UserLite user);

    /**
     * Permet la suppression d'un message.
     * @param message message supprimé
     * @param channelId channel sur lequel le message a été supprimé
     * @param deletedByCreator booléen indiquant si la suppression a été faite par le propriétaire du message
     */
    void deleteMessage(Message message, UUID channelId, boolean deletedByCreator);

    Channel getChannel(UUID id);


    /**
     * Méthode permettant de mofidier les informations concernant un channel depuis le menu contextuel
     */

    void modifyChannel(UUID channelId, String name, String description, Visibility visibility);

    /**
     * Méthode permettant de changer le nickname d'un utilisateur
     * @param user l'utilisateur
     * @param channel channel concerné par la mise à jour de nickname
     */
    void changeNickname(UserLite user, UUID channel, String newNickname);
}
