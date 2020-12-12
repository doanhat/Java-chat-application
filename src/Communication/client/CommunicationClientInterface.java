package Communication.client;

import Communication.common.Parameters;
import Communication.messages.client_to_server.channel_access.proprietary_channels.LeavePropChannelMessage;
import Communication.messages.client_to_server.channel_access.proprietary_channels.RemoveAdminPropMessage;
import Communication.messages.client_to_server.channel_access.shared_channels.LeaveSharedChannelMessage;
import Communication.messages.client_to_server.channel_access.shared_channels.RemoveAdminSharedMessage;
import Communication.messages.client_to_server.channel_modification.DeleteChannelMessage;
import Communication.messages.client_to_server.channel_modification.proprietary_channels.SendProprietaryChannelsMessage;
import Communication.messages.client_to_server.channel_modification.shared_channels.CreateSharedChannelMessage;
import Communication.messages.client_to_server.chat_action.SendMessageMessage;
import Communication.messages.client_to_server.channel_modification.GetHistoryMessage;
import Communication.messages.client_to_server.channel_access.proprietary_channels.AddAdminPropMessage;
import Communication.messages.client_to_server.channel_access.SendInvitationMessage;
import Communication.messages.client_to_server.channel_access.shared_channels.AddAdminSharedMessage;
import Communication.messages.client_to_server.channel_access.proprietary_channels.AskToJoinPropMessage;
import Communication.messages.client_to_server.channel_access.shared_channels.AskToJoinSharedMessage;

import Communication.messages.client_to_server.chat_action.proprietary_channels.DeleteMessagePropMessage;
import Communication.messages.client_to_server.chat_action.proprietary_channels.EditMessagePropMessage;
import Communication.messages.client_to_server.chat_action.shared_channels.EditMessageSharedMessage;
import Communication.messages.client_to_server.chat_action.proprietary_channels.SaveLikeMessageProp;
import Communication.messages.client_to_server.chat_action.shared_channels.DeleteMessageSharedMessage;
import Communication.messages.client_to_server.chat_action.shared_channels.SaveLikeMessageShared;
import common.interfaces.client.*;
import common.sharedData.Channel;
import common.sharedData.ChannelType;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.io.IOException;
import java.util.*;

