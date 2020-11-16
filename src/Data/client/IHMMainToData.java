package Data.client;

import common.interfaces.client.IIHMMainToData;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;

import java.util.List;

public class IHMMainToData implements IIHMMainToData {
    /**
     * Gets connected users.
     *
     * @return the connected users
     */
    @Override
    public List<UserLite> getConnectedUsers() {
        return null;
    }

    /**
     * Gets channels.
     *
     * @return the channels
     */
    @Override
    public List<Channel> getChannels() {
        return null;
    }

    /**
     * Create channel.
     *
     * @param channel  the channel
     * @param isShared the is shared
     * @param isPublic the is public
     * @param owner    the owner
     */
    @Override
    public void createChannel(Channel channel, Boolean isShared, Boolean isPublic, UserLite owner) {

    }

    /**
     * Edit profile.
     *
     * @param options the options
     * @param user    the user
     */
    @Override
    public void editProfile(String[] options, User user) {

    }
}
