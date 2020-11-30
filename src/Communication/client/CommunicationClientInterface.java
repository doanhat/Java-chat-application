package Communication.client;

import Communication.common.Parameters;
import Communication.messages.client_to_server.AskToJoinMessage;
import Communication.messages.client_to_server.CreateChannelMessage;
import Communication.messages.client_to_server.SendMessageMessage;
import common.interfaces.client.*;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommunicationClientInterface implements IDataToCommunication,
                                                     IIHMMainToCommunication,
                                                     IIHMChannelToCommunication {

    private static CommunicationClientInterface instance = null;

    private final CommunicationClientController commController;
    private UserLite localUser;

    private CommunicationClientInterface() {
        this.commController = CommunicationClientController.instance();
    }

    /**
     * Recuperer singleton de CommunicationClientInterface
     * @return singleton de CommunicationClientInterface
     */
    public static CommunicationClientInterface instance() {
        if (instance == null) {
            instance = new CommunicationClientInterface();
        }

        return instance;
    }

    // NOTE Nornalement, appeller la methode instance() devra etre souffit
    //public static IDataToCommunication getIDataToCommunication() { return instance(); }
    //public static IIHMMainToCommunication getIHMMainToCommunication() { return instance(); }
    //public static IIHMChannelToCommunication getIHMChannelToCommunication() { return instance(); }

    /**
     * Installer les interfaces de Data, IHM Main et IHM Channel
     * @param dataIface interface de Data
     * @param mainIface interface de IHM Main
     * @param channelIface interface de IHM Channel
     * @return true si les interfaces sont correctement initialisées 
     * @return false si les interfaces n'ont pas été correctement initialisées
     */
    public boolean setupInterfaces(ICommunicationToData dataIface,
                                   ICommunicationToIHMMain mainIface,
                                   ICommunicationToIHMChannel channelIface) {
        return instance().commController.setupInterfaces(dataIface, mainIface, channelIface);
    }

    /* ---------------------------- IDataToCommunication interface implementations -----------------------------------*/

    /**
     * Connecte le client pour l'utilisateur passé en paramètre au serveur
     * @param user utilisateur à connecter
     */
    @Override
    public void userConnect(UserLite user) {
        this.localUser = user;
        this.commController.start(Parameters.SERVER_IP, Parameters.PORT, user);
    }

    /**
     * Transfere au serveur la demande de suppresion d'un channel
     * @param channelID ID de l'objet à supprimer
     * @implNote
     **/
    @Override
    public void delete(UUID channelID) {
        // TODO V2
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
     * @param channel [Channel] Objet channel a crée sur le serveur
     * @param isShared [Boolean] Si le channel est partagé ou non
     * @param isPublic [Boolean] Si le channel est publique ou non
     * @param owner [UserLite] Information sur le proprietaire du channel si c'est un channel privé
     **/
    public void createChannel(Channel channel, Boolean isShared, Boolean isPublic, UserLite owner) {
        //TODO INTEGRATION /!\ probleme isShared alors que ChannelMessage attend l'inverse
        this.commController.sendMessage(new CreateChannelMessage(owner, channel, !isShared, isPublic));
    }

    /* -------------------------- IIHMChannelToCommunication interface implementations -------------------------------*/

    /**
     * Transfert au serveur l'envoie d'un message d'invitation au serveur'envoi
     * d'une invitation a rejoindre un channel
     *
     * @param sender [UserLite] Utilisateur qui crée l'invitation
     * @param receiver [UserLite] Utilisateur qui doit recevoir l'invitation
     * @param message [Message] Message d'invitation
     **/
    public void sendInvite(UserLite sender, UserLite receiver, Message message) {
        // TODO V2
    }

    /**
     * Demande l'envoie d'un message de nomination d'administrateur au serveur
     *
     * @param us [UserLite] Utilisateur devenant admin
     * @param channel [Channel] Channel qui doit recevoir les droitsChannel
     *                sur lequel on souhait donnée les droits d'admin
     **/
    public void giveAdmin(UserLite us, Channel channel) {
        // TODO V3
    }
    /**
     * Demande de bannir un utilisateur d'un channel
     *
     * @param user Utilisateur a bannir
     * @param duration Durée du bannisement
     * @param explanation Chaine de caractere justifiant le ban
     **/
    public void banUserFromChannel(UserLite user, int duration, String explanation) {
        // TODO V3
    }

    /**
     * Envoie d'un message au serveur
     *
     * @param msg Nouveau messsage a envoyer
     * @param channel Channel sur lequel ont veut envoyer le message
     * @param response Message auquel le nouveau message repond sinon null
     **/
    public void sendMessage(Message msg, Channel channel, Message response) {
        this.commController.sendMessage(new SendMessageMessage(msg, channel.getId(), response));
    }

    /**
     * Envoie une demande d'édite au serveur
     *
     * @param msg [Message] Message d'origine
     * @param new_msg [Message] Message modifier
     * @param channel [Channel] Channel du message a modifier
     **/
    public void editMessage(Message msg, Message new_msg, Channel channel) {
        // TODO V2
    }

    /**
     * Envoie une demande de like d'un message au serveur
     *
     * @param channel [Channel] Channel du message a like
     * @param msg [Message] Message à like
     * @param user [UserLite] Utilisateur ayant like
     **/
    public void likeMessage(Channel channel, Message msg, UserLite user) {
        // TODO V2
    }

    /**
     * Envoie une demande de suppression de message au serveur
     *
     * @param msg [Message] Message a supprimer
     * @param channel [Channel] Channel du message a supprimer
     * @param user [UserLite] Utilisateur demandant la suppression
     **/
    public void suppMessage(Message msg, Channel channel, UserLite user) {
        // TODO V2
    }

    /**
     * Envoie l'information d'un changement de pseudo au serveur
     *
     * @param user [UserLite] Utilisateur concerné
     * @param channel [Channel] Channel ou le changement de pseudo à lieu
     * @param newNickname [String] Nouveau pseudo
     **/
    public void changeNickname(UserLite user, Channel channel, String newNickname) {
        // TODO V2
    }

    /**
     * Demande de quitter un channel au serveur
     *
     * @param user [UserLite] Utilisateur concerné
     * @param channel [Channel] Channel que l'on veut quitter
     **/
    public void leaveChannel(UserLite user, Channel channel) {
        // TODO V2
    }

    /**
     * Demande de rejoindre channel au serveur
     *
     * @param channel [Channel] Channel que l'on veut rejoindre
     **/
    public void askToJoin(Channel channel) {
        commController.sendMessage(new AskToJoinMessage(channel.getId(), localUser));
    }

    /**
     * Recupere l'histoique d'un serveur donnée
     *
     * @param channel [Channel] Channel dont on demande l'historique
     * @return List<Message> Liste des messages qui compose l'historique
     **/
    public List<Message> getHistory(Channel channel){
        // TODO V3
        return new ArrayList<>();
    }
}
