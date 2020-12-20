package common.interfaces.client;

import common.shared_data.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public interface IIHMChannelToCommunication
{
    /**
     * Transfert au serveur l'envoi d'un message d'invitation au serveur'envoi
     * d'une invitation a rejoindre un channel
     *
     * @param guest [UserLite] Utilisateur invité au channel
     * @param channel [Channel] Channel auquel guest est invité
     * @param message [String] Message d'invitation
     **/
    void sendInvite(UserLite guest, Channel channel, String message);

    /**
     * Demande l'envoie d'un message de nomination d'administrateur au serveur
     *
     * @param user [UserLite] Utilisateur devenant admin
     * @param channel [Channel] Channel qui doit recevoir les droitsChannel
     *                sur lequel on souhait donnée les droits d'admin
     **/
    void giveAdmin(UserLite user, Channel channel);

    /**
     * Permet de retirer un administrateur pour un channel.
     * @param user [UserLite] Utilisateur qui n'est plus un admin
     * @param channel [Channel] channel pour lequel on a retiré un admin
     */
    void removeAdmin(UserLite user, Channel channel);

    /**
     * Demande de bannir un utilisateur d'un channel
     *
     * @param userToKick Utilisateur a bannir
     * @param endDate Fin du bannisement
     * @param isPermanent bannisement définitif
     * @param explanation Chaine de caractere justifiant le ban
     * @param channelID ID du canal duquel l'utilisateur doit être banni.
     **/
    void banUserFromChannel(UserLite userToKick, LocalDate endDate, boolean isPermanent, String explanation, UUID channelID);

    /**
     * Annuler de bannir un utilisateur d'un channel
     * @param unKickedUser
     * @param channelID
     */
    void cancelBanOfUserFromChannel(UserLite unKickedUser, UUID channelID);

    /**
     * Envoie d'un message au serveur
     *
     * @param msg Nouveau messsage a envoyer
     * @param channel Channel sur lequel ont veut envoyer le message
     * @param reponse Message auquel le nouveau message repond sinon null
     **/
    void sendMessage(Message msg, Channel channel, Message reponse);

    /**
     * Envoie une demande d'édite au serveur
     *
     * @param msg [Message] Message d'origine
     * @param newMsg [Message] Message modifier
     * @param channel [Channel] Channel du message a modifier
     **/
    void editMessage(Message msg, Message newMsg, Channel channel);

    /**
     * Envoie une demande de like d'un message au serveur
     *
     * @param chan [Channel] Channel du message a like
     * @param msg [Message] Message à like
     * @param us [UserLite] Utilisateur ayant like
     **/
    void likeMessage(Channel chan, Message msg, UserLite us);

    /**
     * Envoie une demande de suppression de message au serveur
     *
     * @param msg [Message] Message a supprimer
     * @param channel [Channel] Channel du message a supprimer
     * @param user [UserLite] Utilisateur demandant la suppression
     **/
    void suppMessage(Message msg, Channel channel, UserLite user);

    /**
     * Envoie l'information d'un changement de pseudo au serveur
     *
     * @param user [UserLite] Utilisateur concerné
     * @param channel [Channel] Channel ou le changement de pseudo à lieu
     * @param newNickname [String] Nouveau pseudo
     **/
    void changeNickname(UserLite user, Channel channel, String newNickname);

    /**
     * Demande de déconnecter un channel au serveur
     *
     * @param channel [Channel] Channel que l'on veut quitter
     **/
    void leaveChannel(Channel channel);

    /**
     * Demande de d'etre connecté à channel au serveur
     *
     * @param channel [Channel] Channel que l'on veut rejoindre
     **/
    void askToJoin(Channel channel);


    /**
     * Recupere l'histoique d'un serveur donnée
     *
     * @param channel [Channel] Channel dont on demande l'historique
     **/
    void getHistory(Channel channel);

    /**
     * Remove user from authorized user list in Channel
     * @param channel
     */
    void quitChannel(Channel channel);

    /**
     * Transfert au serveur la demande de suppresion d'un channel
     *
     * @param channelID ID de l'objet à supprimer
     **/
    void deleteChannel(UUID channelID);

    /**
     * Demande de modification des caractériqtiques d'un channel
     *
     * @param channelID [UUID] ID du channel dont on demande la modification
     * @param userID [UUID] ID de l'utilisateur qui demande la modification
     * @param name [String] Nouveau nom du channel
     * @param description [String] Nouvelle description du channel
     * @param visibility [Visibility] Nouvelle visibilité du channel (public / privé)
     */
    void updateChannel(UUID channelID, UUID userID, String name, String description, Visibility visibility);
}
