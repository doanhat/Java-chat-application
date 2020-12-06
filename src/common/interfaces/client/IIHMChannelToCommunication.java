package common.interfaces.client;

import common.sharedData.*;
import java.util.*;

public interface IIHMChannelToCommunication
{
    /**
     * Transfert au serveur l'envoie d'un message d'invitation au serveur'envoi
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
     * @param us [UserLite] Utilisateur devenant admin
     * @param channel [Channel] Channel qui doit recevoir les droitsChannel
     *                sur lequel on souhait donnée les droits d'admin
     **/
    void giveAdmin(UserLite us, Channel channel);
    /**
     * Demande de bannir un utilisateur d'un channel
     *
     * @param user Utilisateur a bannir
     * @param duration Durée du bannisement
     * @param explanation Chaine de caractere justifiant le ban
     **/
    void banUserFromChannel(UserLite user, int duration, String explanation);

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
     * @param new_msg [Message] Message modifier
     * @param channel [Channel] Channel du message a modifier
     **/
    void editMessage(Message msg, Message new_msg, Channel channel);

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
     * Demande de quitter un channel au serveur
     *
     * @param user [UserLite] Utilisateur concerné
     * @param channel [Channel] Channel que l'on veut quitter
     **/
    void leaveChannel(UserLite user, Channel channel);

    /**
     * Demande de rejoindre channel au serveur
     *
     * @param channel [Channel] Channel que l'on veut rejoindre
     **/
    void askToJoin(Channel channel);

    /**
     * Recupere l'histoique d'un serveur donnée
     *
     * @param channelId [Channel] Channel dont on demande l'historique
     * @return List<Message> Liste des messages qui compose l'historique
     **/
    List<Message> getChannelHistory(UUID channelId);

    /**
     * Signale que le client a fermé l'onglet du channel d'ID channelId.
     * Cela signifie qu'il s'est déconnecté du channel mais ne l'a pas quitté pour autant.
     * @param channelId ID du channel fermé
     * @param user user ayant quitté le channel
     */
    void closeChannel(UUID channelId);

}
