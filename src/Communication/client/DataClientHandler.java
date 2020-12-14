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
                dataClient.likeMessage(chatPackage.channelID, chatPackage.message, chatPackage.user);
                break;
            case DELETE_MESSAGE:
                dataClient.deleteMessage(chatPackage.message,
                                         chatPackage.channelID,
                                         chatPackage.user.getId().equals(chatPackage.message.getAuthor().getId()));
                break;
            case EDIT_NICKNAME:
                dataClient.updateNickname(chatPackage.user, chatPackage.channelID, chatPackage.nickname);
                break;
            case ADD_ADMIN:
                dataClient.newAdmin(chatPackage.user, chatPackage.channelID);
                break;
            case REMOVE_ADMIN:
                // TODO INTEGRATION V3: tell Data to add method to receive admin removal notification
                break;
            default:
                logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
        }
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
                dataClient.saveLikeIntoHistory(chatPackage.channelID, chatPackage.message, chatPackage.user);
                break;
            case DELETE_MESSAGE:
                dataClient.saveDeletionIntoHistory(chatPackage.message, null, chatPackage.channelID);
                break;
            case EDIT_NICKNAME:
                dataClient.saveNicknameIntoHistory(chatPackage.user, chatPackage.channelID, chatPackage.nickname);
                break;
            case ADD_ADMIN:
                dataClient.saveNewAdminIntoHistory(chatPackage.user, chatPackage.channelID);
                break;
            case REMOVE_ADMIN:
                // TODO INTEGRATION V3: tell Data to add method to remove admin from proprietary channel
                break;
            default:
                logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
        }
    }
}
