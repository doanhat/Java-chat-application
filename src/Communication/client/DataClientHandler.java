package Communication.client;

import Communication.common.ChatOperation;
import Communication.common.ChatPackage;
import common.interfaces.client.ICommunicationToData;
import common.shared_data.Message;
import common.shared_data.UserLite;

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
     * Notifier Data l'action de chat sur un channel
     * @param operation chat operation
     * @param chatPackage package de chat
     */
    public void notifyChat(ChatOperation operation, ChatPackage chatPackage) {
        logger.log(Level.FINE, chatPackage.channelID + " a nouvelle notification de chat: " + operation);

        switch (operation) {
            case SEND_MESSAGE:
                dataClient.receiveMessage(chatPackage.message, chatPackage.channelID, chatPackage.messageResponseTo);
                break;
            case EDIT_MESSAGE:
                dataClient.editMessage(chatPackage.message, chatPackage.editedMessage, chatPackage.channelID);
                break;
            case LIKE_MESSAGE:
                dataClient.likeMessage(chatPackage.channelID, chatPackage.message, chatPackage.sender);
                break;
            case DELETE_MESSAGE:
                dataClient.deleteMessage(chatPackage.message,
                                         chatPackage.channelID,
                                         chatPackage.sender.getId().equals(chatPackage.message.getAuthor().getId()));
                break;
            default:
                logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
        }
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
     * Sauvegarde l'action de chat sur channel proprietaire
     * @param operation
     * @param chatPackage
     */
    public void saveChat(ChatOperation operation, ChatPackage chatPackage) {
        switch (operation) {
            case SEND_MESSAGE:
                dataClient.saveMessageIntoHistory(chatPackage.message, chatPackage.channelID, chatPackage.messageResponseTo);
                break;
            case EDIT_MESSAGE:
                dataClient.saveEditionIntoHistory(chatPackage.message, chatPackage.editedMessage, chatPackage.channelID);
                break;
            case LIKE_MESSAGE:
                dataClient.saveLikeIntoHistory(chatPackage.channelID, chatPackage.message, chatPackage.sender);
                break;
            case DELETE_MESSAGE:
                dataClient.saveDeletionIntoHistory(chatPackage.message, null, chatPackage.channelID);
                break;
            default:
                logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
        }
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
