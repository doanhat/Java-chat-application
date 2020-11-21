package Communication.client;

import common.interfaces.client.IIHMChannelToCommunication;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;
import Communication.messages.client_to_server.SendMessageMessage;

import java.util.List;

public class IHMChannelToCommunication implements IIHMChannelToCommunication {

    private CommunicationClientController commController;

    public IHMChannelToCommunication(CommunicationClientController commController) {
        this.commController = commController;
    }

    /**
     * Transfert au serveur l'envoie d'un message d'invitation au serveur'envoi
     * d'une invitation a rejoindre un channel
     *
     * @param sender [UserLite] Utilisateur qui crée l'invitation
     * @param receiver [UserLite] Utilisateur qui doit recevoir l'invitation
     * @param message [Message] Message d'invitation
     **/
    public void sendInvite(UserLite sender, UserLite receiver, Message message){

    }

    /**
     * Demande l'envoie d'un message de nomination d'administrateur au serveur
     *
     * @param us [UserLite] Utilisateur devenant admin
     * @param channel [Channel] Channel qui doit recevoir les droitsChannel
     *                sur lequel on souhait donnée les droits d'admin
     **/
    public void giveAdmin(UserLite us, Channel channel){

    }
    /**
     * Demande de bannir un utilisateur d'un channel
     *
     * @param user Utilisateur a bannir
     * @param duration Durée du bannisement
     * @param explanation Chaine de caractere justifiant le ban
     **/
    public void banUserFromChannel(UserLite user, int duration, String explanation){

    }

    /**
     * Envoie d'un message au serveur
     *
     * @param msg Nouveau messsage a envoyer
     * @param channel Channel sur lequel ont veut envoyer le message
     * @param response Message auquel le nouveau message repond sinon null
     **/
    public void sendMessage(Message msg, Channel channel, Message response){
        //TODO V1
        this.commController.sendMessage(new SendMessageMessage(msg, channel.getId(), response));
    }

    /**
     * Envoie une demande d'édite au serveur
     *
     * @param msg [Message] Message d'origine
     * @param new_msg [Message] Message modifier
     * @param channel [Channel] Channel du message a modifier
     **/
    public void editMessage(Message msg, Message new_msg, Channel channel){

    }

    /**
     * Envoie une demande de like d'un message au serveur
     *
     * @param chan [Channel] Channel du message a like
     * @param msg [Message] Message à like
     * @param us [UserLite] Utilisateur ayant like
     **/
    public void likeMessage(Channel chan, Message msg, UserLite us){

    }

    /**
     * Envoie une demande de suppression de message au serveur
     *
     * @param msg [Message] Message a supprimer
     * @param channel [Channel] Channel du message a supprimer
     * @param user [UserLite] Utilisateur demandant la suppression
     **/
    public void suppMessage(Message msg, Channel channel, UserLite user){

    }

    /**
     * Envoie l'information d'un changement de pseudo au serveur
     *
     * @param user [UserLite] Utilisateur concerné
     * @param channel [Channel] Channel ou le changement de pseudo à lieu
     * @param newNickname [String] Nouveau pseudo
     **/
    public void changeNickname(UserLite user, Channel channel, String newNickname){

    }

    /**
     * Demande de quitter un channel au serveur
     *
     * @param user [UserLite] Utilisateur concerné
     * @param channel [Channel] Channel que l'on veut quitter
     **/
    public void leaveChannel(UserLite user, Channel channel){

    }

    /**
     * Demande de rejoindre channel au serveur
     *
     * @param channel [Channel] Channel que l'on veut rejoindre
     **/
    public void askToJoin(Channel channel){

    }

    /**
     * Recupere l'histoique d'un serveur donnée
     *
     * @param channel [Channel] Channel dont on demande l'historique
     * @return List<Message> Liste des messages qui compose l'historique
     **/
    public List<Message> getHistory(Channel channel){
        return null;
    }
}
