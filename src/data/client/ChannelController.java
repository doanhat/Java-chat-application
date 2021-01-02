package data.client;

import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.shared_data.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ChannelController extends Controller{
    private List<Channel> channelList;
    private Channel localChannel;
    private final FileHandle<Channel> fileHandler = new FileHandle<>(LocationType.CLIENT, FileType.CHANNEL);

    public List<Channel> getChannelList() {
        return channelList;
    }

    public ChannelController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
        channelList = fileHandler.readAllJSONFilesToList(Channel.class);

        // Control ban list after reload
        Date currentDate = java.util.Date.from(LocalDate.now().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        for (Channel channel: channelList) {
            List<Kick> kicks = channel.getKicked();

            ListIterator<Kick> kickIter = kicks.listIterator();
            while (kickIter.hasNext()) {
                Kick kick = kickIter.next();

                if (!kick.isPermanentKick() && currentDate.after(kick.getEndKick())) {
                    channel.addAuthorizedUser(kick.getUser());

                    fileHandler.writeJSONToFile(channel.getId().toString(),channel);

                    kickIter.remove();
                }
            }
        }
    }

    public Channel searchChannelById(UUID id) {
        for(Channel ch : channelList){
            if (ch.getId().equals(id))
                return ch;
        }
        return null;
    }

    /**
     * Load proprietary local channels own to a specific user
     * @param user The user concerned
     */
    public void loadProprietaryChannels(UserLite user) {
        List<Channel> localChannels = fileHandler.readAllJSONFilesToList(Channel.class);
        channelList = localChannels.stream().filter(ch -> ch.getCreator().getId().equals(user.getId()))
                .collect(Collectors.toList());
        for( Channel c : channelList){
            c.setJoinedPersons(new ArrayList<>()); //init : au chargement aucun utilisateur n'a rejoint le channel
        }
    }

    public void addChannelToLocalChannels(Channel channel){
        channelList.removeIf(c -> c.getId().equals(channel.getId()));
        channelList.add(channel);
        new FileHandle<Channel>(LocationType.CLIENT, FileType.CHANNEL).writeJSONToFile(channel.getId().toString(),channel);

    }
    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    public void createChannel(Channel channel) {
        addChannelToLocalChannels(channel);
        this.mainClient.addChannel(channel);
    }

    /**
     * User added to channel.
     *
     * @param user    the user
     * @param channelID the channel
     */
    public void userAddedToChannel(UserLite user, UUID channelID) {
        List<Channel> channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelID)) {
                c.addJoinedUser(user);
                c.addAuthorizedUser(user);
                fileHandler.writeJSONToFile(channelID.toString(), c);
                break;
            }
        }
        /**
         *  TODO integration V2 disable this line
         *  Ici channels ne contient pas tout les channels visible pour l'utilisateur,
         *  seulement ceux qui sont dans le dossier resource/client/channel
         *  Donc cet appel "écrase" la vrai liste des channels visible pour l'utilisateur
         *  Voir donc si cela ne pose pas de problème ailleur
         */
        //this.mainClient.updateListChannel(channels);
    }

    /**
     * User Invited to channel.
     *
     * @param user    the user
     * @param channelID the channel
     */
    public void userInvitedToChannel(UserLite user, UUID channelID) {
        List<Channel> channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelID)) {
                c.addAuthorizedUser(user);
                new FileHandle<Channel>(LocationType.CLIENT, FileType.CHANNEL).writeJSONToFile(channelID.toString(), c);
                break;
            }
        }
    }

    /**
     * Save new admin into history.
     *  @param user    the user
     * @param channelId the channelId
     */
    public void saveNewAdminIntoHistory(UserLite user, UUID channelId) {
        Channel ownedChannel = searchChannelById(channelId);
        if (ownedChannel!=null) {
            ownedChannel.addAdmin(user);
            fileHandler.writeJSONToFile(ownedChannel.getId().toString(),ownedChannel);
        }
    }

    /**
     * New admin.
     *  @param user    the user
     * @param channelId the channelId
     */
    public void newAdmin(UserLite user, UUID channelId) {
        try {
            this.channelClient.addNewAdmin(user, channelId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * New admin.
     *  @param user    the user
     * @param channelId the channelId
     */
    public void removeAdmin(UserLite user, UUID channelId) {
        try {
            this.channelClient.removeAdmin(user, channelId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove channel from list.
     *
     * @param channelId     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    public void removeChannelFromList(UUID channelId, int duration, String explanation) {
        mainClient.removeChannel(channelId);
        channelClient.removeChannelFromList(channelId, duration, explanation);
    }

    /**
     * Delete user from channel.
     *
     * @param user        the user
     * @param channel     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    public void deleteUserFromChannel(User user, Channel channel, int duration, String explanation) {
        for (Channel c : getChannelList()) {
            if (c.getId().equals(channel.getId())) {
                c.removeUser(user.getId());
                if (duration >= 0) {
                    Date now = new Date();
                    now.setTime(now.getTime() + duration);
                    c.kickUser(user, explanation, now);
                } else {
                    c.kickPermanentUser(user, explanation);
                }
            }
        }
    }

    /**
     * Gets history.
     *
     * @param channelId channel id
     * @return history
     */
    public List<Message> getHistory(UUID channelId) {
        for (Channel c : channelList){
            if (c.getId().equals(channelId)){
                return c.getMessages();
            }
        }
        return new ArrayList<>();
    }

    public void addUserToOwnedChannel(UserLite user, UUID channelId) {
        List<Channel> channels;
        channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelId)) {
                c.addJoinedUser(user);
                c.addAuthorizedUser(user);
                fileHandler.writeJSONToFile(channelId.toString(), c);
                break;
            }
        }
    }


    /**
     * Save remove admin into history.
     * @param channelId the channelId
     */
    public void saveRemoveAdminIntoHistory(UUID channelId) {
        FileHandle fileHandler = new FileHandle(LocationType.CLIENT, FileType.CHANNEL);
        Channel ownedChannel = searchChannelById(channelId);
        if (ownedChannel!=null) {
            fileHandler.writeJSONToFile(ownedChannel.getId().toString(),ownedChannel);
        }
    }

    public void removeUserFromJoinedUserChannel(UserLite user, UUID channelId) {
        List<Channel> channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelId)) {
                c.removeUser(user.getId());
                fileHandler.writeJSONToFile(channelId.toString(), c);
                break;
            }
        }
    }

    public void removeAllUserFromJoinedUserChannel(UUID channelId) {
        List<Channel> channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelId)) {
                c.removeAllUser();
                fileHandler.writeJSONToFile(channelId.toString(), c);
                break;
            }
        }
    }

    public void removeUserFromAuthorizedUserChannel(UserLite user, UUID channelId) {
        List<Channel> channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelId)) {
                c.removeUserAuthorization(user.getId());
                fileHandler.writeJSONToFile(channelId.toString(), c);
                break;
            }
        }
    }

    public void updateNickname(UserLite user, UUID channelId, String newNickname) {
        channelClient.changeNickname(user, channelId, newNickname);
    }

    public void saveNicknameIntoHistory(UserLite user, UUID channelId, String newNickname) {
        Channel ownedChannel = searchChannelById(channelId);
        if (ownedChannel!=null) {
            ownedChannel.getNickNames().put(user.getId().toString(),newNickname);
            fileHandler.writeJSONToFile(ownedChannel.getId().toString(),ownedChannel);
        }
    }

    public void updateChannel(UUID channelId, UUID userID, String name, String description, Visibility visibility) {
        Channel ownedChannel = searchChannelById(channelId);
        if (ownedChannel!=null && ownedChannel.userIsAdmin(userID)) {
            if (name!=null) ownedChannel.setName(name);
            if(description!=null)ownedChannel.setDescription(description);
            if (visibility!=null)ownedChannel.setVisibility(visibility);
            fileHandler.writeJSONToFile(ownedChannel.getId().toString(),ownedChannel);
        }
    }

    public void banUserIntoHistory(UserLite user, UUID channelId, Date end, Boolean isPermanent,String explanation) {
        Channel ownedChannel = searchChannelById(channelId);
        List<Kick> kicked = ownedChannel.getKicked();
        if (ownedChannel!=null) {
            kicked.removeIf(k -> k.getUser().getId().equals(user.getId()));
            if (!isPermanent) {
                kicked.add(new Kick(user, channelId, explanation, end));
            } else {
                kicked.add(new Kick(user, channelId, explanation, true));
            }
            fileHandler.writeJSONToFile(ownedChannel.getId().toString(), ownedChannel);
        }
    }

    public void cancelBanOfUserIntoHistory(UserLite user, UUID channelId) {
        Channel ownedChannel = searchChannelById(channelId);
        List<Kick> kicked = ownedChannel.getKicked();
        kicked.removeIf(k -> k.getUser().getId().equals(user.getId()));
        ownedChannel.addAuthorizedUser(user);
        fileHandler.writeJSONToFile(ownedChannel.getId().toString(),ownedChannel);
    }

    public void removeChannel(UUID channelID) {
        Channel ownedChannel = searchChannelById(channelID);

        if (ownedChannel == null) {
            return;
        }

        channelList.remove(ownedChannel);
        fileHandler.deleteJSONFile(channelID.toString());
    }
}