public class CommunicationClientInterface implements IDataToCommunication,
                                                     IIHMMainToCommunication,
                                                     IIHMChannelToCommunication {

    private final CommunicationClientController commController;
    private UserLite localUser;

    public CommunicationClientInterface(CommunicationClientController CommunicationClientController) {
        this.commController = CommunicationClientController;
    }

    /* ---------------------------- IDataToCommunication interface implementations -----------------------------------*/

    /**
     * Connecte le client pour l'utilisateur passé en paramètre au serveur
     *
     * @param user utilisateur à connecter
     */
    @Override
    public void userConnect(UserLite user) {
        this.localUser = user;
        this.commController.start(Parameters.SERVER_IP, Parameters.PORT, user);
    }

    /**
     * Transfere au serveur la demande de suppresion d'un channel
     *
     * @param channelID ID de l'objet à supprimer
     * @implNote
     **/
    @Override
    public void delete(UUID channelID) {
        commController.sendMessage(new DeleteChannelMessage(channelID, localUser));
    }

    /* ---------------------------- IIHMMainToCommunication interface implementations --------------------------------*/

    /**
     * Demande de deconnexion du client
     */
    public void disconnect() {
        commController.disconnect(localUser.getId());
    }

    /**
     * Demande la creation d'un nouveau channel au serveur
     *
     * @param channel  [Channel] Objet channel a crée sur le serveur
     * @param isShared [Boolean] Si le channel est partagé ou non
     * @param isPublic [Boolean] Si le channel est publique ou non
     * @param owner    [UserLite] Information sur le proprietaire du channel si c'est un channel privé
     **/
    public void createChannel(Channel channel, Boolean isShared, Boolean isPublic, UserLite owner) {
        this.commController.sendMessage(new CreateSharedChannelMessage(owner, channel, isShared, isPublic));
    }

    /* -------------------------- IIHMChannelToCommunication interface implementations -------------------------------*/

    /**
     * Transfert au serveur l'envoi d'un message d'invitation au serveur'envoi
     * d'une invitation a rejoindre un channel
     *
     * @param guest [UserLite] Utilisateur invité au channel
     * @param channel [Channel] Channel auquel guest est invité
     * @param message [String] Message d'invitation
     **/
    public void sendInvite(UserLite guest, Channel channel, String message) {
        if (guest == null || channel == null || message == null) {
            return;
        }

        // check if local user has the right to invite
        if (localUser == channel.getCreator() || channel.userIsAdmin(localUser.getId())) {
            // TODO: Verify if invitation only makes a private channel become visible or add user directly to channel
            commController.sendMessage(new SendInvitationMessage(guest, channel, message));
        /*
            if (channel.getType() == ChannelType.OWNED) {
                commController.sendMessage(new AskToJoinPropMessage(channel.getId(), guest, channel.getCreator()));
            }
            else {
                commController.sendMessage(new AskToJoinSharedMessage(channel.getId(), guest));
            }
        */
        }
    }

    /**
     * Demande l'envoie d'un message de nomination d'administrateur au serveur
     *
     * @param user    [UserLite] Utilisateur devenant admin
     * @param channel [Channel] Channel qui doit recevoir les droitsChannel
     *                sur lequel on souhait donnée les droits d'admin
     **/
    public void giveAdmin(UserLite user, Channel channel) {
        if (user == null || channel == null) {
            return;
        }

        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(new AddAdminPropMessage(user, channel));
        }
        else {
            commController.sendMessage(new AddAdminSharedMessage(user, channel));
        }
    }

    @Override
    public void removeAdmin(UserLite user, Channel channel) {
        if (user == null || channel == null) {
            return;
        }

        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(new RemoveAdminPropMessage(user, channel));
        }
        else {
            commController.sendMessage(new RemoveAdminSharedMessage(user, channel));
        }
    }

    /**
     * Demande de bannir un utilisateur d'un channel
     *
     * @param user        Utilisateur a bannir
     * @param duration    Durée du bannisement
     * @param explanation Chaine de caractere justifiant le ban
     **/
    public void banUserFromChannel(UserLite user, int duration, String explanation) {
        // TODO V3
    }

    /**
     * Envoie d'un message au serveur
     *
     * @param msg      Nouveau messsage a envoyer
     * @param channel  Channel sur lequel ont veut envoyer le message
     * @param response Message auquel le nouveau message repond sinon null
     **/
    public void sendMessage(Message msg, Channel channel, Message response) {
        this.commController.sendMessage(new SendMessageMessage(msg, channel.getId(), response));
    }

    /**
     * Envoie une demande d'édite au serveur
     *
     * @param msg     [Message] Message d'origine
     * @param new_msg [Message] Message modifier
     * @param channel [Channel] Channel du message a modifier
     **/
    public void editMessage(Message msg, Message new_msg, Channel channel) {
        if (msg == null || channel == null || new_msg == null) {
            return;
        }
        if (channel.getType() == ChannelType.OWNED) {
            this.commController.sendMessage(new EditMessagePropMessage(msg, new_msg, channel.getId(), channel.getCreator()));
        }
        else {
            this.commController.sendMessage(new EditMessageSharedMessage(msg, new_msg, channel.getId()));
        }
    }

    /**
     * Envoie une demande de like d'un message au serveur
     *
     * @param channel [Channel] Channel du message a like
     * @param msg     [Message] Message à like
     * @param user    [UserLite] Utilisateur ayant like
     **/
    public void likeMessage(Channel channel, Message msg, UserLite user) {
        if (msg == null || channel == null || user == null) {
            return;
        }
        if (channel.getType() == ChannelType.OWNED) {
            this.commController.sendMessage(new SaveLikeMessageProp(channel, msg, user));
        }
        else {
            this.commController.sendMessage(new SaveLikeMessageShared(channel.getId(), msg, user));
        }
    }

    /**
     * Envoie une demande de suppression de message au serveur
     *
     * @param msg     [Message] Message a supprimer
     * @param channel [Channel] Channel du message a supprimer
     * @param user    [UserLite] Utilisateur demandant la suppression
     **/
    public void suppMessage(Message msg, Channel channel, UserLite user) {
        if (msg == null || channel == null || user == null) {
            return;
        }

        if (channel.getType() == ChannelType.OWNED) {
            this.commController.sendMessage(new DeleteMessagePropMessage(channel, msg, user.getId() == msg.getAuthor().getId()));
        }
        else {
            this.commController.sendMessage(new DeleteMessageSharedMessage(channel, msg, user.getId() == msg.getAuthor().getId()));
        }
    }

    /**
     * Envoie l'information d'un changement de pseudo au serveur
     *
     * @param user        [UserLite] Utilisateur concerné
     * @param channel     [Channel] Channel ou le changement de pseudo à lieu
     * @param newNickname [String] Nouveau pseudo
     **/
    public void changeNickname(UserLite user, Channel channel, String newNickname) {
        // TODO V2
    }

    /**
     * Demande de quitter un channel au serveur
     *
     * @param user    [UserLite] Utilisateur concerné
     * @param channel [Channel] Channel que l'on veut quitter
     **/
    public void leaveChannel(UserLite user, Channel channel) {
        if (channel == null || user == null) {
            return;
        }

        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(new LeavePropChannelMessage(user, channel.getId(), channel.getCreator()));
        }
        else {
            commController.sendMessage(new LeaveSharedChannelMessage(user, channel.getId()));
        }
    }

    /**
     * Demande de rejoindre channel au serveur
     *
     * @param channel [Channel] Channel que l'on veut rejoindre
     **/
    public void askToJoin(Channel channel) {
        if (channel == null) {
            return;
        }

        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(new AskToJoinPropMessage(channel.getId(), localUser, channel.getCreator()));
        }
        else {
            commController.sendMessage(new AskToJoinSharedMessage(channel.getId(), localUser));
        }
    }

    /**
     * Recupere l'histoique d'un serveur donnée
     *
     * @param channel [Channel] Channel dont on demande l'historique
     **/
    public void getHistory(Channel channel) {
        if (channel != null) {
            commController.sendMessage(new GetHistoryMessage(channel.getId(), localUser));
        }
    }

    @Override
    public void closeChannel(UUID channelID) {

    }

    @Override
    public void sendProprietaryChannels(List<Channel> channels) {
        commController.sendMessage(new SendProprietaryChannelsMessage(localUser, channels));
    }

    @Override
    public void sendProprietaryChannel(Channel channel) {
        commController.sendMessage(new SendProprietaryChannelsMessage(localUser, Collections.singletonList(channel)));
    }
}
