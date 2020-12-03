package Data.client;

import common.interfaces.client.IIHMMainToData;
import common.sharedData.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        return this.dataController.getUserController().getConnectedUsers();
    }

    /**
     * Gets channels.
     *
     * @return the channels
     */
    @Override
    public List<Channel> getChannels() {
        return this.dataController.getChannelController().getChannels();
    }

    /**
     * Create channel.
     *
     * @param name  the channel name
     * @param isShared the is shared
     * @param isPublic the is public
     * @param owner    the owner
     */
    @Override
    public void createChannel(String name, String description, Boolean isShared, Boolean isPublic, UserLite owner) {
        Channel channel;
        channel = new Channel(name, owner, description, isPublic ? Visibility.PUBLIC : Visibility.PRIVATE, isShared ? ChannelType.SHARED : ChannelType.OWNED);
        this.dataController.getChannelController().addVisibleChannel(channel);
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
     * @param user    the user
     * @param nickName  the nick name
     * @param avatar    the avatar
     * @param password  the password
     * @param lastName  the last name
     * @param firstName the first name
     * @param birthDate the birth date
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
    public void localAuthentification(String nickName, String password) {
        UserController userController = dataController.getUserController();
        userController.verificationAccount(nickName,password);
    }

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

    // TODO UPGRATE
    @Override
    public User getUser() {
        return dataController.getUserController().getUser();
    }
}
