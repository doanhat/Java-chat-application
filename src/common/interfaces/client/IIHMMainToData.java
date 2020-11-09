package Common.Interfaces.Client;

import Common.Data.Channel;
import Common.Data.User;
import Common.Data.UserLite;

import java.util.List;

/**
 * The interface Iihm main to data.
 */
public interface IIHMMainToData {
    /**
     * Gets connected users.
     *
     * @return the connected users
     */
    List<UserLite> getConnectedUsers();

    /**
     * Gets channels.
     *
     * @return the channels
     */
    List<Channel> getChannels();

    /**
     * Create channel.
     *
     * @param channel  the channel
     * @param isShared the is shared
     * @param isPublic the is public
     * @param owner    the owner
     */
    void createChannel(Channel channel, Boolean isShared, Boolean isPublic, UserLite owner);

    /**
     * Edit profile.
     *
     * @param options the options
     * @param user    the user
     */
    void editProfile(String[] options, User user);

}
