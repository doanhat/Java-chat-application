package Communication.client;

import Communication.common.ChannelOperation;
import Communication.common.InfoPackage;
import Communication.common.Parameters;
import Communication.messages.client_to_server.channel_access.proprietary_channels.LeavePropChannelMessage;
import Communication.messages.client_to_server.channel_access.proprietary_channels.QuitPropChannelMessage;
import Communication.messages.client_to_server.channel_access.shared_channels.LeaveSharedChannelMessage;
import Communication.messages.client_to_server.channel_access.QuitChannelMessage;
import Communication.messages.client_to_server.channel_modification.DeleteChannelMessage;
import Communication.messages.client_to_server.channel_modification.proprietary_channels.SendProprietaryChannelsMessage;
import Communication.messages.client_to_server.channel_modification.shared_channels.CreateSharedChannelMessage;
import Communication.messages.client_to_server.chat_action.ChatMessage;
import Communication.messages.client_to_server.channel_modification.GetHistoryMessage;
import Communication.messages.client_to_server.channel_access.SendInvitationMessage;
import Communication.messages.client_to_server.channel_access.proprietary_channels.AskToJoinPropMessage;
import Communication.messages.client_to_server.channel_access.shared_channels.AskToJoinSharedMessage;

import common.interfaces.client.*;
import common.shared_data.Channel;
import common.shared_data.ChannelType;
import common.shared_data.Message;
import common.shared_data.UserLite;

import java.util.*;

