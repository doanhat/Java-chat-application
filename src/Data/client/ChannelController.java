package Data.client;

import Data.resourceHandle.FileHandle;
import Data.resourceHandle.FileType;
import Data.resourceHandle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChannelController extends Controller{
    private List<Channel> channelList;
    private Channel localChannel;
    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
    public ChannelController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
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
        FileHandle fileHandle = new FileHandle<Channel>(LocationType.client, FileType.channel);
        List<Channel> localChannels = fileHandle.readAllJSONFilesToList(Channel.class);
        channelList = localChannels.stream().filter(ch -> ch.getCreator().getId().equals(user.getId()))
                .collect(Collectors.toList());
        for( Channel c : channelList){
            c.setJoinedPersons(new ArrayList<>()); //init : au chargement aucun utilisateur n'a rejoint le channel
        }
    }

    public void addChannelToLocalChannels(Channel channel){
        for (Channel c : channelList){
            if (c.getId().equals(channel.getId())){
                channelList.remove(c);
            }
        }
        channelList.add(channel);
        new FileHandle<Channel>(LocationType.client, FileType.channel).writeJSONToFile(channel.getId().toString(), channel);

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
                new FileHandle<Channel>(LocationType.client, FileType.channel).writeJSONToFile(channelID.toString(), c);
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
                new FileHandle<Channel>(LocationType.client, FileType.channel).writeJSONToFile(channelID.toString(), c);
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
        FileHandle fileHandler = new FileHandle(LocationType.client, FileType.channel);
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
            this.channelClient.addNewAdmin(user,this.channelClient.getChannel(channelId));
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
     * Ban user into history.
     *
     * @param user     the user
     * @param channel  the channel
     * @param duration the duration
     */
    public void banUserIntoHistory(User user, Channel channel, int duration) {

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
        return null;
    }

    public void sendOwnedChannelsToServer(){
        this.comClient.sendProprietaryChannels(this.channelList);
    }
    public void sendOwnedChannelToServer(Channel channel){
        this.comClient.sendProprietaryChannel(channel);
    }

    public void addUserToOwnedChannel(UserLite user, UUID channelId) {
        List<Channel> channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelId)) {
                c.addJoinedUser(user);
                c.addAuthorizedUser(user);
                new FileHandle<Channel>(LocationType.client, FileType.channel).writeJSONToFile(channelId.toString(), c);
                break;
            }
        }
    }

    public void removeUserFromJoinedUserChannel(UserLite user, UUID channelId) {
        List<Channel> channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelId)) {
                c.removeUser(user.getId());
                new FileHandle<Channel>(LocationType.client, FileType.channel).writeJSONToFile(channelId.toString(), c);
                break;
            }
        }
    }

    public void removeAllUserFromJoinedUserChannel(UUID channelId) {
        List<Channel> channels = getChannelList();
        for (Channel c : channels) {
            if(c.getId().equals(channelId)) {
                c.removeAllUser();
                new FileHandle<Channel>(LocationType.client, FileType.channel).writeJSONToFile(channelId.toString(), c);
                break;
            }
        }
    }
}
