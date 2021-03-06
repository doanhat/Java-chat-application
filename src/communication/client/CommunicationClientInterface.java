package communication.client;

import communication.common.ChannelAccessRequest;
import communication.common.ChannelOperation;
import communication.common.Parameters;
import communication.common.info_packages.BanUserPackage;
import communication.common.info_packages.ChatPackage;
import communication.common.info_packages.InfoPackage;
import communication.common.info_packages.UpdateChannelPackage;
import communication.messages.client_to_server.channel_access.ChannelAccessRequestMessage;
import communication.messages.client_to_server.channel_modification.DeleteChannelMessage;
import communication.messages.client_to_server.channel_modification.GetChannelUsersMessage;
import communication.messages.client_to_server.channel_modification.GetHistoryMessage;
import communication.messages.client_to_server.channel_modification.proprietary_channels.SendProprietaryChannelsMessage;
import communication.messages.client_to_server.channel_modification.shared_channels.CreateSharedChannelMessage;
import communication.messages.client_to_server.channel_operation.ChannelOperationMessage;
import communication.messages.client_to_server.connection.AvatarMessage;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IIHMChannelToCommunication;
import common.interfaces.client.IIHMMainToCommunication;
import common.shared_data.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

public class CommunicationClientInterface implements IDataToCommunication,
                                                     IIHMMainToCommunication,
                                                     IIHMChannelToCommunication {

    private final CommunicationClientController commController;
    private       UserLite                      localUser;

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

    /* ---------------------------- IIHMMainToCommunication interface implementations --------------------------------*/

    @Override
    public String getIP() {
        return Parameters.SERVER_IP;
    }

    @Override
    public void setIP(String addressIP) {
        Parameters.SERVER_IP = addressIP;
    }

    @Override
    public int getPort() {
        return Parameters.PORT;
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
        if (user == null || encodedString == null) {
            return;
        }

        commController.sendMessage(new AvatarMessage(AvatarMessage.Operation.PUT, localUser, user, encodedString));
    }

    @Override
    public void getAvatarPath(UserLite user) {
        if (user == null) {
            return;
        }

        commController.sendMessage(new AvatarMessage(AvatarMessage.Operation.GET, localUser, user));
    }

    public UserLite getLocalUser() {
        return localUser;
    }


    @Override
    public void getConnectedUsers(UUID channelID) {
        if (channelID != null) {
            commController.sendMessage(new GetChannelUsersMessage(channelID, localUser));
        }
    }

    /* -------------------------- IIHMChannelToCommunication interface implementations -------------------------------*/

    /**
     * Transfert au serveur l'envoi d'un message d'invitation au serveur'envoi
     * d'une invitation a rejoindre un channel
     *
     * @param guest   [UserLite] Utilisateur invité au channel
     * @param channel [Channel] Channel auquel guest est invité
     * @param message [String] Message d'invitation
     **/
    public void sendInvite(UserLite guest, Channel channel, String message) {
        if (guest == null || channel == null || message == null) {
            return;
        }

        // check if local user has the right to invite
        if (localUser.getId().equals(channel.getCreator().getId()) || channel.userIsAdmin(localUser.getId())) {
            commController.sendMessage(new ChannelAccessRequestMessage(ChannelAccessRequest.INVITE,
                                                                       channel.getId(),
                                                                       guest));
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

        infoPackage.user        = user;
        infoPackage.channelID   = channel.getId();

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.ADD_ADMIN, infoPackage));
    }

    @Override
    public void removeAdmin(UserLite user, Channel channel) {
        if (user == null || channel == null) {
            return;
        }

        InfoPackage infoPackage = new InfoPackage();

        infoPackage.user        = user;
        infoPackage.channelID   = channel.getId();

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.REMOVE_ADMIN, infoPackage));
    }

    /**
     * Demande de bannir un utilisateur d'un channel
     *
     * @param userToKick  Utilisateur a bannir
     * @param endDate     Fin du bannisement
     * @param isPermanent bannisement définitif
     * @param explanation Chaine de caractere justifiant le ban
     * @param channelID   ID du canal duquel l'utilisateur doit être banni.
     **/
    @Override
    public void banUserFromChannel(UserLite userToKick,
                                   LocalDate endDate,
                                   boolean isPermanent,
                                   String explanation,
                                   UUID channelID) {
        if (userToKick == null || channelID == null) {
            return;
        }

        BanUserPackage banUserPackage = new BanUserPackage();

        banUserPackage.user = localUser;
        banUserPackage.setUserToBan(userToKick);
        banUserPackage.channelID = channelID;
        banUserPackage.setEndDate(endDate);
        banUserPackage.setPermanent(isPermanent);
        banUserPackage.setExplanation(explanation);

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.BAN_USER, banUserPackage));
    }

    /**
     * Annuler de bannir un utilisateur d'un channel
     *
     * @param unKickedUser User à annuler le bannir
     * @param channelID ID du channel
     */
    @Override
    public void cancelBanOfUserFromChannel(UserLite unKickedUser, UUID channelID) {
        if (unKickedUser == null || channelID == null) {
            return;
        }

        BanUserPackage banUserPackage = new BanUserPackage();

        banUserPackage.user = localUser;
        banUserPackage.setUserToBan(unKickedUser);
        banUserPackage.channelID = channelID;

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.UNBAN_USER, banUserPackage));
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

        ChatPackage chatPackage       = new ChatPackage();

        chatPackage.user              = localUser;
        chatPackage.message           = msg;
        chatPackage.channelID         = channel.getId();
        chatPackage.messageResponseTo = response;

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.SEND_MESSAGE, chatPackage));
    }

    /**
     * Envoie une demande d'édite au serveur
     *
     * @param msg     [Message] Message d'origine
     * @param newMsg  [Message] Message modifier
     * @param channel [Channel] Channel du message a modifier
     **/
    public void editMessage(Message msg, Message newMsg, Channel channel) {
        if (msg == null || channel == null || newMsg == null) {
            return;
        }

        ChatPackage chatPackage   = new ChatPackage();

        chatPackage.user          = localUser;
        chatPackage.message       = msg;
        chatPackage.channelID     = channel.getId();
        chatPackage.editedMessage = newMsg;

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.EDIT_MESSAGE, chatPackage));
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

        ChatPackage chatPackage = new ChatPackage();

        chatPackage.user        = user;
        chatPackage.message     = msg;
        chatPackage.channelID   = channel.getId();

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.LIKE_MESSAGE, chatPackage));
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

        ChatPackage chatPackage = new ChatPackage();

        chatPackage.user        = user;
        chatPackage.message     = msg;
        chatPackage.channelID   = channel.getId();

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.DELETE_MESSAGE, chatPackage));
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

        infoPackage.user        = user;
        infoPackage.nickname    = newNickname;
        infoPackage.channelID   = channel.getId();

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.EDIT_NICKNAME, infoPackage));
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

        commController.sendMessage(new ChannelAccessRequestMessage(ChannelAccessRequest.LEAVE,
                                                                   channel.getId(),
                                                                   localUser));
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

        if (channel.getType() == ChannelType.OWNED && channel.getCreator().getId().equals(localUser.getId())) {
            commController.sendMessage(new SendProprietaryChannelsMessage(localUser,
                                                                          Collections.singletonList(channel)));
        }
        else {
            commController.sendMessage(new ChannelAccessRequestMessage(ChannelAccessRequest.JOIN,
                                                                       channel.getId(),
                                                                       localUser));
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

    /**
     * Transfere au serveur la demande de suppresion d'un channel
     *
     * @param channelID ID de l'objet à supprimer
     **/
    @Override
    public void deleteChannel(UUID channelID) {
        commController.sendMessage(new DeleteChannelMessage(channelID, localUser));
    }

    /**
     * Demande de de quitter définitivement d'un channel
     *
     * @param channel [Channel] channel dont on quitte
     */
    @Override
    public void quitChannel(Channel channel) {
        if (channel.getType() == ChannelType.OWNED && channel.getCreator().getId().equals(localUser.getId())) {
            commController.sendMessage(new DeleteChannelMessage(channel.getId(), localUser));
        }
        else {
            commController.sendMessage(new ChannelAccessRequestMessage(ChannelAccessRequest.QUIT,
                                                                       channel.getId(),
                                                                       localUser));
        }
    }

    /**
     * Demande de modification des caractériqtiques d'un channel
     *
     * @param channelID   [UUID] ID du channel dont on demande la modification
     * @param name        [String] Nouveau nom du channel
     * @param description [String] Nouvelle description du channel
     * @param visibility  [Visibility] Nouvelle visibilité du channel (public / privé)
     */
    public void updateChannel(UUID channelID, String name, String description, Visibility visibility) {
        if (channelID == null) {
            return;
        }

        UpdateChannelPackage updatePackage = new UpdateChannelPackage();

        updatePackage.user        = localUser;
        updatePackage.name        = name;
        updatePackage.description = description;
        updatePackage.visibility  = visibility;
        updatePackage.channelID   = channelID;

        this.commController.sendMessage(new ChannelOperationMessage(ChannelOperation.UPDATE_CHANNEL, updatePackage));
    }
}
