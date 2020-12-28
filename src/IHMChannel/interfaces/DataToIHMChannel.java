package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import IHMChannel.controllers.ChannelController;
import IHMChannel.controllers.ChannelPageController;
import common.IHMTools.IHMTools;
import common.interfaces.client.IDataToIHMChannel;
import common.shared_data.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataToIHMChannel implements IDataToIHMChannel{

    private IHMChannelController controller;

    public DataToIHMChannel(IHMChannelController controller){
        this.controller = controller;
    }

    
    /**
     * Permet de traiter la suppression du channel actuellement ouvert sur l'interface.
     * @param channelID id du channel supprimé
     */
    @Override
    public void openChannelDeleted(UUID channelID) {
        // Normalement, cela revient à leaveChannel => en attendant son implémentation => création de removeChannel.
        // this.controller.getChannelPageController().leaveChannel(channelID);
        this.controller.getChannelPageController().removeChannel(channelID);
    }

    /**
     * Permet d'ajouter un administrateur pour un channel.
     *  @param user    user qui devient admin
     * @param channelId channel pour lequel on a ajouté un admin
     */
    @Override
    public void addNewAdmin(UserLite user, UUID channelId) throws IOException {
        ChannelPageController channelPageController = controller.getChannelPageController();
        if(channelPageController != null){ // channelPageController sera null si un des clients n'a encore ouvert aucun channel
            ChannelController channelController = channelPageController.getChannelController(channelId);
            if(channelController != null){
                channelController.addNewAdmin(user);
            }

        }

    }

    @Override
    public void removeAdmin(UserLite user, UUID channelId) throws IOException {
        ChannelController channelController = controller.getChannelPageController().getChannelController(channelId);
        channelController.removeAdmin(user);
    }

    /**
     * Quand l'utilisateur se fait kické d'un channel, retire le channel en question de sa liste de channels.
     * Ce retrait peut, comme un kick, être temporaire.
     * Cela peut être aussi du au fait que le propriétaire du channel se soit déconnecté
     *  @param channelID     channel à retirer
     * @param duration    durée du kick
     * @param explanation motif du kick
     */
    @Override
    public void removeChannelFromList(UUID channelID, int duration, String explanation) {

        IHMTools.informationPopup(String.format("Vous avez été banni pendant %d secondes pour la raison suivante : %s", duration, explanation));

        controller.getChannelPageController().removeTab(channelID);
    }

    /**
     * Notification pour les membres restants d'un channel qu'un utilisateur a été kické.
     *  @param user        utilisateur kické
     * @param channelId     channel concerné
     * @param duration    durée du kick
     * @param explanation motif du kick
     */
    //TODO Intégration décommenter en mettant le bon constructeur ou en remplacement les attributs de la méthode par
    // un seul "Kick"
    @Override
    public void userBanNotification(UserLite user, UUID channelId, LocalDate duration, String explanation) {
        if (user!=null){
            controller.getInterfaceForCommunication().removeConnectedUser(channelId, user);
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date end = null;
            if(duration != null){
                end = Date.from(duration.atStartOfDay(defaultZoneId).toInstant());
            }
            List<Kick> userList = controller.getChannelPageController().getChannelController(channelId).getCurrentChannel().getKicked();
            userList.add(new Kick(user, channelId, explanation, end));
            try {
                controller.getChannelPageController().getChannelController(channelId).removeUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Notifie d'un retour d'un utilisateur précédemment kické.
     *  @param user    user revenu sur le channel
     * @param channelId id du channel sur lequel user est revenu*/
    @Override
    public void userBanCancelledNotification(UserLite user, UUID channelId) {
        if (user!=null){
            List<Kick> userList = controller.getChannelPageController().getChannelController(channelId).getCurrentChannel().getKicked();
            userList.removeIf(k -> k.getUser().getId().equals(user.getId()));
            //ch.addAuthorizedUser(user);
        }

        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Permet la réception d'un message sur un channel. Sert aussi à la réception de messages qui sont des réponses
     * à d'autres.
     *
     * @param message    message reçu
     * @param channelID    channel sur lequel le message a été reçu
     * @param responseTo message parent
     */
    @Override
    public void receiveMessage(Message message, UUID channelID, Message responseTo) {
        //get channelctrl depuis ihmctrl
        ChannelController channelController = controller.getChannelPageController().getChannelController(channelID);
        //appeler channelctrl.receiveMessage()
        channelController.receiveMessage(message, responseTo);
    }

    /**
     * Permet la modification d'un message.
     *  @param message    message d'origine
     * @param newMessage nouveau message
     * @param channelId    channel sur lequel le message a été modifié
     */
    @Override
    public void editMessage(Message message, Message newMessage, UUID channelId) {
        controller.getChannelPageController().getChannelController(channelId).editMessage(message, newMessage);
    }

    /**
     * Permet de liker un message.
     * @param channelId channel du message
     * @param message message liké
     * @param user    utilisateur à l'origine du like
     */
    @Override
    public void likeMessage(UUID channelId, Message message, UserLite user) {
        controller.getChannelPageController().getChannelController(channelId).likeMessage(message, user);
    }

    /**
     * Permet la suppression d'un message.
     *  @param message          message supprimé
     * @param channelId          channel sur lequel le message a été supprimé
     * @param deletedByCreator booléen indiquant si la suppression a été faite par le propriétaire du message
     */
    @Override
    public void deleteMessage(Message message, UUID channelId, boolean deletedByCreator) {
        controller.getChannelPageController().getChannelController(channelId).deleteMessage(message, deletedByCreator);
    }

    @Override
    public Channel getChannel(UUID id){
        return controller.getChannelPageController().getChannelController(id).getCurrentChannel();
    }

    @Override
    public void modifyChannel(UUID channelId, String name, String description, Visibility visibility) {
        controller.getChannelPageController().modifyChannel(channelId, name, description, visibility);
        controller.getInterfaceToIHMMain().modifyChannel(channelId, name, description, visibility);
    }
}
