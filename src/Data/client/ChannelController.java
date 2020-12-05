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
            if(c.getId() == channelID) {
                c.addUser(user);
                break;
            }
        }
        this.mainClient.updateListChannel(channels);
    }

    /**
     * Save new admin into history.
     *
     * @param user    the user
     * @param channel the channel
     */
    public void saveNewAdminIntoHistory(User user, Channel channel) {

    }

    /**
     * New admin.
     *
     * @param user    the user
     * @param channel the channel
     */
    public void newAdmin(User user, Channel channel) {

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

    /**
     * Save message into history.
     *
     * @param message  the message
     * @param channel  the channel
     * @param response the response
     */
}
