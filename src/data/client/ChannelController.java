package data.client;

import data.resource_handle.FileHandle;
import data.resource_handle.FileType;
import data.resource_handle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.shared_data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChannelController extends Controller{
    private List<Channel> channelList;
    private Channel localChannel;
    public List<Channel> getChannelList() {
        return channelList;
    }
    public Channel getLocalChannel() { return localChannel; }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
    public ChannelController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
        channelList = new FileHandle<Channel>(LocationType.CLIENT, FileType.CHANNEL).readAllJSONFilesToList(Channel.class);
        sendOwnedChannelsToServer();
    }

    public Channel searchChannelById(UUID id) {
        for(Channel ch : channelList){
            if (ch.getId().equals(id))
                return ch;
        }
        return null;
    }

    public void addChannelToLocalChannels(Channel channel){
        for (Channel c : channelList){
            if (c.getId().equals(channel.getId())){
                channelList.remove(c);
            }
        }
        channelList.add(channel);
        new FileHandle<Channel>(LocationType.CLIENT, FileType.CHANNEL).writeJSONToFile(Channel.class);

    }
    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    public void createChannel(Channel channel) {
        addChannelToLocalChannels(channel);
        this.mainClient.addChannelToList(channel);
        sendOwnedChannelToServer(channel);
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
                c.addUser(user);
                new FileHandle<Channel>(LocationType.CLIENT, FileType.CHANNEL).writeJSONToFile(Channel.class);
                break;
            }
        }
        this.mainClient.updateListChannel(channels);
    }

    /**
     * Save new admin into history.
     *  @param user    the user
     * @param channelId the channelId
     */
    public void saveNewAdminIntoHistory(UserLite user, UUID channelId) {
        FileHandle fileHandler = new FileHandle(LocationType.CLIENT, FileType.CHANNEL);
        Channel ownedChannel = searchChannelById(channelId);
        if (ownedChannel!=null) {
            ownedChannel.addAdmin(user);
            fileHandler.writeJSONToFile(ownedChannel);
        }
    }

    /**
     * New admin.
     *  @param user    the user
     * @param channelId the channelId
     */
    public void newAdmin(UserLite user, UUID channelId) {
        this.channelClient.addNewAdmin(user,this.channelClient.getChannel(channelId));
    }

    /**
     * Remove channel from list.
     *
     * @param channel     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    public void removeChannelFromList(Channel channel, int duration, String explanation) {
        throw new UnsupportedOperationException();
    }

    /**
     * Ban user into history.
     *
     * @param user     the user
     * @param channel  the channel
     * @param duration the duration
     */
    public void banUserIntoHistory(User user, Channel channel, int duration) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
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
                c.addUser(user);
                new FileHandle<Channel>(LocationType.CLIENT, FileType.CHANNEL).writeJSONToFile(Channel.class);
                break;
            }
        }
    }
}
