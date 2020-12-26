package data.server;

import Communication.common.Parameters;
import Communication.messages.client_to_server.connection.ClientPulseMessage;
import Communication.messages.client_to_server.connection.UserConnectionMessage;
import common.interfaces.server.IServerDataToCommunication;
import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import common.shared_data.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;

public class ChannelsListController {
    private List<Channel> sharedChannels;
    private List<Channel> ownedChannels;
    private FileHandle fileHandle;
    private Timer timer;
    private IServerDataToCommunication commIface;

    public ChannelsListController() {
        this.fileHandle = new FileHandle<Channel>(LocationType.SERVER, FileType.CHANNEL);
        this.sharedChannels = createChannelListFromJSONFiles();
        this.ownedChannels = new ArrayList<>();
    }

    public List<Channel> createChannelListFromJSONFiles(){
        List<Channel> list = fileHandle.readAllJSONFilesToList(Channel.class);
        for( Channel c : list){
            c.setJoinedPersons(new ArrayList<>()); //init pour éviter la liste null
        }
        return list;
    }

    public void setIServerDataToCommunication(IServerDataToCommunication commIface) {
        this.commIface = commIface;

        // NOTE: refreshKicks utilise commIface, donc on initialise le timer ici afin d'éviter null référence de commIface
        if (this.timer != null) {
            this.timer.cancel();
            this.timer.purge();
        }

        // interval de rafraîchir de 30s pour enlever le ban qui se dépasse le endDate
        this.timer = new Timer();

        this.timer.schedule(new TimerTask(){
            @Override
            public void run() {
                Date currentDate = java.util.Date.from(LocalDate.now().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

                for (Channel channel: sharedChannels) {
                    refreshKicks(channel, currentDate);
                }

                for (Channel channel: ownedChannels) {
                    refreshKicks(channel, currentDate);
                }
            }
        }, 30000, 30000);
    }

    public Channel searchChannelById(UUID id) {
        /*Chercher dans les channels partagés*/
        for(Channel ch : sharedChannels){
            if (ch.getId().equals(id))
                return ch;
        }
        /*Chercher dans les channels partagés*/
        for(Channel ch : ownedChannels){
            if (ch.getId().equals(id))
                return ch;
        }
        return null;
    }

    public List<Message> getChannelMessages(UUID channelID){
        Channel channel = searchChannelById(channelID);

        if (channel == null) {
            return null;
        }

        return channel.getMessages();
    }

    public Message getMessageFromId(UUID channelId, UUID messageId) {
        List<Message> messages = getChannelMessages(channelId);

        for (Message msg : messages) {
            if (msg.getId().equals(messageId)) {
                return msg;
            }
        }

        return null;
    }

    public List<Channel> searchChannelByDesc(String description) {
        return null;
    }

    public List<Channel> searchChannelByUsers(List<String> users) {
        return null;
    }

    public List<UUID> getChannelsWhereUser(UUID userID){
        ArrayList<UUID> res = new ArrayList<UUID>();
        for (Channel channel: ownedChannels) {
            if(channel.userIsAuthorized(userID))
                res.add(channel.getId());
        }
        for (Channel channel: sharedChannels) {
            if(channel.userIsAuthorized(userID))
                res.add(channel.getId());
        }
        return res;
    }

    public void addChannel(Channel channel) {
        if(searchChannelById(channel.getId())==null) {
            if(channel.getType().equals(ChannelType.SHARED)) {
                sharedChannels.add(channel);
                writeChannelDataToJSON(channel);
            } else{ //Channel proprietaire
                ownedChannels.add(channel);
            }
        }
    }

    public void removeChannel(UUID channelID) {
        Channel ch = searchChannelById(channelID);
        if(ch!=null) {
            if(ch.getType().equals(ChannelType.SHARED)) {
                sharedChannels.remove(ch);
                fileHandle.deleteJSONFile(channelID.toString());
            } else{ //Channel proprietaire
                ownedChannels.remove(ch);
            }
        }
    }

    public List<Channel> getSharedChannels() {
        return this.sharedChannels;
    }

    public List<Channel> getOwnedChannels() {
        return this.ownedChannels;
    }

    public List<Channel> getOwnedChannelsForUser(UUID user){
        List<Channel> list = new ArrayList<>();
        for (Channel channel: ownedChannels) {
            if(channel.getCreator().equals(user)){
                list.add(channel);
            }
        }
        return list;
    }

    public void writeChannelDataToJSON(Channel channel){
        this.fileHandle.writeJSONToFile(channel.getId().toString(),channel);
    }

    public Channel readJSONToChannelData(UUID idChannel){
        return (Channel) this.fileHandle.readJSONFileToObject(idChannel.toString(), Channel.class);
    }

    /**
     * Enregistre un message et son parent dans l'historique d'un channel.
     *
     * @param channel   Le channel.
     * @param message   Le message à enregistrer.
     * @param parent    Le parent du message, s'il existe.
     */
    public void writeMessageInChannel(Channel channel, Message message, Message parent){
        if (channel != null) {
            List<Message> messages = channel.getMessages();

            if (parent != null) {
                for (Message msg : messages) {
                    if (msg.getId().equals(parent.getId())) {
                        msg.addAnswers(message);
                        break;
                    }
                }
            }

            this.writeChannelDataToJSON(channel);
        }
    }

    /**
     * Sets a message as deleted.
     *
     * @param channel           Le channel.
     * @param message           Le message à supprimer.
     * @param deletedByCreator  L'utilisateur qui demande la suppression est l'auteur.
     */
    public void writeRemovalMessageInChannel(Channel channel, Message message, Boolean deletedByCreator) {
        if (channel.getType() == ChannelType.SHARED) {
            Channel c = this.searchChannelById(channel.getId());
            List<Message> ms = c.getMessages();

            for (Message m : ms) {
                if (m.getId().equals(message.getId())) {
                    m.setMessage("");
                    m.delete(deletedByCreator);
                    break;
                }
            }

            this.writeChannelDataToJSON(c);
        }
    }

    /**
     * Enregistre un nouveau admin dans l'historique d'un channel.
     *
     * @param channel   Le channel.
     * @param user      L'admin à ajouter.
     */
    public void writeNewAdminInChannel(Channel channel, UserLite user) {
        channel.addAdmin(user);
        if (channel.getType() == ChannelType.SHARED) {
            this.writeChannelDataToJSON(channel);
        }
    }

    /**
     * Enregistre les modifications d'un message dans l'historique.
     *
     * @param channelId     (UUID) L'identifiant du channel.
     * @param editedMsg     Le message contenant le texte edité.
     */
    public void writeEditMessage(UUID channelId, Message editedMsg) {
        Channel channel = searchChannelById(channelId);
        Message originalMsg = getMessageFromId(channel.getId(), editedMsg.getId());

        if (channel != null && originalMsg != null) {
            originalMsg.setMessage(editedMsg.getMessage());
            originalMsg.setEdited(true);

            writeChannelDataToJSON(channel);
        }
    }

    /**
     * Enregistre le like d'un message dans l'historique d'un channel.
     *
     * @param channelId     L'identifiant du channel
     * @param msg           Le message auquel on réagit
     * @param user          L'utilisateur qui réagit
     */
    public void writeLikeIntoHistory(UUID channelId, Message msg, UserLite user) {
        Channel channel = searchChannelById(channelId);
        Message message = getMessageFromId(channel.getId(), msg.getId());

        if (channel != null && message != null) {
            message.addLike(user);
            writeChannelDataToJSON(channel);
        }
    }

    public List<Channel> disconnectOwnedChannel(UserLite owner) {
        List<Channel> userOwnedChannels = new ArrayList<>();

        if (ownedChannels == null) {
            return null;
        }

        for (Channel channel: ownedChannels) {
            if (channel.getCreator().getId().equals(owner.getId())) {
                userOwnedChannels.add(channel);

            }
        }
        ownedChannels.removeIf(ch -> (ch.getCreator().getId().equals(owner.getId())));
        return userOwnedChannels;
    }


    /**
     * Save deletion into history.
     *
     * @param channelId  the channel ID
     */
    public void saveDeletionIntoHistory(UUID channelId) {
        Channel channel = searchChannelById(channelId);
        if(channel!=null) {
            FileHandle fileHandler;
            if (channel.getType().equals(ChannelType.OWNED))
                fileHandler = new FileHandle<Channel>(LocationType.CLIENT, FileType.CHANNEL);
            else
                fileHandler = new FileHandle<Channel>(LocationType.SERVER, FileType.CHANNEL);

            fileHandler.deleteJSONFile(channel.getId().toString());
        }
    }

    /**
     * Enregistre la suppression d'un admin dans l'historique d'un channel.
     *
     * @param channel   Le channel.
     */
    public void writeRemoveAdminInChannel(Channel channel) {
        if (channel.getType() == ChannelType.SHARED) {
            this.writeChannelDataToJSON(channel);
        }
    }

    public void banUserFromChannel(UserLite user, UUID channelId, Date date, Boolean isPermanent, String explanation) {
        Channel channel = searchChannelById(channelId);

        if(channel!=null && channel.getType().equals(ChannelType.SHARED)) {
            List<Kick> kicked = channel.getKicked();
            kicked.removeIf(k -> k.getUser().getId().equals(user.getId()));
            if (!isPermanent){
                kicked.add(new Kick(user,channelId,explanation,date));
            } else {
                kicked.add(new Kick(user,channelId,explanation,true));
            }

            channel.removeUserAuthorization(user.getId());
            channel.removeUser(user.getId());

            if (channel.userIsAdmin(user.getId())) {
                // Note remove admin if admin is banned
                channel.removeAdmin(user.getId());
            }

            writeChannelDataToJSON(channel);
        }

        // TODO INTEGRATION V4: Remarque à Data: faire la gestion des bans temporaires (un polling pour enlever le ban après la dépasse de délai défini par endBan)
    }

    private void refreshKicks(Channel channel, Date currentDate) {
        List<Kick> kicks = channel.getKicked();

        for (Kick kick: kicks) {
            if (!kick.isPermanentKick() && currentDate.after(kick.getEndKick())) {
                kicks.remove(kick);
                channel.addAuthorizedUser(kick.getUser());

                commIface.informUsersBanRemoved(channel, kick.getUser());

                writeChannelDataToJSON(channel);
            }
        }
    }
}