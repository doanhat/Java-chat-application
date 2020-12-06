package Data.client;

import Data.resourceHandle.FileHandle;
import Data.resourceHandle.FileType;
import Data.resourceHandle.LocationType;
import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChannelController extends Controller{
    private List<Channel> channelList;

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
    public ChannelController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
        channelList = new FileHandle<Channel>(LocationType.client, FileType.channel).readAllJSONFilesToList(Channel.class);
        sendOwnedChannelsToServer();
    }

    public Channel searchChannelById(UUID id) {
        for(Channel ch : channelList){
            if (ch.getId().equals(id))
                return ch;
        }
        return null;
    }
    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    public void addVisibleChannel(Channel channel) {
        List<Channel> channels = getChannelList();
        channels.add(channel);
        this.mainClient.addChannelToList(channel);
        this.comClient.sendProprietaryChannels(channelList);
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
                break;
            }
        }
        this.mainClient.updateListChannel(channels);
        this.comClient.sendProprietaryChannels(channelList);
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
            sendOwnedChannelsToServer();
        }
    }

    /**
     * New admin.
     *  @param user    the user
     * @param channelId the channelId
     */
    public void newAdmin(UserLite user, UUID channelId) {
        Channel channel = searchChannelById(channelId);
        if (channel!=null){
            channel.addAdmin(user);
            this.channelClient.addNewAdmin(user,this.channelClient.getChannel(channelId));
            sendOwnedChannelsToServer();
        }
    }

    /**
     * Remove channel from list.
     *
     * @param channel     the channel
     * @param duration    the duration
     * @param explanation the explanation
     */
    public void removeChannelFromList(Channel channel, int duration, String explanation) {

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
}
