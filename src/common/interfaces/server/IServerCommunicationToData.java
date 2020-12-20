package common.interfaces.server;

import common.shared_data.Channel;
import common.shared_data.Message;
import common.shared_data.UserLite;
import common.shared_data.Visibility;
import java.util.List;
import java.util.UUID;

public interface IServerCommunicationToData {

    Channel requestChannelCreation(Channel channel,boolean isShared, boolean isPublic, UserLite owner);
    /**
     * Méthode pour faire la suppression d'un channel
     *
     * @param channelID l'identificateur du channel à supprimer
     * @param user l'utilisateur qui fait la demande de suppression
     * */
    boolean requestChannelRemoval(UUID channelID, UserLite user);

    /**
     * Méthode pour mettre à jour les informations d'un channel dans la liste des channels
     *
     * @param channelID l'identificateur du channel concerné
     * @param userID l'identificateur qui veut faire les changes sur le channel
     * @param name nouvel nom du channel, mettre à null si pas besoin de le changer
     * @param description nouvelle description du channel, mettre à null si pas besoin de la changer
     * @param visibility nouvelle visibilité du channel, mettre à null si pas besoin de la changer
     * */
    void updateChannel(UUID channelID, UUID userID, String name, String description, Visibility visibility);

    /**
     * Méthode pour ajouter un administrateur à la liste des administrateurs d'un channel
     *
     * @param channel le channel dans lequel l'administrateur sera ajouté
     * @param user l'utilisateur qui fera partie des administrateurs du channel
     * */
    void saveNewAdminIntoHistory(Channel channel, UserLite user);

    /**
     * Méthode pour kicker un utilisateur d'un channel. Ce méthode sera chargé de faire la création de l'objet
     * Kick correspondante et du traitement de l'utilisateur
     *
     * @param channel le channel concerné
     * @param user l'utilisateur qui sera kické du channel
     * @param duration nombre de minutes que l'utilisateur sera kické du channel. La valeur 0 correspondra à un Kick
     * permanent
     * @param reason description de la raison pour laquelle l'utilisateur à été kické du channel
     * */
    boolean banUserFromChannel(Channel channel, UserLite user, int duration, String reason);

    /**
     * Méthode pour annuler un kick avant la fin de sa duration
     *
     * @param channel le channel dans lequel à été fait le kick
     * @param user l'utilisateur qui a été kické du group et pour lequel le kick sera annulé
     * */
    boolean cancelUsersBanFromChannel(Channel channel, UserLite user);

    /**
     * Méthode pour poster un message dans un channel
     *
     * @param channel le channel dans lequel sera posté le message
     * @param ms l'objet message qui sera ajouté à la liste des messages d'un channel
     * @param response la réponse du message, si elle existe.
     * */
    void saveMessageIntoHistory(Channel channel, Message ms, Message response);

    /**
     * Méthode pour mettre à jour les informations d'un message posté dans un channel
     *
     * @param channel le channel dans lequel à été posté le message qui sera modifié
     * @param ms l'objet message qui contient déjà les modifications faites
     * */
    void editMessage(Channel channel, Message ms);

    /**
     * Méthode pour ajouter un utilisateur correspondante à un like pour un message
     *
     * @param channel le channel auquel il appartient le message concerné
     * @param user l'utilisateur qui à liké le message
     * */
    void saveLikeIntoHistory(Channel channel, Message ms, UserLite user);

    /**
     * Méthode pour supprimer un message de la liste de messages postés dans un channel
     *
     * @param channel le channel dans lequel est posté le message concerné
     * @param ms le message qui sera retiré du channel
     * */
    void saveRemovalMessageIntoHistory(Channel channel, Message ms, Boolean deletedByCreator);

    /**
     * Méthode pour retourner la liste des messages postés dans un channel
     *
     * @param channel le channel pour lequel on veut retourner ces messages
     * */
    List<Message> getHistory(Channel channel);

    /**
     * Méthode pour obtenir la liste des channels qui sont visibles pour un utilisateur
     *
     * @param user l'utilisateur pour lequel on va retourner les channels visibles
     * */
    List<Channel> getVisibleChannels(UserLite user);

    /**
     * Méthode pour créer un channel public partagé
     *
     * @param name le nom du channel
     * @param creator l'utilisateur créateur du channel
     * @param description la description du channel
     * */
    Channel createPublicSharedChannel(String name, UserLite creator, String description);

    /**
     * Méthode pour créer un channel privé proprietaire
     *
     * @param name le nom du channel
     * @param creator l'utilisateur créateur du channel
     * @param description la description du channel
     * */
    Channel createPrivateOwnedChannel(String name, UserLite creator, String description);

    /**
     * Méthode pour créer un channel public proprietaire
     *
     * @param name le nom du channel
     * @param creator l'utilisateur créateur du channel
     * @param description la description du channel
     * */
    Channel createPublicOwnedChannel(String name, UserLite creator, String description);

    /**
     * Méthode pour créer un channel privé partagé
     *
     * @param name le nom du channel
     * @param creator l'utilisateur créateur du channel
     * @param description la description du channel
     * */
    Channel createPrivateSharedChannel(String name, UserLite creator, String description);

