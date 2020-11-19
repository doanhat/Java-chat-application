package Data.client;

import common.interfaces.client.IIHMMainToData;
import common.sharedData.Channel;
import common.sharedData.User;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.util.Date;
import java.util.List;

public class IHMMainToData implements IIHMMainToData {

    private DataClientController dataController;

    public IHMMainToData(DataClientController dataClientController) {
        this.dataController = dataClientController;
    }


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
     * Search channel.
     *
     * @param name        the name
     * @param creator     the creator
     * @param description the description
     * @param visibility  the visibility
     */
    @Override
    public List<Channel> searchChannel(String name, UserLite creator, String description, Visibility visibility) {
        return null;
    }

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
    @Override
    public void editProfile(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate, User user) {

    }

    /**
     * Local authentification.
     *
     * @param nickName the pseudo
     * @param password the password
     */
    @Override
    public void localAuthentification(String nickName, String password) {}

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
    @Override
    public void createAccount(String nickName, String avatar, String password, String lastName, String firstName, Date birthDate) {

    }

}
