package Data.client;

import common.interfaces.client.IDataToCommunication;
import common.interfaces.client.IDataToIHMChannel;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.User;

import java.util.List;

public class ChannelController extends Controller{
    public ChannelController(IDataToCommunication comClient, IDataToIHMChannel channelClient, IDataToIHMMain mainClient) {
        super(comClient, channelClient, mainClient);
    }

    /**
     * Add visible channel.
     *
     * @param channel the channel
     */
    public void addVisibleChannel(Channel channel) {

    }

    /**
     * User added to channel.
     *
     * @param user    the user
     * @param channel the channel
     */
    public void userAddedToChannel(User user, Channel channel) {

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