    /**
     * Méthode pour faire la déconnexion d'un utilisateur
     *
     * @param userID l'identifiant de l'utilisateur qui va se déconnecter
     * */

    void disconnectUser(UUID userID);


    /**
     * Méthode pour faire la connexion d'un utilisateur
     * */
    void newConnection(UserLite user);

    /**
     * Méthode pour retourner la liste des utilisateurs connectés
     * */
    List<UserLite> getConnectedUsers();

    /**
     * Méthode pour mettre à jour le nickname d'un utilisateur dans un channel
     *
     * @param channel le channel dans lequel l'utilisateur va modifier son nickname
     * @param user l'utilisateur qui va changer son nickname dans le channel
     * @param newNickname le nouvel nickname de l'utilisateur dans le channel
     * */
    void updateNickname(Channel channel, UserLite user, String newNickname);

    /**
     * Méthode pour envoyer une invitation d'abonnement à un channel
     *
     * @param sender l'utilisateur qui envoie l'invitation
     * @param receiver l'utilisateur auquel sera envoyé l'invitation
     * @param message le message d'invitation
     * */
    void sendChannelInvitation(UserLite sender, UserLite receiver, String message);

    /**
     *  Méthode pour renvoyer la liste des messages d'un channel
     *
     * @param channelID l'identifiant du channel concerné
     * */
    List<Message> getChannelMessages(UUID channelID);

    /**
     * Méthode pour s'abonner à un channel
     *
     * @param channel le channel auquel l'utilisateur va s'abonner
     * @param user l'utilisateur qui va s'abonner au channel
     * */
    void joinChannel(UUID channel, UserLite user);

    /**
     * Méthode pour se désabonner d'un channel volontairement 
     *
     * @param channelID l'identificatuer du channel auquel l'utilisateur va se désabonner
     * @param user l'utilisateur qui va se désabonner
     * */
    void leaveChannel(UUID channelID, UserLite user);

    /**
     * Méthode pour ajouter un utilisateur à la liste des utilisateurs abonnés d'un channel
     *
     * @param channel le channel dans lequel l'utilisateur va s'abonner
     * @param user l'utilisateur à être ajouté à la liste
     * */
    void requestAddUser(Channel channel, UserLite user);

    /**
     * Méthode pour se retirer de la liste des authaurizedUsers d'un channel volontairement
     *
     * @param channelID l'identificatuer du channel auquel l'utilisateur va se désabonner
     * @param user l'utilisateur qui va se désabonner
     * */
    void quitChannel(UUID channelID, UserLite user);

    /**
     * Méthode qui renvoie l'adresse de l'utilisateur
     *
     * @param user l'utilisateur dont l'adresse doit être transmise
     * */
    Object getUserAddress(UserLite user);

    /**
     * Méthode pour vérifier les droits d'un utilisateur dans un channel
     *
     * @param channel le channel
     * @param user l'utilisateur dont les droits seront testés
     * */
    Boolean checkAuthorization(Channel channel, UserLite user);

    Channel getChannel(UUID channelID);

    List<Channel> disconnectOwnedChannel(UserLite owner);

    /**
     * Méthode pour retourner la liste des identieurs des channels auxquels appartient un utilisateur
     * @param userID l'identificateur de l'utilisateur
     * */
    List<UUID> getChannelsWhereUser(UUID userID);

    /**
     * Méthode pour retourner la liste des identieurs des channels dans lesquels un utilisateur est active
     * (liste différente à la liste de la méthode getChannelsWhereUser car les channels proprietaires peuvent
     * pas être actives)
     * @param userID l'identificateur de l'utilisateur
     * */
    List<UUID> getChannelsWhereUserActive(UUID userID);


    /**
     * Méthode pour retourner la liste des utilisateurs actives dans un channel
     * @param channelID l'identificateur du channel
     * */
    List<UserLite> getActiveUsersInChannel(UUID channelID);


    /**
     * Méthode pour ajouter la liste des channels proprietaires d'un utilisateur dans la liste des channels
     * dans le serveur
     * @param ownedChannels Liste des channels proprietaires à ajouter
     * @param ownerID l'identificateur de l'utilisateur proprietaire des channels
     * */
    void addOwnedChannelsToServerList(List<Channel> ownedChannels, UUID ownerID);

    /**
     * Envoyer une image encodée en string Base64 au server pour stocker
     *
     * @param user          utilisateur ayant l'image comme avatar
     * @param encodedString le string encodée en Base64
     */
    void saveAvatarToServer(UserLite user, String encodedString);

    /**
     * Récupérer le chemin vers l'avatar de l'utilisateur dans le serveur
     *
     * @param user utilisateur
     * @return
     */
    String getAvatarPath(UserLite user);

    /**
     *  Méthode pour retirer les droits d'administrateur d'un utilisateur dans un channel
     * @param channelID l'identificateur du channel
     * @param adminID l'identificateur de l'admin du channel
     *
     * */
    void requestRemoveAdmin(UUID channelID, UUID adminID);
}