public class CommunicationClientInterface implements IDataToCommunication,
                                                     IIHMMainToCommunication,
                                                     IIHMChannelToCommunication {

    private final CommunicationClientController commController;
    private UserLite localUser;

    public CommunicationClientInterface(CommunicationClientController communicationClientController) {
        this.commController = communicationClientController;
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
    public void deleteChannel(UUID channelID) {
        commController.sendMessage(new DeleteChannelMessage(channelID, localUser));
    }

    /* ---------------------------- IIHMMainToCommunication interface implementations --------------------------------*/

    @Override
    public void setIP(String addressIP) {
        Parameters.SERVER_IP = addressIP;
    }

    @Override
    public void setPort(int port) {
        Parameters.PORT = port;
    }

    /**
     * Demande de deconnexion du client
     */
    public void disconnect() {
        commController.disconnect(localUser.getId());
        // TODO RECONNECTION APPLICATION maybe set localUser to null;
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

    @Override
    public void saveAvatarToServer(UserLite user, String encodedString) {
        //TODO Note Data : Appeler saveAvatarToServer(UserLite user, String avatarBase64) dans IServerCommunicationToData
    }

    @Override
    public String getAvatarPath(UserLite user) {
        //TODO Note Data : Appeler getAvatarPath(UserLite user) dans IServerCommunicationToData
        return null;
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
            commController.sendMessage(new SendInvitationMessage(guest, channel, message));
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

        InfoPackage infoPackage = new InfoPackage();
        infoPackage.user = user;
        infoPackage.channelID = channel.getId();

        this.commController.sendMessage(new ChatMessage(ChannelOperation.ADD_ADMIN, infoPackage));
    }

    @Override
    public void removeAdmin(UserLite user, Channel channel) {
        if (user == null || channel == null) {
            return;
        }

        InfoPackage infoPackage = new InfoPackage();
        infoPackage.user = user;
        infoPackage.channelID = channel.getId();

        this.commController.sendMessage(new ChatMessage(ChannelOperation.REMOVE_ADMIN, infoPackage));
    }

    /**
     * Demande de bannir un utilisateur d'un channel
     *
     * @param user        Utilisateur a bannir
     * @param duration    Durée du bannisement
     * @param explanation Chaine de caractere justifiant le ban
     **/
    public void banUserFromChannel(UserLite user, int duration, String explanation) {
        // TODO V4
    }

    /**
     * Envoie d'un message au serveur
     *
     * @param msg      Nouveau messsage a envoyer
     * @param channel  Channel sur lequel ont veut envoyer le message
     * @param response Message auquel le nouveau message repond sinon null
     **/
    public void sendMessage(Message msg, Channel channel, Message response) {
        if (msg == null || channel == null) {
            return;
        }

        InfoPackage infoPackage = new InfoPackage();
        infoPackage.user = localUser;
        infoPackage.message = msg;
        infoPackage.channelID = channel.getId();
        infoPackage.messageResponseTo = response;

        this.commController.sendMessage(new ChatMessage(ChannelOperation.SEND_MESSAGE, infoPackage));
    }

    /**
     * Envoie une demande d'édite au serveur
     *
     * @param msg     [Message] Message d'origine
     * @param newMsg [Message] Message modifier
     * @param channel [Channel] Channel du message a modifier
     **/
    public void editMessage(Message msg, Message newMsg, Channel channel) {
        if (msg == null || channel == null || newMsg == null) {
            return;
        }

        InfoPackage infoPackage = new InfoPackage();
        infoPackage.user = localUser;
        infoPackage.message = msg;
        infoPackage.channelID = channel.getId();
        infoPackage.editedMessage = newMsg;

        this.commController.sendMessage(new ChatMessage(ChannelOperation.EDIT_MESSAGE, infoPackage));
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

        InfoPackage infoPackage = new InfoPackage();
        infoPackage.user = user;
        infoPackage.message = msg;
        infoPackage.channelID = channel.getId();

        this.commController.sendMessage(new ChatMessage(ChannelOperation.LIKE_MESSAGE, infoPackage));
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

        InfoPackage infoPackage = new InfoPackage();
        infoPackage.user = user;
        infoPackage.message = msg;
        infoPackage.channelID = channel.getId();

        this.commController.sendMessage(new ChatMessage(ChannelOperation.DELETE_MESSAGE, infoPackage));
    }

    /**
     * Envoie l'information d'un changement de pseudo au serveur
     *
     * @param user        [UserLite] Utilisateur concerné
     * @param channel     [Channel] Channel ou le changement de pseudo à lieu
     * @param newNickname [String] Nouveau pseudo
     **/
    public void changeNickname(UserLite user, Channel channel, String newNickname) {
        if (channel == null || user == null || newNickname == null) {
            return;
        }

        InfoPackage infoPackage = new InfoPackage();
        infoPackage.user = user;
        infoPackage.nickname = newNickname;
        infoPackage.channelID = channel.getId();

        this.commController.sendMessage(new ChatMessage(ChannelOperation.EDIT_MESSAGE, infoPackage));
    }

    /**
     * Demande de quitter un channel au serveur
     *
     * @param channel [Channel] Channel que l'on veut quitter
     **/
    public void leaveChannel(Channel channel) {
        if (channel == null) {
            return;
        }

        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(new LeavePropChannelMessage(localUser, channel.getId(), channel.getCreator()));
        }
        else {
            commController.sendMessage(new LeaveSharedChannelMessage(localUser, channel.getId()));
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
            if (channel.getCreator().getId().equals(localUser.getId())) {
                commController.sendMessage(new SendProprietaryChannelsMessage(localUser, Collections.singletonList(channel)));
            }
            else {
                commController.sendMessage(new AskToJoinPropMessage(channel.getId(), localUser, channel.getCreator()));
            }
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
    public void quitChannel(Channel channel) {
        if (channel.getType() == ChannelType.OWNED) {
            if (channel.getCreator().getId().equals(localUser.getId())) {
                commController.sendMessage(new DeleteChannelMessage(channel.getId(), localUser));
            }
            else {
                commController.sendMessage(new QuitPropChannelMessage(localUser, channel.getId(), channel.getCreator()));
            }
        } else {
            commController.sendMessage(new QuitChannelMessage(localUser, channel.getId()));
        }
    }


}
