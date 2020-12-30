package Communication.client;

import Communication.common.ChannelOperation;
import Communication.common.info_packages.BanUserPackage;
import Communication.common.info_packages.ChatPackage;
import Communication.common.info_packages.InfoPackage;
import Communication.common.info_packages.UpdateChannelPackage;
import common.interfaces.client.ICommunicationToData;
import common.shared_data.Message;
import common.shared_data.UserLite;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe qui gère les intéractions de module Communication à module Data
 */
public class DataClientHandler {

    private final Logger               logger = Logger.getLogger(this.getClass().getName());
    private       ICommunicationToData dataClient;

    /**
     * Installer l'interfaces de Communication à Data
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
    public void notifyInvisibleChannels(List<UUID> channelIDs, String explanation) {
        for (UUID channelID : channelIDs) {
            dataClient.removeChannelFromList(channelID, 0, explanation);
        }
    }

    /**
     * Notifier Data l'action de chat sur un channel
     *
     * @param operation   chat operation
     * @param infoPackage package de chat
     */
    public void notifyChat(ChannelOperation operation, InfoPackage infoPackage) {
        logger.log(Level.FINE, infoPackage.channelID + " a nouvelle notification de chat: " + operation);

        switch (operation) {
            case SEND_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataClient.receiveMessage(castedPackage.message,
                                              castedPackage.channelID,
                                              castedPackage.messageResponseTo);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: SEND_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case EDIT_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataClient.editMessage(castedPackage.message, castedPackage.editedMessage, castedPackage.channelID);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: EDIT_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case LIKE_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataClient.likeMessage(castedPackage.channelID, castedPackage.message, castedPackage.user);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: LIKE_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case DELETE_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataClient.deleteMessage(castedPackage.message,
                                             castedPackage.channelID,
                                             castedPackage.user.getId()
                                                               .equals(castedPackage.message.getAuthor().getId()));
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: DELETE_MESSAGE contient mauvais ChatPackage");
                }

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
            case BAN_USER:
                if (infoPackage instanceof BanUserPackage) {
                    BanUserPackage castedPackage = (BanUserPackage) infoPackage;

                    dataClient.banUser(castedPackage.getUserToBan(),
                                       castedPackage.getEndDate(),
                                       castedPackage.isPermanent(),
                                       castedPackage.getExplanation(),
                                       castedPackage.channelID);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: BAN_USER contient mauvais BanUserPackage");
                }

                break;
            case UNBAN_USER:
                if (infoPackage instanceof BanUserPackage) {
                    BanUserPackage castedPackage = (BanUserPackage) infoPackage;

                    dataClient.unbannedUserToChannel(castedPackage.getUserToBan(), castedPackage.channelID);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: UNBAN_USER contient mauvais BanUserPackage");
                }

                break;
            case UPDATE_CHANNEL:
                if (infoPackage instanceof UpdateChannelPackage) {
                    UpdateChannelPackage castedPackage = (UpdateChannelPackage) infoPackage;

                    logger.log(Level.INFO, "UPDATE_CHANNEL: " + castedPackage);

                    dataClient.updateChannel(castedPackage.channelID, castedPackage.user.getId(),
                                             castedPackage.name, castedPackage.description, castedPackage.visibility);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: UPDATE_CHANNEL contient mauvais UpdateChannelPackage");
                }

                break;
            default:
                logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
        }
    }

    /* ------------------------------------- Proprietary Channel handling ---------------------------------------*/

    /**
     * Notifier que la demande de se deconecter d'un channel a été accepté
     *
     * @param channelID identifiant unique (UUID) du channel deconnecté
     * @param ownerID   identifiant unique (UUID) de l'utilisateur qui est parti
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
     *
     * @param channelID
     * @param userLite
     */
    public void requestLeaveChannel(UUID channelID, UserLite userLite) {
        dataClient.removeUserFromJoinedUserChannel(userLite, channelID, 0, "Leave");
    }

    /**
     * Informer Channel proprietaire qu'un utilisateur vient de se quitter un channel
     *
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
     *
     * @param channelID
     * @return
     */
    public List<Message> requestHistory(UUID channelID) {
        return dataClient.getHistory(channelID);
    }

    /**
     * Sauvegarde l'action de chat sur channel proprietaire
     *
     * @param operation
     * @param infoPackage
     */
    public void saveChat(ChannelOperation operation, InfoPackage infoPackage) {
        switch (operation) {
            case SEND_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataClient.saveMessageIntoHistory(castedPackage.message,
                                                      castedPackage.channelID,
                                                      castedPackage.messageResponseTo);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: SEND_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case EDIT_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataClient.saveEditionIntoHistory(castedPackage.message,
                                                      castedPackage.editedMessage,
                                                      castedPackage.channelID);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: EDIT_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case LIKE_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataClient.saveLikeIntoHistory(castedPackage.channelID, castedPackage.message, castedPackage.user);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: LIKE_MESSAGE contient mauvais ChatPackage");
                }

                break;
            case DELETE_MESSAGE:
                if (infoPackage instanceof ChatPackage) {
                    ChatPackage castedPackage = (ChatPackage) infoPackage;

                    dataClient.saveDeletionIntoHistory(castedPackage.message, castedPackage.channelID,
                                                       castedPackage.user.getId()
                                                                         .equals(castedPackage.message.getAuthor()
                                                                                                      .getId()));
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: DELETE_MESSAGE contient mauvais ChatPackage");
                }

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
            case BAN_USER:
                if (infoPackage instanceof BanUserPackage) {
                    BanUserPackage castedPackage = (BanUserPackage) infoPackage;

                    dataClient.banUserIntoHistory(castedPackage.getUserToBan(),
                                                  castedPackage.getEndDate(),
                                                  castedPackage.isPermanent(),
                                                  castedPackage.getExplanation(),
                                                  castedPackage.channelID);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: BAN_USER contient mauvais BanUserPackage");
                }

                break;
            case UNBAN_USER:
                if (infoPackage instanceof BanUserPackage) {
                    BanUserPackage castedPackage = (BanUserPackage) infoPackage;
                    dataClient.cancelBanOfUserIntoHistory(castedPackage.getUserToBan(), castedPackage.channelID);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: UNBAN_USER contient mauvais BanUserPackage");
                }

                break;
            case UPDATE_CHANNEL:
                if (infoPackage instanceof UpdateChannelPackage) {
                    UpdateChannelPackage castedPackage = (UpdateChannelPackage) infoPackage;

                    dataClient.updateChannelIntoHistory(castedPackage.channelID,
                                                        castedPackage.user.getId(),
                                                        castedPackage.name,
                                                        castedPackage.description,
                                                        castedPackage.visibility);
                }
                else {
                    logger.log(Level.SEVERE, "ChatMessage: UPDATE_CHANNEL contient mauvais UpdateChannelPackage");
                }

                break;
            default:
                logger.log(Level.WARNING, "ChatMessage: opetration inconnue");
        }
    }
}
