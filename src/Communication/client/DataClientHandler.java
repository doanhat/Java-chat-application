package Communication.client;

import common.interfaces.client.ICommunicationToData;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataClientHandler {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private ICommunicationToData dataClient;

    /**
     * Installer l'interfaces de Data
     *
     * @param dataIface interface de Data
     */
    public void setICommunicationToData(ICommunicationToData dataIface) {
        this.dataClient = dataIface;
    }

    /* ------------------------------------- Channel Notifications handling ---------------------------------------*/

    /**
     * Retire un channel de la liste des channels car celui-ci à été invisible
     *
     * @param channelIDs liste identifiant unique (UUID) des channels à supprimer
     */
    public void notifyInvisibleChannels(List<UUID> channelIDs) {
        // TODO INTEGRATION V3: verify which method is for delete proprietary channel and which is for delete channel from visible list
        for (UUID channelID: channelIDs) {
            dataClient.removeChannelFromList(channelID, 0, "Channel supprimé");
        }
    }

    /**
     * Notifier Data l'arrivée d'un message de chat
     *
     * @param msg       message reçu
     * @param channelID identifiant unique (UUID) du channel dans lequel le message à été reçu
     * @param response  Message auquel ce message à répondu
     */
    public void notifyReceiveMessage(Message msg, UUID channelID, Message response) {
        logger.log(Level.FINE, channelID + " has new message ");

        dataClient.receiveMessage(msg, channelID, response);
    }

    /**
     * Notifier Data un message de chat est supprimé
     *
     * @param message message supprimé
     * @param channelID identifiant unique (UUID) du channel
     * @param deleteByCreator (Boolean) si le message a supprimé par Createur
     */
    public void notifyDeletedMessage(Message message, UUID channelID, Boolean deleteByCreator) {
        // TODO INTEGRATION V3: verify with Data which method for notifying IHM Channel and which is for delete proprietary message
        dataClient.deleteMessage(message, null, deleteByCreator);
    }

    /**
     * Notifier Data un message de chat est liké
     *
     * @param channelId identifiant unique (UUID) du channel
     * @param message message liké
     * @param user [UserLite] Utilisateur like message
     */
    public void notifyLikedMessage(UUID channelId, Message message, UserLite user){
        dataClient.likeMessage(channelId, message, user);
    }

    /**
     * Notifier Data un message de chat est modifié
     *
     * @param msg ancien message
     * @param newMsg message modifié
     * @param channelID identifiant unique (UUID) du channel
     */
    public void notifyEditMessage(Message msg, Message newMsg, UUID channelID) {
        dataClient.editMessage(msg, newMsg, channelID);
    }

    /**
     * Avertit Data de l'ajout d'un nouvel admin
     *
     * @param channelID [UUID] Channel ou un admin est ajoute
     * @param user      [UserLite] Utilisateur devenant admin
     */
    public void notifyNewAdminAdded(UUID channelID, UserLite user) {
        logger.log(Level.FINE, "new admin " + user.getNickName() + " added to channel " + channelID);

        dataClient.newAdmin(user, channelID);
    }

    /**
     * Avertit Data de retirer d'un nouvel admin
     *
     * @param channelID [UUID] Channel ou un admin est ajoute
     * @param user      [UserLite] Utilisateur devenant admin
     */
    public void notifyAdminRemoved(UUID channelID, UserLite user) {
        logger.log(Level.FINE, "removed admin " + user.getNickName() + " from channel " + channelID);

        // TODO INTEGRATION V3: tell Data to add method to receive admin removal notification
    }

    /**
     * Avertit Data de changer nickname d'un utilisateur
     * @param user [UserLite] Utilisateur change nickname
     * @param channelId identifiant unique (UUID) du channel
     * @param name nouvel nickname (String)
     */
    public void notifyChangeNickName(UserLite user, UUID channelId, String name){
        dataClient.updateNickname(user, channelId, name);
    }

    /* ------------------------------------- Proprietary Channel handling ---------------------------------------*/

    /**
     * Notifier que la demande de se deconecter d'un channel a été accepté
     *
     * @param channelID  identifiant unique (UUID) du channel deconnecté
     * @param ownerID identifiant unique (UUID) de l'utilisateur qui est parti
     */
    public void removeAllJoinsPersonsToProprietaryChannel(UUID channelID, UUID ownerID) {
        dataClient.removeAllUserFromJoinedUserChannel(channelID, 0, "Owner disconnected");
    }

    /**
     * Demande Data d'ajouter un utilisateur a un channel proprietaire
     *
     * @param user      Utilisateur qui a rejoint le channel
     * @param channelID identifiant unique (UUID) du channel
     */
    public void addUserToProprietaryChannel(UserLite user, UUID channelID) {
        logger.log(Level.FINE, "Data add user " + user.getNickName() + " to proprietary channel " + channelID);
        dataClient.addUserToOwnedChannel(user, channelID);
    }

    /**
     * Informer Channel proprietaire qu'un utilisateur vient de se déconnecter sur le channel
     * @param channelID
     * @param userLite
     */
    public void requestLeaveChannel(UUID channelID, UserLite userLite) {
        dataClient.removeUserFromJoinedUserChannel(userLite, channelID, 0, "Leave");
    }

    /**
     * Demande Data d'ajouter un utilisateur a la liste invitée un channel proprietaire
     *
     * @param user      Utilisateur qui a rejoint le channel
     * @param channelID identifiant unique (UUID) du channel
     */
    public void inviteUserToProprietaryChannel(UserLite user, UUID channelID) {
        logger.log(Level.FINE, "Data invite user " + user.getNickName() + " to proprietary channel " + channelID);
        dataClient.inviteUserToOwnedChannel(user, channelID);
    }

    /**
     * Recupére l'historique d'un channel proprietaire
     * @param channelID
     * @return
     */
    public List<Message> requestHistory(UUID channelID) {
        return dataClient.getHistory(channelID);
    }

    /**
     * Déclencher Data de faire l'action de sauvegarde d'un message d'un channel proprietaire
     *
     * @param msg       message reçu
     * @param channelID identifiant unique (UUID) du channel dans lequel le message à été reçu
     * @param response  Message auquel ce message à répondu
     */
    public void saveMessage(Message msg, UUID channelID, Message response) {
        dataClient.saveMessageIntoHistory(msg, channelID, response);
    }

    /**
     * Déclencher Data de faire l'action de supprimer d'un message d'un channel proprietaire
     * @param message
     * @param channelID
     * @param deleteByCreator
     */
    public void deleteMessage(Message message, UUID channelID, Boolean deleteByCreator) {
        dataClient.saveDeletionIntoHistory(message, null, channelID);
    }

    /**
     * Déclencher Data de faire l'action de liker d'un message d'un channel proprietaire
     * @param channelId
     * @param msg
     * @param user
     */
    public void saveLike(UUID channelId, Message msg, UserLite user){
        dataClient.saveLikeIntoHistory(channelId, msg, user);
    }

    /**
     * Sauvegarde une edition de message sur le client
     * @param message ancien message
     * @param newMessage nouveau message
     * @param channelID channel concerne
     */
    public void saveEdit(Message message, Message newMessage, UUID channelID){
        dataClient.saveEditionIntoHistory(message, newMessage, channelID);
    }

    /**
     * Demande Owner d'ajouter un nouvel admin au channel proprietaire
     *
     * @param channelID [UUID] Channel ou un admin est ajoute
     * @param user      [UserLite] Utilisateur devenant admin
     */
    public void addAdminToProprietaryChannel(UUID channelID, UserLite user) {
        logger.log(Level.FINE, "request owner to add admin " + user.getNickName() + " to channel " + channelID);

        dataClient.saveNewAdminIntoHistory(user, channelID);
    }

    /**
     * Demande Owner d'ajouter un nouvel admin au channel proprietaire
     *
     * @param channelID [UUID] Channel ou un admin est ajoute
     * @param user      [UserLite] Utilisateur devenant admin
     */
    public void removeAdminFromProprietaryChannel(UUID channelID, UserLite user) {
        logger.log(Level.FINE, "request owner to add admin " + user.getNickName() + " to channel " + channelID);

        // TODO INTEGRATION V3: tell Data to add method to remove admin from proprietary channel
    }
}
