package common.interfaces.client;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;

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
     * @param name  the channel name
     * @param description  the channel description
     * @param isShared the is shared
     * @param isPublic the is public
     * @param owner    the owner
     */
    void createChannel(String name, String description, Boolean isShared, Boolean isPublic, UserLite owner);

    /**
     * Edit profile.
     *
     * @param options the options
     * @param user    the user
     */
    void editProfile(String[] options, User user);

}
