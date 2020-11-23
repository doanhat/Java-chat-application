package Data.client;

import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChannelController extends Controller{
    public ChannelController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
    }
    /**
     * Get all channels
     *
     * @return List<Channel>
     */
    public List<Channel> getChannels() {
        List<Channel> channels = new ArrayList<Channel>();

        // TODO : Get real data
        for (int i = 1; i < 5; i++) {
            channels.add(new Channel("channel n°" + i, null, "Description du channel n°" + i, i % 2 == 0 ? Visibility.PUBLIC : Visibility.PRIVATE, ChannelType.SHARED));
        }
        return channels;
    }
    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    public void addVisibleChannel(Channel channel) {
        List<Channel> channels = getChannels();
        channels.add(channel);
        this.mainClient.updateListChannel(channels);
    }

    /**
     * User added to channel.
     *
     * @param user    the user
     * @param channelID the channel
     */
    public void userAddedToChannel(UserLite user, UUID channelID) {
        List<Channel> channels = getChannels();
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
     * @param channel the channel
     * @return history
     */
    List<Message> getHistory(Channel channel) {
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
