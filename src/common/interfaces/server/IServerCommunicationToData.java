package common.interfaces.server;

import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;
import java.util.List;

public interface IServerCommunicationToData {

    /**
     * Méthode pour faire la suppression d'un channel
     *
     * @param channel le channel à être supprime
     * @param user l'utilisateur qui fait la demande de suppression
     * */
    List<Channel> requestChannelRemoval(Channel channel, UserLite user);

    /**
     * Méthode pour ajouter un channel à la liste des channels actifs du serveur
     *
     * @param channel le channel à être ajouté
     * @param typeOwner indique si le channel est de type propriétaire
     * @param typePublic indique si le channel est de type public
     * @param user l'utilisateur qui fait la demande
     * */
    List<Channel> requestChannelCreation(Channel channel, Boolean typeOwner, Boolean typePublic, UserLite user);

    /**
     * Méthode pour mettre à jour les informations d'un channel dans la liste des channels
     *
     * @param channel le channel concerné avec les modifications déjà faites
     * */
    List<UserLite> updateChannel(Channel channel);

    /**
     * Méthode pour ajouter un utilisateur à la liste des utilisateurs abonnés d'un channel
     *
     * @param channel le channel dans lequel l'utilisateur va s'abonner
     * @param user l'utilisateur à être ajouté à la liste
     * */
    void requestAddUser(Channel channel, UserLite user);

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
     * Méthode pour faire la déconnexion d'un utilisateur
     *
     * @param user l'utilisateur qui va se déconnecter
     * */

    List<UserLite> disconnectUser(UserLite user);

    /**
     * Méthode pour faire la connexion d'un utilisateur
     * */
    List<UserLite> newConnection(UserLite user);

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
     * Méthode pour s'abonner à un channel
     *
     * @param channel le channel auquel l'utilisateur va s'abonner
     * @param user l'utilisateur qui va s'abonner au channel
     * */
    List<UserLite> joinChannel(Channel channel, UserLite user);

    /**
     * Méthode pour se désabonner d'un channel
     *
     * @param channel le channel duquel l'utilisateur va se désabonner
     * @param user l'utilisateur qui va se désabonner
     * */
    void leaveChannel(Channel channel, UserLite user);

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
}
