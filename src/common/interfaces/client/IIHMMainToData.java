package common.interfaces.client;

import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.Date;
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
     * Search channel.
     *
     * @param name        the name
     * @param creator     the creator
     * @param description the description
     * @param visibility  the visibility
     */
    List<Channel> searchChannel(String name, UserLite creator, String description, Visibility visibility);

    /**
     * Edit profile.
     *
     * @param nickName  the nick name
     * @param avatar    the avatar
     * @param password  the password
     * @param lastName  the last name
     * @param firstName the first name
     * @param birthDate the birth date
     * @param user      the user
     */
    void editProfile(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate, User user);

    /**
     * Local authentification.
     *
     * @param nickName   the pseudo
     * @param password the password
     */
    void localAuthentification(String nickName, String password);

    /**
     * Create account.
     *
     * @param nickName  the nick name
     * @param avatar    the avatar
     * @param password  the password
     * @param lastName  the last name
     * @param firstName the first name
     * @param birthDate the birth date
     */
    void createAccount(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate);
}
