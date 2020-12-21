package common.interfaces.client;

import common.shared_data.Channel;
import common.shared_data.User;
import common.shared_data.UserLite;
import common.shared_data.Visibility;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The interface Iihm main to data.
 */
public interface IIHMMainToData {
    /**
     * Déconnecter application
     */
    void disconnect();

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
    void createChannel(String name, String description, boolean isShared, boolean isPublic, UserLite owner);

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
    boolean localAuthentification(String nickName, String password);

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
    boolean createAccount(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate);

    /**
     * Get connected User.
     */
    User getUser();

    String exportUserProfile(UUID userId);

    /**
     * Get user by id
     * @param userId id user
     * @return User matching the id
     */
    User getUserById(UUID userId);

    /**
     * Return path to the directory where avatar image are store locally
     * End by file separator
     * @return
     */
    String getLocalAvatarDirectoryPath();
}
