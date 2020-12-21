package Communication.client;

import Communication.common.ChannelOperation;
import Communication.common.InfoPackage;
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
     * @param infoPackage package de chat
     */
    public void notifyChat(ChannelOperation operation, InfoPackage infoPackage) {
        logger.log(Level.FINE, infoPackage.channelID + " a nouvelle notification de chat: " + operation);
        System.out.println(infoPackage.channelID + " a nouvelle notification de chat: " + operation);
        switch (operation) {
            case SEND_MESSAGE:
                dataClient.receiveMessage(infoPackage.message, infoPackage.channelID, infoPackage.messageResponseTo);
                break;
            case EDIT_MESSAGE:
                dataClient.editMessage(infoPackage.message, infoPackage.editedMessage, infoPackage.channelID);
                break;
            case LIKE_MESSAGE:
                dataClient.likeMessage(infoPackage.channelID, infoPackage.message, infoPackage.user);
                break;
            case DELETE_MESSAGE:
                dataClient.deleteMessage(infoPackage.message,
                                         infoPackage.channelID,
                                         infoPackage.user.getId().equals(infoPackage.message.getAuthor().getId()));
                break;
            case EDIT_NICKNAME:
                dataClient.updateNickname(infoPackage.user, infoPackage.channelID, infoPackage.nickname);
                break;
            case ADD_ADMIN:
                dataClient.newAdmin(infoPackage.user, infoPackage.channelID);
                break;
            case REMOVE_ADMIN:
                dataClient.requestRemoveAdmin(infoPackage.channelID, infoPackage.user);
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
     * Informer Channel proprietaire qu'un utilisateur vient de se quitter un channel
     * @param channelID
     * @param userLite
     */
    public void requestQuitChannel(UUID channelID, UserLite userLite) {
        dataClient.removeUserFromAuthorizedUserChannel(userLite, channelID);
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
     * @param infoPackage
     */
    public void saveChat(ChannelOperation operation, InfoPackage infoPackage) {
        switch (operation) {
            case SEND_MESSAGE:
                dataClient.saveMessageIntoHistory(infoPackage.message, infoPackage.channelID, infoPackage.messageResponseTo);
                break;
            case EDIT_MESSAGE:
                dataClient.saveEditionIntoHistory(infoPackage.message, infoPackage.editedMessage, infoPackage.channelID);
                break;
            case LIKE_MESSAGE:
                dataClient.saveLikeIntoHistory(infoPackage.channelID, infoPackage.message, infoPackage.user);
                break;
            case DELETE_MESSAGE:
                // TODO Integ V3 Data : il manque une paramètre
                //dataClient.saveDeletionIntoHistory(chatPackage.message, null, chatPackage.channelID);
                break;
            case EDIT_NICKNAME:
                dataClient.saveNicknameIntoHistory(infoPackage.user, infoPackage.channelID, infoPackage.nickname);
                break;
            case ADD_ADMIN:
                dataClient.saveNewAdminIntoHistory(infoPackage.user, infoPackage.channelID);
                break;
            case REMOVE_ADMIN:
                dataClient.requestRemoveAdmin(infoPackage.channelID, infoPackage.user);
                break;
            default:
                logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
        }
    }
}
